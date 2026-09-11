package com.enigmacamp.service.impl;

import com.enigmacamp.dao.AttendanceDao;
import com.enigmacamp.entity.Attendance;
import com.enigmacamp.service.AttendanceService;

import java.util.List;
import java.util.Optional;

public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceDao attendanceDao;
    private EmployeeServiceImpl employeeService;

    public AttendanceServiceImpl(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
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
        if (attendance.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Department ID cannot be 0 or negative"
            );
        }

        validateAttendance(attendance);

        return attendanceDao.update(attendance);
    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be greater than 0"
            );
        }

        attendanceDao.deleteById(id);
    }

    private void validateAttendance(Attendance attendance) {
        if (attendance == null) {
            throw new IllegalArgumentException(
                    "Student cannot be null"
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
                    "Employee Attendance cannot be empty"
            );
        }

        if (attendance.getCheckOut() == null ||
                attendance.getCheckOut().toString().isBlank()) {
            throw new IllegalArgumentException(
                    "Employee Attendance cannot be empty"
            );
        }

        employeeService.validateEmployee(attendance.getEmployee());

    }
}
