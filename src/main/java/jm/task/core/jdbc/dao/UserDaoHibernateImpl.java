package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {



    public UserDaoHibernateImpl() { // пустой

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45) NULL," +
                    "lastname VARCHAR(45) NULL," +
                    "age INT (3) NULL," +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();

        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();

            System.out.println("User c именем - " + name + " добавлен в базе данных");
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();

        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User>userList;

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            userList = session.createSQLQuery("FROM User").list();
            transaction.commit();


        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();

        }

    }
}
