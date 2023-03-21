package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Budky", (byte) 60);
        userService.saveUser("Valentina", "Budko", (byte) 60);
        userService.saveUser("Olga", "Kovaleva", (byte) 60);
        userService.saveUser("Yuri", "Kovalev", (byte) 60);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
