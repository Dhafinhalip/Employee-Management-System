package com.enigmacamp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullname;
    private String email;
    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "employee")
    private Attendance attendance;

    public Employee() {
    }

    public Employee(String fullname, String email, String address, Department department) {
        this.fullname = fullname;
        this.email = email;
        this.address = address;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
