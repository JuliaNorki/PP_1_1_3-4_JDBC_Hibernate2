package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    private static final   User user1= new User("Jul1","Nor1", (byte) 1);
    private static final   User user2= new User("Jul2","Nor2", (byte) 2);
    private static final   User user3= new User("Jul3","Nor3", (byte) 3);
    private static final   User user4= new User("Jul4","Nor4", (byte) 4);//проолрлор
    private static final   User user5= new User("Jul5","Nor5", (byte) 5);//прорроророооооо
    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(),user1.getAge());
        userService.saveUser(user2.getName(),user2.getLastName(),user2.getAge());
        userService.saveUser(user3.getName(),user3.getLastName(),user3.getAge());
        userService.saveUser(user4.getName(),user4.getLastName(),user4.getAge());

        userService.getAllUsers();
       // userService.cleanUsersTable();
       // userService.dropUsersTable();

    }
}
