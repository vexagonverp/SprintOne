package com.net.SprintOne.service.serviceImpl;

import com.net.SprintOne.model.Role;
import com.net.SprintOne.model.RoleDto;
import com.net.SprintOne.repositories.RoleRepository;
import com.net.SprintOne.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ConvertServiceImpl convertService;



    @Override
    public List<RoleDto> findAll(){
        List<Role> roles = roleRepository.findAll();
        return convertService.convertRoleListToDto(roles);
    }

    public void save(Role role){
        roleRepository.save(role);
    }

    public List<RoleDto> findByName(String name){
        List<Role> roles = roleRepository.findByName(name);
        return convertService.convertRoleListToDto(roles);
    }
}
