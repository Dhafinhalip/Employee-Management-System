package com.enigmacamp.delivery.controller;

import com.enigmacamp.entity.Attendance;
import com.enigmacamp.entity.Employee;
import com.enigmacamp.service.AttendanceService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class AttendanceController {
    private final Scanner scanner = new Scanner(System.in);
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("========== ATTENDANCE MANAGEMENT ==========");
            System.out.println("1. ADD ATTENDANCE");
            System.out.println("2. GET ALL ATTENDANCE");
            System.out.println("3. GET ATTENDANCE BY ID");
            System.out.println("4. UPDATE ATTENDANCE");
            System.out.println("5. DELETE ATTENDANCE");
            System.out.println("0. EXIT");
            System.out.print("Choose menu: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> createHandler();
                case 2 -> listHandler();
                case 3 -> getByIdHandler();
                case 4 -> updateHandler();
                case 5 -> deleteHandler();
                case 0 -> {
                    System.out.println("Bye...");
                    return;
                }
                default -> System.out.println("Choose the right menu (0-5)!");
            }
        }
    }

    private void createHandler() {
        System.out.println("========== FORM ATTENDANCE ==========");
        System.out.print("Employee ID: ");
        Long id = Long.valueOf(scanner.nextLine());
        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Check In (HH::MM): ");
        LocalTime checkIn = LocalTime.parse(scanner.nextLine());
        System.out.print("Check Out (HH::MM): ");
        LocalTime checkOut = LocalTime.parse(scanner.nextLine());

        Employee employee = new Employee(id);
        var payload = new Attendance(employee, date, checkIn, checkOut);
        Attendance attendance = attendanceService.create(payload);
        System.out.println("Attendance created successfully!");
        System.out.println("Attendance ID: " + attendance.getId());
    }

    private void listHandler() {
        System.out.println("ID | Date             | Check In     | Check Out              | Employee");
        System.out.println("------------------------------------------------------");
        attendanceService.getAll().forEach(attendance ->
                System.out.printf("%d | %tF             | %tR     | %tR              | %s",
                attendance.getId(),
                attendance.getDate(),
                attendance.getCheckIn(),
                attendance.getCheckOut(),
                attendance.getEmployee().getFullname()));
    }

    private void getByIdHandler() {
        System.out.print("Id : ");
        Long id = Long.valueOf(scanner.nextLine());
        attendanceService.getById(id).ifPresentOrElse(
                attendance ->  System.out.println(
                        "ID : " + attendance.getId() +
                        "Check In : " + attendance.getCheckIn() +
                        "Check Out  : " + attendance.getCheckOut() +
                        "Employee  : " + attendance.getEmployee().getFullname()),
                () -> {
                    System.out.println(
                            "Employee with ID "
                                    + id
                                    + " not found."
                    );
                }
        );
    }

    private void updateHandler() {
        System.out.print("ID: ");
        Long id = Long.valueOf(scanner.nextLine());
        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Check In: ");
        LocalTime checkIn = LocalTime.parse(scanner.nextLine());
        System.out.print("Check Out: ");
        LocalTime checkOut = LocalTime.parse(scanner.nextLine());
        System.out.print("Employee ID: ");
        Long idEmployee = Long.valueOf(scanner.nextLine());

        Employee employee = new Employee(idEmployee);
        Attendance attendance = new Attendance(id, date, checkIn, checkOut, employee);

        try {
            Attendance updatedEmployee =
                    attendanceService.update(attendance);

            System.out.println("Attendance Successfully Update");
            System.out.println(updatedEmployee);
        }catch (IllegalArgumentException e) {
            System.out.println("Error : " + e.getMessage());
        }

    }

    private void deleteHandler() {
        System.out.print("Id : ");
        Long id = Long.valueOf(scanner.nextLine());
        try {
            attendanceService.getById(id).ifPresentOrElse(
                    employee -> attendanceService.delete(id),
                    () -> {
                        System.out.println(
                                "Attendance with ID "
                                        + id
                                        + " not found."
                        );
                    }
            );
        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
