package com.enigmacamp.delivery.controller;

import com.enigmacamp.entity.Department;
import com.enigmacamp.service.DepartementService;

import java.util.List;
import java.util.Scanner;

public class DepartmentController {
    private final Scanner scanner = new Scanner(System.in);
    private final DepartementService departementService;

    public DepartmentController(DepartementService departementService) {
        this.departementService = departementService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("========== DEPARTMENT MANAGEMENT ==========");
            System.out.println("1. ADD DEPARTMENT");
            System.out.println("2. GET ALL DEPARTMENT");
            System.out.println("3. GET DEPARTMENT BY ID");
            System.out.println("4. UPDATE DEPARTMENT");
            System.out.println("5. DELETE DEPARTMENT");
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
            System.out.println("========== FORM DEPARTMENT ==========");

            System.out.print("Department Name: ");
            String name = scanner.nextLine();

            var payload = new Department(name.toUpperCase());
            Department department =  departementService.create(payload);

            System.out.println("Departement created successfully!");
            System.out.println("Departement ID: " + department.getId());
        }catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void listHandler() {
        List<Department> departments = departementService.getAll();
        System.out.println("----------------------");
        System.out.println("   DEPARTMENT TABLE");
        System.out.println("----------------------");
        System.out.println("ID | Department Name  ");
        System.out.println("----------------------");

        if (departments.isEmpty()) {
            System.out.println("\t DATA IS EMPTY");
        }

        for (Department department : departments) {
            System.out.printf("%d  | %s \n", department.getId(),
                    department.getName());
        }
    }

    private void getByIdHandler() {
        try {
            System.out.print("Id : ");
            Long id = Long.valueOf(scanner.nextLine());

            departementService.getById(id).ifPresentOrElse(
                    department -> System.out.println(
                            "Department ID : " + department.getId() + "\n" +
                            "Department Name : " + department.getName()),
                    () -> {
                        System.out.println(
                                "Department with ID "
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

            System.out.print("Department Name: ");
            String name = scanner.nextLine();

            Department department = new Department(id, name.toUpperCase());
            Department updatedDepartment = departementService.update(department);

            System.out.println("Department Succesfully Update");
            System.out.println(
                    "Department ID : " + updatedDepartment.getId() + "\n" +
                    "Department Name : " + updatedDepartment.getName());
        } catch (IllegalArgumentException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void deleteHandler() {
        try {
            System.out.print("Id : ");
            Long id = Long.valueOf(scanner.nextLine());

            departementService.getById(id).ifPresentOrElse(
                    employee -> departementService.delete(id),
                    () -> {
                        System.out.println(
                                "Department with ID "
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
