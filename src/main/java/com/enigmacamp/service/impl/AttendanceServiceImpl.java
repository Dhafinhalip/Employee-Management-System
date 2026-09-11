package com.enigmacamp.service.impl;

import com.enigmacamp.dao.AttendanceDao;
import com.enigmacamp.entity.Attendance;
import com.enigmacamp.service.AttendanceService;
import com.enigmacamp.service.EmployeeService;

import java.util.List;
import java.util.Optional;

public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceDao attendanceDao;
    private EmployeeService employeeService;

    public AttendanceServiceImpl(AttendanceDao attendanceDao, EmployeeService employeeService) {
        this.attendanceDao = attendanceDao;
        this.employeeService = employeeService;
    }

    @Override
    public Attendance create(Attendance attendance) {
        validateAttendance(attendance);

        return attendanceDao.save(attendance);
    }

    @Override
    public List<Attendance> getAll() {
        return attendanceDao.findAll();
    }

    @Override
    public Optional<Attendance> getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Attendance ID must be greater than 0"
            );
        }

        return attendanceDao.findById(id);
    }

    @Override
    public Attendance update(Attendance attendance) {
        if (getById(attendance.getId()).isEmpty()) {
            throw new IllegalArgumentException(
                    "Department with ID" + attendance.getId() + "Not Found"
            );
        }

        validateAttendance(attendance);

        return attendanceDao.update(attendance);

    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Attendance ID must be greater than 0"
            );
        }

        try {
            attendanceDao.deleteById(id);
            System.out.println("Attendance Data Successfully Delete");
        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void validateAttendance(Attendance attendance) {
        if (attendance == null) {
            throw new IllegalArgumentException(
                    "Student cannot be null"
            );
        }

        if (employeeService.getById(attendance.getEmployee().getId()).isEmpty()) {
            throw new NullPointerException(
                    "Employee must exist."
            );
        }

        if (attendance.getDate() == null ||
                attendance.getDate().toString().isEmpty()) {

            throw new IllegalArgumentException(
                    "Date cannot be empty"
            );
        }

        if (attendance.getCheckIn() == null ||
                attendance.getCheckIn().toString().isBlank()) {
            throw new IllegalArgumentException(
                    "checkIn cannot be empty"
            );
        }

        if (attendance.getCheckOut() == null ||
                attendance.getCheckOut().toString().isBlank()) {
            throw new IllegalArgumentException(
                    "checkOut cannot be empty"
            );
        }


    }
}
