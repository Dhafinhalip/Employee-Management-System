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
            System.out.println("4. GET EMPLOYEE BY NAME");
            System.out.println("5. GET EMPLOYEE BY EMAIL");
            System.out.println("6. GET EMPLOYEES BY DEPARTMENT");
            System.out.println("7. UPDATE EMPLOYEE");
            System.out.println("8. DELETE EMPLOYEE");
            System.out.println("0. EXIT");
            System.out.print("Choose menu: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> createHandler();
                case 2 -> listHandler();
                case 3 -> getByIdHandler();
                case 4 -> getByNameHandler();
                case 5 -> getByEmailHandler();
                case 6 -> getByDepartmentHandler();
                case 7 -> updateHandler();
                case 8 -> deleteHandler();
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
        System.out.println("\t\t\t\t\tEMPLOYEE TABLE");
        System.out.println("----------------------------------------------------");
        System.out.println("ID  |   Full Name   |       Email        | Department");
        System.out.println("----------------------------------------------------");

        if (employees.isEmpty()) {
            System.out.println("\t\t\t DATA IS EMPTY");
        }

        for (Employee employee : employees) {
            System.out.printf(
                    "%d   |  %s |  %s  |    %s", employee.getId(),
                    employee.getFullname(),
                    employee.getEmail(),
                    employee.getDepartment().getName() + "\n");
        }
    }

    private void getByIdHandler() {
        try {
            System.out.print("Id : ");
            Long id = Long.valueOf(scanner.nextLine());

            Employee employee = employeeService.getById(id);

            if (employee == null) {
                System.out.println("Employee with ID " + id + " Not Found ");
                return;
            }

            System.out.println(
                "Employee ID : " + employee.getId() +
                "\nFull Name : " + employee.getFullname() +
                "\nEmail  : " + employee.getEmail() +
                "\nAddress : " + employee.getAddress() +
                "\nDepartment : " + employee.getDepartment().getName());

        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void getByNameHandler() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            Employee employee = employeeService.getByName(name);

            if (employee == null) {
                System.out.println("Employee with name : " + name + " Not Found ");
                return;
            }

            System.out.println(
                    "Employee ID : " + employee.getId() +
                            "\nFull Name : " + employee.getFullname() +
                            "\nEmail  : " + employee.getEmail() +
                            "\nAddress : " + employee.getAddress() +
                            "\nDepartment : " + employee.getDepartment().getName());

        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void getByEmailHandler() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Employee employee = employeeService.getByName(email);

            if (employee == null) {
                System.out.println("Employee with email : " + email + " Not Found ");
                return;
            }

            System.out.println(
                    "Employee ID : " + employee.getId() +
                            "\nFull Name : " + employee.getFullname() +
                            "\nEmail  : " + employee.getEmail() +
                            "\nAddress : " + employee.getAddress() +
                            "\nDepartment : " + employee.getDepartment().getName());

        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void getByDepartmentHandler() {

        try {
            System.out.print("Department Name : ");
            String department = scanner.nextLine();
            int id = 1;

            List<Employee> employees = employeeService.getAll();
            List<Employee> employeesByDepartment = employees.stream().filter(employee -> employee
                    .getDepartment().getName().equals(department.toUpperCase())).toList();

            if (employeesByDepartment.isEmpty()) {
                System.out.println("Employees with department name : " + department + " Not Found ");
                return;
            }

            System.out.println("----------------------------------------------------");
            System.out.println("\t\t\t\t\tEMPLOYEE TABLE");
            System.out.println("----------------------------------------------------");
            System.out.println("NO |   Full Name   |       Email        | Department");
            System.out.println("----------------------------------------------------");

            for (Employee employee : employeesByDepartment) {
                System.out.printf(
                        "%d  |  %s |  %s  |    %s", id,
                        employee.getFullname(),
                        employee.getEmail(),
                        employee.getDepartment().getName() + "\n");
                id++;
            }

        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void updateHandler() {

        try {
            Employee updatedEmployee = null;
            int choice;

            System.out.print("Id: ");
            Long id = Long.valueOf(scanner.nextLine());

            Employee employee = employeeService.getById(id);

            if (employee == null) {
                System.out.println("Employee with ID " + id + " Not Found ");
                return;
            }

            do {
                System.out.println("========== EMPLOYEE UPDATE ==========");
                System.out.println("1. UPDATE NAME");
                System.out.println("2. UPDATE EMAIL");
                System.out.println("3. UPDATE ADDRESS");
                System.out.println("4. UPDATE DEPARTMENT");
                System.out.println("0. EXIT");
                System.out.print("Choose menu: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        employee.setFullname(name);
                        updatedEmployee = employeeService.update(employee);
                        break;
                    case 2:
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        employee.setEmail(email);
                        updatedEmployee = employeeService.update(employee);
                        break;
                    case 3:
                        System.out.print("Address: ");
                        String address = scanner.nextLine();
                        employee.setAddress(address);
                        updatedEmployee = employeeService.update(employee);
                        break;
                    case 4:
                        System.out.print("Department Id : ");
                        Long idDepartment = Long.valueOf(scanner.nextLine());

                        Department department = new Department(idDepartment);
                        employee.setDepartment(department);
                        updatedEmployee = employeeService.update(employee);
                        break;
                    case 0:
                        System.out.println("Bye...");
                        break;
                    default:
                        System.out.println("Choose the right menu (0-5)!");
                }

                if (updatedEmployee == null) {
                    return;
                }

                System.out.println("Employee is Succesfully Update");
                System.out.println(
                        "Employee ID : " + updatedEmployee.getId() +
                        "\nFull Name : " + updatedEmployee.getFullname() +
                        "\nEmail  : " + updatedEmployee.getEmail() +
                        "\nAddress : " + updatedEmployee.getAddress() +
                        "\nDepartment : " + updatedEmployee.getDepartment().getName());

            } while (choice != 0);

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void deleteHandler() {
        try {
            System.out.print("Id : ");
            Long id = Long.valueOf(scanner.nextLine());

            Employee employee = employeeService.getById(id);

            if (employee == null) {
                System.out.println("Employee with ID " + id + " Not Found ");
                return;
            }

            employeeService.delete(id);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


}
