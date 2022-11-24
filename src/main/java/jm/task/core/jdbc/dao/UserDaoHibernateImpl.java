package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaQuery;

import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaUpdate;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Transaction transaction = null;



    public UserDaoHibernateImpl() { // пустой

    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45) NULL," +
                    "lastname VARCHAR(45) NULL," +
                    "age INT (3) NULL," +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Table created");
        }catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }


        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Table deleted");

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name,lastName,age);
            session.save(user);
            transaction.commit();
            System.out.println("User saved");


        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("User deleted");

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
       List<User> result = new ArrayList<>();

       try (Session session = Util.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();
           result = session.createQuery("from User", User.class).list();
           session.getTransaction().commit();
       }catch (HibernateException e) {
           if (transaction != null) {
               transaction.rollback();
           }


        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User ").executeUpdate();
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }


        }

    }
}
