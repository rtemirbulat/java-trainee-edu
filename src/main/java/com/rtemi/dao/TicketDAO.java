package com.rtemi.dao;

import com.rtemi.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private static final String URL = "jdbc:postgresql://:5432/my_ticket_service_db";
    private static final String USER = "admin";
    private static final String PASSWORD = "secret";

    public void saveTicket(int userId, String ticketType) {
        String query = "INSERT INTO public.Ticket (user_id, ticket_type) VALUES (?, ?::ticket_type)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, ticketType);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Ticket getTicketById(int id) {
        String query = "SELECT * FROM public.Ticket WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Ticket(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("ticket_type"),
                        resultSet.getTimestamp("creation_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        String query = "SELECT * FROM public.Ticket WHERE user_id = ?";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tickets.add(new Ticket(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("ticket_type"),
                        resultSet.getTimestamp("creation_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public void updateTicketType(int id, String ticketType) {
        String query = "UPDATE public.Ticket SET ticket_type = ?::ticket_type WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ticketType);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveAllTickets(){
        String query = "SELECT * FROM public.ticket";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("ID | User ID | Ticket Type | Creation Date");
            System.out.println("---|---------|-------------|--------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String ticketType = resultSet.getString("ticket_type");
                Timestamp creationDate = resultSet.getTimestamp("creation_date");

                System.out.printf("%-3d| %-7d | %-11s | %-25s%n", id, userId, ticketType, creationDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
