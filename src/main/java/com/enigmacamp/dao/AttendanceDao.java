package com.enigmacamp.dao;

import com.enigmacamp.entity.Attendance;


import java.util.List;
import java.util.Optional;

public interface AttendanceDao {
    Attendance save(Attendance attendance);

    List<Attendance> findAll();

    Optional<Attendance> findById(Long id);

    Attendance update(Attendance attendance);

    void deleteById(Long id);

}
