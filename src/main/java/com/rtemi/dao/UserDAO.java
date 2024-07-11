package com.rtemi.dao;

import com.rtemi.model.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDAO {
    private final DataSource dataSource;

    public UserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveUser(String name) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String query = "INSERT INTO public.User (name) VALUES (?)";
        try (Connection connection = dataSource.getConnection()){
            if(connection !=null){
                System.out.println("connected");
            }
            else{
                System.out.println("Failed to make connection");
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM public.User WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("creation_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUserById(int id) {
        String query = "DELETE FROM public.User WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("deleted user by id " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void retrieveAllUsers(){
        String query = "SELECT * FROM public.User";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("ID | Name       | Creation Date");
            System.out.println("---|------------|--------------------------");
           while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Timestamp creationDate = resultSet.getTimestamp("creation_date");

                System.out.printf("%-3d| %-10s | %-25s%n", id, name, creationDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
