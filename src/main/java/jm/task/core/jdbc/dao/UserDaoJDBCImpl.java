package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection = Util.getConnection();





    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() { //создайте


        try (Statement statement = connection.createStatement()) {
            String sgl = "CREATE TABLE IF NOT EXISTS Nor.User" + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, nameVARCHAR(140),lastName VARCHAR(145),age INT)";
            statement.executeUpdate(sgl);

        } catch (SQLException e) {
            System.out.printf("Ошибка при создании пользователя");
            e.printStackTrace();
        }

    }

    public void dropUsersTable() throws SQLException {
        try  (Statement statement = connection.createStatement()) {


            statement.executeUpdate("DROP TABLE IF EXISTS User");
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(); //транзакции откатить все изменения
        }


        }



    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO users VALUES (?,?,?)")) {

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3, String.valueOf(age));

            preparedStatement.executeUpdate();
            System.out.println("User c именем " + name + "добавлен в базу данных");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM User WHERE id");//берет в параметрах и засовывет в запрос sql


            System.out.printf("User с %d - удален\n", id);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

        public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery( "SELECT id, name, lastname, age");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));//дали юзеру ади
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

            return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE User");
            System.out.println("Таблица пустая");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
