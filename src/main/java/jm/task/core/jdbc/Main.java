package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    private static final   User user1= new User("Jul","Nor", (byte) 6);
    public static void main(String[] args) {
        userService.createUsersTable();

    }
}
