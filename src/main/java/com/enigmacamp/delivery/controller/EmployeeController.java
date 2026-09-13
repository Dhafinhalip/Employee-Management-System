package com.enigmacamp.delivery.controller;

import com.enigmacamp.entity.Department;
import com.enigmacamp.entity.Employee;
import com.enigmacamp.service.EmployeeService;

import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    private final Scanner scanner = new Scanner(System.in);
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("========== EMPLOYEE MANAGEMENT ==========");
            System.out.println("1. ADD EMPLOYEE");
            System.out.println("2. GET ALL EMPLOYEE");
            System.out.println("3. GET EMPLOYEE BY ID");
            System.out.println("4. UPDATE EMPLOYEE");
            System.out.println("5. DELETE EMPLOYEE");
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
        try {
            System.out.println("========== FORM EMPLOYEE ==========");
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Address: ");
            String address = scanner.nextLine();

            System.out.print("Department Id : ");
            Long id = Long.valueOf(scanner.nextLine());

            Department department = new Department(id);
            var payload = new Employee(name, email, address, department);

            Employee employee = employeeService.create(payload);
            System.out.println("Employee created successfully!");
            System.out.println("Employee ID: " + employee.getId());
        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void listHandler() {
        List<Employee> employees = employeeService.getAll();
        System.out.println("----------------------------------------------------");
        System.out.println("\t\t\t\t EMPLOYEE TABLE");
        System.out.println("----------------------------------------------------");
        System.out.println("ID |   Full Name   |       Email        | Department");
        System.out.println("----------------------------------------------------");

        if (employees.isEmpty()) {
            System.out.println("\t\t\t DATA IS EMPTY");
        }

        for (Employee employee : employees) {
            System.out.printf(
                    "%d  |  %s |  %s  |    %s", employee.getId(),
                    employee.getFullname(),
                    employee.getEmail(),
                    employee.getDepartment().getName() + "\n");
        }
    }

    private void getByIdHandler() {
        try {
            System.out.print("Id : ");
            Long id = Long.valueOf(scanner.nextLine());
            employeeService.getById(id).ifPresentOrElse(employee -> System.out.println(
                            "Employee ID : " + employee.getId() +
                            "\nFull Name : " + employee.getFullname() +
                            "\nEmail  : " + employee.getEmail() +
                            "\nAddress : " + employee.getAddress() +
                            "\nDepartment : " + employee.getDepartment().getName()),
                    () -> {
                        System.out.println(
                                "Employee with ID "
                                        + id
                                        + " not found."
                        );
                    }
            );
        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void updateHandler() {

        try {
            System.out.print("Id: ");
            Long id = Long.valueOf(scanner.nextLine());

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Address: ");
            String address = scanner.nextLine();

            System.out.print("Department Id : ");
            Long idDepartment = Long.valueOf(scanner.nextLine());

            Department department = new Department(idDepartment);

            Employee employee = new Employee(id, name, email, address, department);
            Employee updatedEmployee = employeeService.update(employee);

            System.out.println("Employee is Succesfully Update");
            System.out.println(
                    "Employee ID : " + updatedEmployee.getId() +
                    "\nFull Name : " + updatedEmployee.getFullname() +
                    "\nEmail  : " + updatedEmployee.getEmail() +
                    "\nAddress : " + updatedEmployee.getAddress() +
                    "\nDepartment : " + updatedEmployee.getDepartment().getName());

        } catch (IllegalArgumentException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void deleteHandler() {
        try {
            System.out.print("Id : ");
            Long id = Long.valueOf(scanner.nextLine());

            employeeService.getById(id).ifPresentOrElse(
                    employee -> employeeService.delete(id),
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


}
