package com.enigmacamp.delivery;

import com.enigmacamp.config.JPAConfig;
import com.enigmacamp.dao.AttendanceDao;
import com.enigmacamp.dao.DepartementDao;
import com.enigmacamp.dao.EmployeeDao;
import com.enigmacamp.dao.impl.AttendanceDaoImpl;
import com.enigmacamp.dao.impl.DepartementDaoImpl;
import com.enigmacamp.dao.impl.EmployeeDaoImpl;
import com.enigmacamp.delivery.controller.AttendanceController;
import com.enigmacamp.delivery.controller.DepartmentController;
import com.enigmacamp.delivery.controller.EmployeeController;
import com.enigmacamp.service.AttendanceService;
import com.enigmacamp.service.DepartementService;
import com.enigmacamp.service.EmployeeService;
import com.enigmacamp.service.impl.AttendanceServiceImpl;
import com.enigmacamp.service.impl.DepartementServiceImpl;
import com.enigmacamp.service.impl.EmployeeServiceImpl;

import java.util.Scanner;

public class Server {
    private final Scanner scanner = new Scanner(System.in);
    private final AttendanceService attendanceService;
    private final DepartementService departementService;
    private final EmployeeService employeeService;

    public Server(AttendanceService attendanceService, DepartementService departementService, EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.departementService = departementService;
        this.employeeService = employeeService;
    }

    public void run() {
        while (true) {
            System.out.println("========== EMPLOYEE MANAGEMENT SYSTEM ==========");
            System.out.println("1. EMPLOYEE MANAGEMENT");
            System.out.println("2. DEPARTMENT MANAGEMENT");
            System.out.println("3. ATTENDANCE MANAGEMENT");
            System.out.println("0. EXIT");
            System.out.print("Choose menu: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> new EmployeeController(employeeService).showMenu();
                case 2 -> new DepartmentController(departementService).showMenu();
                case 3 -> new AttendanceController(attendanceService).showMenu();
                case 0 -> {
                    System.out.println("Bye...");
                    JPAConfig.disconnect();
                    return;
                }
                default -> System.out.println("Choose the right menu (0-3)!");
            }
        }
    }


    public static Server serve() {
        //Department
        DepartementDao departementDao = new DepartementDaoImpl();
        DepartementService departementService = new DepartementServiceImpl(departementDao);

        //Employee
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeDao, departementService);

        //Attendance
        AttendanceDao attendanceDao = new AttendanceDaoImpl();
        AttendanceService attendanceService = new AttendanceServiceImpl(attendanceDao, employeeService);

        return new Server(attendanceService, departementService, employeeService);
    }

}
