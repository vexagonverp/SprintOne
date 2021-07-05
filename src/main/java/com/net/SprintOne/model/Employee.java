package com.net.SprintOne.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity(name="Employee")
@Table(name="employeesdetail",
        uniqueConstraints = {
                @UniqueConstraint(name="id_number_unique",columnNames = "id_number"),
                @UniqueConstraint(name="cell_phone_unique",columnNames = "cell_phone")
        }
)
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name="id",
            updatable = false
    )
    private long id;
    @Column(
            name = "id_number",
            updatable = false,
            nullable = false
    )
    private UUID id_number;
    @Column(
            name="full_name",
            columnDefinition="TEXT"
    )
    private String full_name;
    @Column(
            name="address",
            columnDefinition="TEXT"
    )
    private String address;
    @Column(
            name="department",
            columnDefinition="TEXT"
    )
    private String department;
    @Column(
            name="gender",
            columnDefinition="TEXT"
    )
    private String gender;
    @Column(
            name="job_title",
            columnDefinition="TEXT"
    )
    private String job_title;
    @Column(
            name="place_of_birth",
            columnDefinition="TEXT"
    )
    private String place_of_birth;
    @Column(
            name="race",
            columnDefinition="TEXT"
    )
    private String race;
    @Column(
            name="religion",
            columnDefinition="TEXT"
    )
    private String religion;
    @Column(
            name="birth_day"
    )
    private Date birth_day;
    @Column(
            name="contracted_date"
    )
    private Date contracted_date;
    @Column(
            name="start_date"
    )
    private Date start_date;
    @Column(
            name="cell_phone"
    )
    private int cell_phone;


    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="manager_id")
    private Employee manager_id;

    @OneToMany(mappedBy="manager_id")
    private Set<Employee> subordinates;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id")
    @MapsId
    @JsonIgnore
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public Employee(){

    }
    public Employee(User user,int cell_phone) {
        this.cell_phone = cell_phone;
        this.user = user;
        this.id_number = UUID.randomUUID();//chance of encountering duplicate is rarer than getting hit by meteor.
    }

    public Employee(String full_name, String address, String department, String gender, String job_title, String place_of_birth, String race, String religion, Date birth_day, Date contracted_date, Date start_date, int cell_phone) {
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
