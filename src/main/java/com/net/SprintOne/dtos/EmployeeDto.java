package com.net.SprintOne.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.net.SprintOne.model.Employee;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class EmployeeDto implements Serializable {

    private long id;

    private UUID id_number;

    private String full_name;

    private String address;

    private String department;

    private String gender;

    private String job_title;

    private String place_of_birth;

    private String race;

    private String religion;

    private Date birth_day;

    private Date contracted_date;

    private Date start_date;

    private int cell_phone;



    private Employee manager_id;


    private Set<EmployeeDto> subordinates;

    @JsonIgnore
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public int getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(int cell_phone) {
        this.cell_phone = cell_phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getId_number() {
        return id_number;
    }

    public void setId_number(UUID id_number) {
        this.id_number = id_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Date getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(Date birth_day) {
        this.birth_day = birth_day;
    }

    public Date getContracted_date() {
        return contracted_date;
    }

    public void setContracted_date(Date contracted_date) {
        this.contracted_date = contracted_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Employee getManager_id() {
        return manager_id;
    }

    public void setManager_id(Employee manager_id) {
        this.manager_id = manager_id;
    }

    public Set<EmployeeDto> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }

    public EmployeeDto(){

    }
    public EmployeeDto(int cell_phone) {
        this.cell_phone = cell_phone;
        this.id_number = UUID.randomUUID();//chance of encountering duplicate is rarer than getting hit by meteor.
    }

    public EmployeeDto(String full_name, String address, String department, String gender, String job_title, String place_of_birth, String race, String religion, Date birth_day, Date contracted_date, Date start_date, int cell_phone) {
        this.id_number = UUID.randomUUID();
        this.full_name = full_name;
        this.address = address;
        this.department = department;
        this.gender = gender;
        this.job_title = job_title;
        this.place_of_birth = place_of_birth;
        this.race = race;
        this.religion = religion;
        this.birth_day = birth_day;
        this.contracted_date = contracted_date;
        this.start_date = start_date;
        this.cell_phone = cell_phone;
    }
}
