package com.enigmacamp.service;

import com.enigmacamp.entity.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    Attendance create(Attendance attendance);

    List<Attendance> getAll();

    Optional<Attendance> getById(Long id);

    Attendance update(Attendance attendance);

    void delete(Long id);
}
