package com.net.SprintOne.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.net.SprintOne.dtos.AuthenticationResponse;
import com.net.SprintOne.model.Employee;
import com.net.SprintOne.dtos.EmployeeDto;
import com.net.SprintOne.model.User;
import com.net.SprintOne.dtos.UserDto;
import com.net.SprintOne.service.JwtUserDetailsService;
import com.net.SprintOne.service.serviceImpl.ConvertServiceImpl;
import com.net.SprintOne.service.serviceImpl.EmailServiceImpl;
import com.net.SprintOne.service.serviceImpl.EmployeeServiceImpl;
import com.net.SprintOne.service.serviceImpl.UserServiceImpl;
import com.net.SprintOne.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtTokenUtils;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private ConvertServiceImpl convertService;
    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> payload){
        String email = null;
        String password = null;
        try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
        UserDto userDto = new UserDto(email,password);
        return createAuthenticationToken(userDto);
    }

    private ResponseEntity<?> createAuthenticationToken(UserDto userDto) {
        String encodedPassword = userDto.getPassword();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), encodedPassword)
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new BadCredentialsException("Incorrect username or password")
                    , HttpStatus.FORBIDDEN);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userDto.getEmail());
        final String jwt = jwtTokenUtils.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody Map<String, Object> payload)
            throws Exception {
        System.out.println(payload);
        int phone;
        try{
            String rawNum = payload.get("phone").toString();
            phone= Integer.parseInt(rawNum);
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Invalid phone format")
                    , HttpStatus.FORBIDDEN);
        }
        String email = null;
        String password = null;
        String fullName = null;
        try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
            password = bCryptPasswordEncoder.encode(password);
            System.out.println(password);
            fullName = payload.get("fullName").toString();
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
        if(!userService.findByEmail(email).isEmpty()||
                !employeeService.findByCellPhone(phone).isEmpty())return new ResponseEntity<>(new Exception(
                "There is already an account with the email address: " + email+
                        " or phone number: "+ phone), HttpStatus.FORBIDDEN);
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(fullName,password,email,date);
        EmployeeDto employeeDto = new EmployeeDto(phone);
        User user = convertService.convertUserDtoToEntity(userDto);
        Employee employee = convertService.convertEmployeeDtoToEntity(employeeDto);
        employee.setUser(user);
        try{
            employeeService.save(employee);
            userService.save(user);
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Couldn't save to database"),HttpStatus.FORBIDDEN);
        }
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", true);
        objectNode.put("message", "Account creation successful");
        //return objectNode;
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    @RequestMapping(value = "/confirm", method= RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> confirmToken(@RequestBody Map<String, Object> payload) {
        UserDto userDto = new UserDto();
        try {
            String email = payload.get("email").toString();
            userDto.setEmail(email);
        } catch (Exception e) {
            return new ResponseEntity<>(new Exception("Missing email !")
                    , HttpStatus.FORBIDDEN);
        }
        User user = userService.findUserByEmail(userDto.getEmail());
        ObjectNode objectNode = mapper.createObjectNode();
        if (generateToken(user)) {
            String link = "http://localhost:8080/api/auth/confirm?token=" + user.getToken();
            emailService.send(
                    user.getEmail(),
                    buildEmail(user.getName(), link));
            objectNode.put("status", true);
            objectNode.put("message", "Email sent");
            return new ResponseEntity<>(objectNode, HttpStatus.OK);
        }
        objectNode.put("status", false);
        objectNode.put("message", "Token have not expired or user already verified");
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    @RequestMapping(value = "/confirm", method= RequestMethod.GET)
    @Transactional
    public ResponseEntity<?> confirmToken(@RequestParam(name= "token", required=true) String token){
        User userByToken = null;
        ObjectNode objectNode = mapper.createObjectNode();
        try {
            userByToken = userService.findUserByToken(UUID.fromString(token));
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Token not found !")
                    , HttpStatus.FORBIDDEN);
        }if(userByToken.isActivate()){
            objectNode.put("status", true);
            objectNode.put("message", "Email already verified");
            return new ResponseEntity<>(objectNode, HttpStatus.OK);
        }
        UUID userToken = userByToken.getToken();
        LocalDateTime userExpired = userByToken.getUpdatedAt();
        if(!userToken.equals(UUID.fromString(token)) || !userExpired.isBefore(LocalDateTime.now())){
            objectNode.put("status", false);
            objectNode.put("message", "Token doesn't match or have not expired yet !");
            return new ResponseEntity<>(objectNode, HttpStatus.CONFLICT);
        }
        userByToken.setActivate(true);
        userByToken.setUpdatedAt(LocalDateTime.now());
        objectNode.put("status", true);
        objectNode.put("message", "Email verified");
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    private boolean generateToken(User user){
        long dayToConfirm = 1;
        ObjectNode objectNode = mapper.createObjectNode();
        if((user.getToken() == null || user.getUpdatedAt().isAfter(LocalDateTime.now()))
            && !user.isActivate()){
            user.setToken(UUID.randomUUID());
            user.getUpdatedAt().plusDays(dayToConfirm);
            return true;

        }
        return false;
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
