package service;


import connection.DatabaseConnection;
import domain.TicketVIP;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketVIPService {
    private static final String INSERT_STATEMENT = "INSERT INTO tickets (ticketNo, name, discount, promoCode, extraFee) VALUES (?,?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM tickets WHERE ticketNo = ?";
    private static final String UPDATE_STATEMENT = "UPDATE tickets SET name = ? WHERE ticketNo = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM tickets WHERE ticketNo = ?";
    private static final String COUNT_STATEMENT = "SELECT COUNT(*) FROM tickets";
    private static TicketVIPService instance = null;

    public TicketVIPService getInstance() {
        if (instance == null)
            instance = new TicketVIPService();
        return instance;
    }

    public void readTicketsFromFile() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/Data/VIPTickets.txt"))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFields = currentLine.split(",");
                TicketVIP ticket = new TicketVIP(Integer.parseInt(dataFields[0]), dataFields[1], Integer.parseInt(dataFields[2]));
                ServiceMain.getTicketVIPList().add(ticket);

            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully read " + ServiceMain.getTicketVIPList().size() + " tickets!");

    }

    public void writeTicketsToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/Data/VIPTickets.txt"))) {
            for (TicketVIP ticket : ServiceMain.getTicketVIPList()) {
                bufferedWriter.write(ticket.getTicketNo().toString() + "," + ticket.getName() + ","
                        + ticket.getExtraFee().toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote " + ServiceMain.getTicketVIPList().size() + " tickets!");
    }

    public TicketVIP saveTicket(TicketVIP ticket) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            statement.setInt(1, ticket.getTicketNo());
            statement.setString(2, ticket.getName());
            statement.setInt(3, 0);
            statement.setString(4, null);
            statement.setInt(5, ticket.getExtraFee());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new ticket was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new ticket: " + e.getMessage());
            return new TicketVIP(ticket.getTicketNo(), ticket.getName(), ticket.getExtraFee());
        }
        return ticket;
    }

    public Boolean findTicket(Integer ticketNo) {
        TicketVIP ticket = new TicketVIP(ticketNo, null, 10);
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            statement.setInt(1, ticketNo);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    System.out.println("Something went wrong when trying to find a regular ticket: TicketVIP was not found!");
                    return false;
                }

                System.out.println("TicketVIP was found!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to find ticket: " + e.getMessage());
        }
        return false;
    }

    public TicketVIP updateTicket(TicketVIP ticket) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_STATEMENT)) {
            statement.setString(1, ticket.getName());
            statement.setInt(2, ticket.getTicketNo());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("TicketVIP was updated successfully!");
                return ticket;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to update ticket: " + e.getMessage());
            return new TicketVIP(ticket.getTicketNo(), ticket.getName(), ticket.getExtraFee());
        }

        System.out.println("Something went wrong when trying to update ticket: TicketVIP was not found!");
        return new TicketVIP(ticket.getTicketNo(), ticket.getName(), ticket.getExtraFee());
    }

    public boolean deleteTicket(Integer ticketNo) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_STATEMENT)) {
            statement.setInt(1, ticketNo);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("TicketVIP was deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to delete VIP ticket: " + e.getMessage());
            return false;
        }

        System.out.println("Something went wrong when trying to delete ticket: TicketVIP was not found!");
        return false;
    }

    public Integer countTickets() {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(COUNT_STATEMENT)) {
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    System.out.println("Something went wrong when trying to find a VIP ticket: TicketVIP was not found!");
                    return -1;
                }
                System.out.println("Count executed");
                return result.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to find count from VIP ticket: " + e.getMessage());
        }
        return -1;
    }

}

