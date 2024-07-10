package com.rtemi.services;

import com.rtemi.dao.TicketDAO;
import com.rtemi.dao.UserDAO;
import com.rtemi.model.Ticket;
import com.rtemi.model.User;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketServiceDB {
    public static void main(String[] args) throws ClassNotFoundException {
        UserDAO userDAO = new UserDAO();
        TicketDAO ticketDAO = new TicketDAO();

        AtomicInteger idCounter = new AtomicInteger();


        Scanner sc = new Scanner(System.in);

        int choice;
        System.out.println("Hello, I can perform 5 actions:");
        System.out.println("1. Save Tickets and Users");
        System.out.println("2. Fetch tickets by id and Users by id");
        System.out.println("3. Update ticket type");
        System.out.println("4. Delete Users by ID and their tickets if any exist");
        System.out.println("5. Find tickets by userid");
        System.out.println("6. Display all users");
        System.out.println("7. Display all tickets");
        System.out.println("8. Exit");
        System.out.println("Choose action: ");
        choice = Integer.parseInt(sc.nextLine().trim());
        switch (choice){
            case 1:
                System.out.println("Do you want to enter ticket (press 1) or user (press 2)");
                int newChoice =  Integer.parseInt(sc.nextLine().trim());
                System.out.println("You chose "+ newChoice);
                System.out.println("Initializing : ");
                if(newChoice==1){
                    System.out.println("Enter ticket userid and ticket_type");
                    int userId =  Integer.parseInt(sc.nextLine().trim());
                    String ticketType = sc.nextLine().trim();
                    System.out.println("Userid:" + userId);
                    System.out.println("Ticket_type : "+ ticketType);
                    ticketDAO.saveTicket(userId,ticketType);
                }
                else if(newChoice==2){
                    System.out.println("Enter user name: ");
                    String name = sc.nextLine().trim();
                    System.out.println("Name entered: " + name);
                    userDAO.saveUser(name);
                }
                else{
                    System.out.println("Try again");
                    break;
                }
                break;
            case 2:
                System.out.println("You want to find ticket or user? (1,2)");
                newChoice =  Integer.parseInt(sc.nextLine().trim());
                System.out.println("You've chosen: "+ newChoice);
                if(newChoice == 1){
                    System.out.println("Please enter id of ticket: ");
                    int ticketId =  Integer.parseInt(sc.nextLine().trim());
                    System.out.println("Entered : "+ ticketId);
                    Ticket ticket = ticketDAO.getTicketById(ticketId);
                    System.out.println("id :" + ticket.getId() + "type: " + ticket.getTicketType() + "creation date: " + ticket.getCreationDate());
                } else if (newChoice == 2) {

                    System.out.println("Please enter id of user: ");
                    int userId =  Integer.parseInt(sc.nextLine().trim());
                    System.out.println("Entered : "+ userId);
                    User user = userDAO.getUserById(userId);
                    System.out.println(user.getName() + " " + user.getId() + user.getCreationDate());
                }
                else
                    break;
                break;
            case 3:
                System.out.println("Enter id of ticket: ");
                int ticketId =  Integer.parseInt(sc.nextLine().trim());
                System.out.println("Entered " + ticketId);
                System.out.println("Enter new ticket type of ticket: ");
                String ticketType = sc.nextLine().trim();
                System.out.println("Entered " + ticketType);
                ticketDAO.updateTicketType(ticketId,ticketType);
                break;
            case 4:
                System.out.println("Enter id for deletion: ");
                int userid =  Integer.parseInt(sc.nextLine().trim());
                System.out.println("entered "+ userid);
                userDAO.deleteUserById(userid);
                break;
            case 5:
                System.out.println("Fetching tickets by userid");
                System.out.println("Enter userid: ");
                userid =  Integer.parseInt(sc.nextLine().trim());
                System.out.println("entered: " + userid);
                List<Ticket> tickets = ticketDAO.getTicketsByUserId(userid);
                System.out.println("Displaying tickets: ");
                for (Ticket t : tickets) {
                    System.out.println("User Ticket: " + t.getTicketType());
                }
                break;
            case 6:
                System.out.println("Retrieving all users");
                userDAO.retrieveAllUsers();
                break;
            case 7:
                System.out.println("Retrieving all tickets");
                ticketDAO.retrieveAllTickets();
                break;
            case 8:
                System.out.println("Exiting Program...");
                System.exit(0);
                break;
            default:
                System.out.println("Not a menu option, try again: ");
                break;
        }

    }
}
