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


        try (Statement statement = connection.createStatement()) { // делаем соединение
            statement.executeUpdate( "CREATE TABLE IF NOT EXISTS users (" +
                    " id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(20)NOT NULL," +
                    "lastname VARCHAR(20) NOT NULL," +
                    "age TINYINT NOT NULL," +
                    "PRIMARY KEY (id));");
            System.out.println("Успешно");


        } catch (SQLException e) {
            System.out.printf("Ошибка при создании пользователя");
            e.printStackTrace();
        }

    }

    public void dropUsersTable() throws SQLException {
        try  (Statement statement = connection.createStatement()) {


            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(); //транзакции откатить все изменения
        }


        }



    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO users(name, lastname, age) VALUES (?,?,?)")) { // вставить в поля следующин значения

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);

            preparedStatement.executeUpdate();
            System.out.println("User c именем " + name + "добавлен в базу данных");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE id");//берет в параметрах и засовывет в запрос sql


            System.out.printf("User с %d - удален\n", id); // удален и переход на новую строку
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

        public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery( "SELECT id, name, lastname, age FROM users"); // получать определенные данные из таблицы
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));//дали юзеру айди
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
        try (Statement statement = connection.createStatement()){ // здесь чистим страницу
            statement.executeUpdate("TRUNCATE users");
            System.out.println("Таблица пустая");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
