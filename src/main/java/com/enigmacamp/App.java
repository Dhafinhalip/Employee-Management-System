package com.enigmacamp;

import com.enigmacamp.delivery.Server;

public class App {

    public static void main(String[] args) {
        System.out.println("The application is starting ...");

        Server server = Server.serve();
        server.run();

        System.out.println("The application is ended!");
    }
}

