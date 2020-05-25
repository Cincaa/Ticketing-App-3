package service;

import connection.DatabaseConnection;
import domain.Ticket;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TicketService {
    private static TicketService instance = null;

    private static final String INSERT_STATEMENT = "INSERT INTO tickets (ticketNo, name, discount, promoCode, extraFee) VALUES (?,?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM tickets WHERE ticketNo = ?";
    private static final String UPDATE_STATEMENT = "UPDATE tickets SET name = ? WHERE ticketNo = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM tickets WHERE ticketNo = ?";
    private static final String COUNT_STATEMENT =  "SELECT COUNT(*) FROM tickets";

    public TicketService getInstance() {
        if (instance == null)
            instance = new TicketService();
        return instance;
    }

    public void readTicketsFromFile() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/Data/RegularTickets.txt"))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFields = currentLine.split(",");
                Ticket ticket = new Ticket(Integer.parseInt(dataFields[0]), dataFields[1]);
                ServiceMain.getTicketList().add(ticket);

            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully read " + ServiceMain.getTicketList().size() + " tickets!");

    }

    public void writeTicketsToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/Data/RegularTickets.txt"))) {
            for (Ticket ticket : ServiceMain.getTicketList()) {
                bufferedWriter.write(ticket.getTicketNo().toString() + "," + ticket.getName());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
    }
    public Ticket saveTicket(Ticket ticket) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            statement.setInt(1, ticket.getTicketNo());
            statement.setString(2, ticket.getName());
            statement.setInt(3, 0);
            statement.setString(4, null);
            statement.setInt(5, 0);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new ticket was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new ticket: " + e.getMessage());
            return new Ticket(ticket.getTicketNo(), ticket.getName());
        }
        return ticket;
    }

    public Ticket findTicket (Integer ticketNo) {
        Ticket ticket = new Ticket(ticketNo, null);
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            statement.setInt(1, ticketNo);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    System.out.println("Something went wrong when trying to find a regular ticket: Ticket was not found!");
                    return ticket;
                }

                System.out.println("Ticket was found!");
                ticket.setTicketNo(result.getInt("no"));
                ticket.setName(result.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to find ticket: " + e.getMessage());
        }
        return ticket;
    }

    public Ticket updateTicket (Ticket ticket) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_STATEMENT)) {
            statement.setInt(1, ticket.getTicketNo());
            statement.setString(2, ticket.getName());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Ticket was updated successfully!");
                return ticket;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to update ticket: " + e.getMessage());
            return new Ticket(ticket.getTicketNo(), ticket.getName());
        }

        System.out.println("Something went wrong when trying to update ticket: Ticket was not found!");
        return new Ticket(ticket.getTicketNo(), ticket.getName());
    }

    public boolean deleteTicket(Integer ticketNo) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_STATEMENT)) {
            statement.setInt(1, ticketNo);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Ticket was deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to delete ticket: " + e.getMessage());
            return false;
        }

        System.out.println("Something went wrong when trying to delete ticket: Ticket was not found!");
        return false;
    }
    public Integer countTickets(){
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(COUNT_STATEMENT)) {
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    System.out.println("Something went wrong when trying to find a regular ticket: Ticket was not found!");
                    return -1;
                }
                System.out.println("Count executed");
               return result.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to find count from ticket: " + e.getMessage());
        }
        return -1;
        }
}

