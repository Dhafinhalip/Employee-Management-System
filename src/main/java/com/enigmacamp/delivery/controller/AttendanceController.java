package com.enigmacamp.delivery.controller;

import com.enigmacamp.entity.Attendance;
import com.enigmacamp.entity.Employee;
import com.enigmacamp.service.AttendanceService;
import com.enigmacamp.service.EmployeeService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class AttendanceController {
    private final Scanner scanner = new Scanner(System.in);
    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    public AttendanceController(AttendanceService attendanceService, EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("========== ATTENDANCE MANAGEMENT ==========");
            System.out.println("1. ADD ATTENDANCE");
            System.out.println("2. GET ALL ATTENDANCE");
            System.out.println("3. GET ATTENDANCE BY ID");
            System.out.println("4. UPDATE ATTENDANCE");
            System.out.println("5. DELETE ATTENDANCE");
            System.out.println("6. COUNT LATE EMPLOYEE");
            System.out.println("7. COUNT NEVER ATTENDED EMPLOYEE");
            System.out.println("8. TOTAL WORKING HOURS EMPLOYEE");
            System.out.println("9. MOST DILIGENT EMPLOYEE");
            System.out.println("0. EXIT");
            System.out.print("Choose menu: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> createHandler();
                case 2 -> listHandler();
                case 3 -> getByIdHandler();
                case 4 -> updateHandler();
                case 5 -> deleteHandler();
                case 6 -> countLateEmployee();
                case 7 -> countNeverAttended();
                case 8 -> totalWorkingHours();
                case 9 -> mostDiligentEmployee();
                case 0 -> {
                    System.out.println("Bye...");
                    return;
                }
                default -> System.out.println("Choose the right menu (0-5)!");
            }
        }
    }

    private void createHandler() {
        try {
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
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void listHandler() {
        List<Attendance> attendances = attendanceService.getAll();
        System.out.println("-----------------------------------------------------");
        System.out.println("\t\t\t\t ATTENDANCE TABLE");
        System.out.println("-----------------------------------------------------");
        System.out.println("ID |    Date    | Check In | Check Out |   Employee");
        System.out.println("-----------------------------------------------------");

        if (attendances.isEmpty()) {
            System.out.println("\t\t\t\t DATA IS EMPTY");
        }

        for (Attendance attendance : attendances) {
            System.out.printf("%d  | %tF |   %tR  |   %tR   | %s",
                    attendance.getId(),
                    attendance.getDate(),
                    attendance.getCheckIn(),
                    attendance.getCheckOut(),
                    attendance.getEmployee().getFullname() + "\n");
        }
    }

    private void getByIdHandler() {
        try {
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
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void updateHandler() {
        try {
            System.out.print("ID: ");
            Long id = Long.valueOf(scanner.nextLine());

            System.out.print("Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Check In (HH::MM): ");
            LocalTime checkIn = LocalTime.parse(scanner.nextLine());

            System.out.print("Check Out(HH::MM): ");
            LocalTime checkOut = LocalTime.parse(scanner.nextLine());

            System.out.print("Employee ID: ");
            Long idEmployee = Long.valueOf(scanner.nextLine());

            Employee employee = new Employee(idEmployee);
            Attendance attendance = new Attendance(id, date, checkIn, checkOut, employee);

            Attendance updatedAttendance = attendanceService.update(attendance);

            System.out.println("Attendance Successfully Update");
            System.out.println(updatedAttendance);
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


    private void countLateEmployee() {
        List<Attendance> allAttendance = attendanceService.getAll();

        long totalEmployeeLate = allAttendance.stream().filter(attendance -> attendance.getCheckIn().isAfter(LocalTime.of(9,0))).count();

        System.out.println("Total late employees : " + totalEmployeeLate);
    }

    private void countNeverAttended() {
        List<Attendance> allAttendance = attendanceService.getAll();
        List<Employee> allEmployee = employeeService.getAll();

        long totalNeverAttended = allEmployee.stream().filter(employee -> allAttendance.stream()
                .noneMatch(attendance ->
                attendance.getEmployee().getId() ==
                employee.getId())).count();

        System.out.println("Employees Who Never Attended : " + totalNeverAttended);
    }

    private void totalWorkingHours() {
        List<Attendance> allAttendance = attendanceService.getAll();

        System.out.println("----------------------------------");
        System.out.println("ID |    Employee    | Working Hour");
        System.out.println("----------------------------------");

        allAttendance.stream().collect(Collectors.groupingBy(Attendance::getEmployee, Collectors.summingDouble(attendance -> {
            long minutes = Duration.between(attendance.getCheckIn(), attendance.getCheckOut()).toMinutes();
            return minutes / 60.0;
        }))).entrySet().stream().sorted(Comparator.comparingLong(map -> map.getKey().getId())).forEach((map) -> {
            System.out.printf("%d  |  %s  |    %.1f \n", map.getKey().getId(), map.getKey().getFullname(), map.getValue());
        });
    }

    private void mostDiligentEmployee() {
        List<Attendance> allAttendance = attendanceService.getAll();

        allAttendance.stream().collect(Collectors.groupingBy(attendance -> attendance.getDate().getMonth(),
                Collectors.groupingBy(attendance -> attendance.getEmployee().getFullname(),
                Collectors.summingDouble(attendance -> {
                    long minutes = Duration.between(attendance.getCheckIn(), attendance.getCheckOut()).toMinutes();
                    return minutes / 60.0;
                })))).forEach((month, map) -> {
                System.out.println("--------------------------");
                System.out.println("\t\t" + month);
                System.out.println("--------------------------");

                Map<String, Double> diligentEmployee = map.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(1).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2) -> e1,
                        LinkedHashMap::new
                ));

                diligentEmployee.forEach((k,v) -> {
                    System.out.println("Most diligent employee :");
                    System.out.println(k);
                    System.out.println("Total Working Hours : " + v);
                });
        });
    }
}
