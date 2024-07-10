package com.rtemi.services;

import com.rtemi.dao.TicketDAO;
import com.rtemi.dao.UserDAO;
import com.rtemi.model.Ticket;
import com.rtemi.model.User;
import com.rtemi.model.enums.TicketType;

import java.util.List;
import java.util.Scanner;


public class DataService {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        TicketDAO ticketDAO = new TicketDAO();
        Scanner sc = new Scanner(System.in);

        int choice;
        System.out.println("Hello, I can perform 9 actions:");
        System.out.println("1. Save Tickets and Users");
        System.out.println("2. Fetch tickets by id and Users by id");
        System.out.println("3. Update ticket type");
        System.out.println("4. Delete Users by ID and their tickets if any exist");
        System.out.println("5. Find tickets by userid");
        System.out.println("6. Update both User and all his tickets in the same transaction:");
        System.out.println("7. Display all users");
        System.out.println("8. Display all tickets");
        System.out.println("9. Exit");
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
                    System.out.println("Userid:" + userId);
                    System.out.println("Select ticket type: DAY , WEEK , MONTH , YEAR : ");
                    TicketType ticketType = TicketType.valueOf(sc.nextLine());
                    System.out.println("Ticket_type : "+ ticketType);
                    ticketDAO.saveTicket(new Ticket(userId,ticketType));
                }
                else if(newChoice==2){
                    System.out.println("Enter user name: ");
                    String name = sc.nextLine().trim();
                    System.out.println("Name entered: " + name);
                    User user = new User();
                    user.setName(name);
                    userDAO.saveUser(user);
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
                System.out.println("Select new ticket type: DAY , WEEK , MONTH , YEAR : ");
                TicketType ticketType = TicketType.valueOf(sc.nextLine());
                System.out.println("Ticket_type : "+ ticketType);
                ticketDAO.updateTicketType(ticketId,ticketType);
                break;
            case 4:
                System.out.println("Enter id for deletion: ");
                int userid =  Integer.parseInt(sc.nextLine().trim());
                System.out.println("entered "+ userid);
                ticketDAO.deleteTicketsByUserId(userid);
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
                System.out.println("Update both User and all tickets related to user: ");
                System.out.println("Enter user id: ");
                int inputId = Integer.parseInt(sc.nextLine().trim());
                User user = userDAO.getUserById(inputId);
                System.out.println("Lets set new params, first, select new name: ");
                String name = sc.nextLine();
                user.setName(name);
                System.out.println("Now lets update tickets by selecting new Ticket Type: DAY, WEEK, YEAR, MONTH");
                ticketType = TicketType.valueOf(sc.nextLine());
                System.out.println("Ticket_type : "+ ticketType);
                userDAO.updateUserAndTickets(user,ticketType);
            case 7:
                System.out.println("Retrieving all users");
                userDAO.retrieveAllUsers();
                break;
            case 8:
                System.out.println("Retrieving all tickets");
                ticketDAO.retrieveAllTickets();
                break;
            case 9:
                System.out.println("Exiting Program...");
                System.exit(0);
                break;
            default:
                System.out.println("Not a menu option, try again: ");
                break;
        }
    }
}
