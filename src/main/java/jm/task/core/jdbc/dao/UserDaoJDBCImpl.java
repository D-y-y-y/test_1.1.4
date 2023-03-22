package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import javax.transaction.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test?useSSL=false&serverTimezone=UTC", "root", "1234")) {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE test.users (\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  name VARCHAR(45),\n" +
                "  lastName VARCHAR(45),\n" +
                "  age INT,\n" +
                "  PRIMARY KEY (id))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;");
            statement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test?useSSL=false&serverTimezone=UTC", "root", "1234")) {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS users");
            statement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test?useSSL=false&serverTimezone=UTC", "root", "1234")) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test?useSSL=false&serverTimezone=UTC", "root", "1234")) {
            PreparedStatement preparedStat = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStat.setLong(1, id);
            preparedStat.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test?useSSL=false&serverTimezone=UTC", "root", "1234")) {
            PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = prStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test?useSSL=false&serverTimezone=UTC", "root", "1234")) {
            PreparedStatement prStatement = connection.prepareStatement("TRUNCATE TABLE users;");
            prStatement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
