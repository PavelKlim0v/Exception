package com.teachmeskills.lesson_21.task_2.service;

import com.teachmeskills.lesson_21.task_2.service.connector.MySQLConnector;
import com.teachmeskills.lesson_21.task_2.entity.User;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserCRUDService {

    private static final String INSERT_USER = "INSERT INTO user (name, email) VALUES (?, ?)";
    private static final String GET_DATA_USER = "SELECT * FROM user WHERE id = ?";
    private static final String GET_DATA_ALL_USER = "SELECT * FROM user";
    private static final String UPDATE_DATA_USER = "UPDATE user SET name = ?, email = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String DELETE_ALL_USERS = "DELETE FROM user";
    private static User user = null;
    // TODO: не должно быть общим. У него маленький жизненный цикл. Нужно каждый раз создавать.
    private PreparedStatement preparedStatement = null;
    private MySQLConnector connector;
    // TODO: В этих полях нет смысла.
    private int idInnerMethod;
    private String nameInnerMethod;
    private String emailInnerMethod;

    public UserCRUDService () {
        connector = new MySQLConnector();
    }

    // TODO: доделать. Правильная мысль.
//    // закрытие потоков класса
//    public void closeThreadsClassCRUD() {
//        try {
//            preparedStatement.close();
//            connector = null;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    // добавление нового пользователя
    public void createUser(User user) {
        try {
            preparedStatement = connector.getConnection().prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private User getUserData(ResultSet resultSet) throws SQLException {
        idInnerMethod = resultSet.getInt("id");
        nameInnerMethod = resultSet.getString("name");
        emailInnerMethod = resultSet.getString("email");
        return new User(idInnerMethod, nameInnerMethod, emailInnerMethod);
    }

    // вывести в консоль данные пользователя по id (все данные по пользователю)
    public User getUserById(int id) {
        try {
            Statement statement = connector.getConnection().createStatement();
            preparedStatement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery(GET_DATA_USER);
            user = new User();
            return user = getUserData(resultSet);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // вывести в консоль список всех пользователей (все данные пользователей)
    public List<User> getDataAllUsers() {
        try {
            Statement statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_DATA_ALL_USER);

            List<User> listUsers = new ArrayList<>();
            while (resultSet.next()) {
                user = new User();
                user = getUserData(resultSet);
                listUsers.add(user);
            }
            return listUsers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // изменить данные пользователя по id в бд
    public void updateUser(User user) {
        try {
            preparedStatement = connector.getConnection().prepareStatement(UPDATE_DATA_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.executeUpdate();
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // удалить пользователя по id в бд
    public void deleteUserById(int id) {
        try {
            preparedStatement = connector.getConnection().prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // удалить всех пользователей в бд
    public void deleteAllUsers() {
        try {
            preparedStatement = connector.getConnection().prepareStatement(DELETE_ALL_USERS);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
