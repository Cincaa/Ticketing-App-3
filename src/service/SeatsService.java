package service;


import connection.DatabaseConnection;
import domain.Seat;
import domain.SeatEconomic;
import domain.SeatVIP;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SeatsService {
    private static SeatsService instance = null;

    private static final String INSERT_STATEMENT = "INSERT INTO seats (no, category) VALUES (?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM persons WHERE no = ?";
    private static final String UPDATE_STATEMENT = "UPDATE seats SET category = ? WHERE no = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM seats WHERE no = ?";

    public SeatsService getInstance() {
        if (instance == null)
            instance = new SeatsService();
        return instance;
    }

    public void readSeatsFromFile() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/Data/Seats.txt"))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFields = currentLine.split(",");
                if (dataFields[1].equals("R")) {
                    Seat seat = new Seat(Integer.parseInt(dataFields[0]), dataFields[1]);
                    ServiceClass.getSeatSet().add(seat);
                }
                if (dataFields[1].equals("V")) {
                    SeatVIP seat = new SeatVIP(Integer.parseInt(dataFields[0]), dataFields[1], dataFields[2]);
                    ServiceClass.getSeatSet().add(seat);
                }
                if (dataFields[1].equals("E")) {
                    SeatEconomic seat = new SeatEconomic(Integer.parseInt(dataFields[0]), dataFields[1], dataFields[2]);
                    ServiceClass.getSeatSet().add(seat);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully read " + ServiceClass.getTicketEconomicList().size() + " seats!");

    }

    public void writeSeatsToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/Data/Seats.txt"))) {
            for (Seat seat : ServiceClass.getSeatSet()) {
                if (seat instanceof SeatEconomic) {
                    bufferedWriter.write(seat.getNumber().toString() + "," + "E" + "," + ((SeatEconomic) seat).getSpecialEntrance());
                    bufferedWriter.newLine();
                } else if (seat instanceof SeatVIP) {
                    bufferedWriter.write(seat.getNumber().toString() + "," + "V" + "," + ((SeatVIP) seat).getDrink());
                    bufferedWriter.newLine();
                } else if (seat instanceof Seat) {
                    bufferedWriter.write(seat.getNumber().toString() + "," + "R");
                    bufferedWriter.newLine();

                }

            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
    }
    public Seat saveSeat(Seat seat) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            statement.setInt(1, seat.getNumber());
            statement.setString(2, seat.getCategory());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new user: " + e.getMessage());
            return new Seat(seat.getNumber()+1);
        }
        return seat;
    }

    public Seat findSeat(Integer no) {
        Seat seat = new Seat(no);
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            statement.setInt(1, no);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    System.out.println("Something went wrong when trying to find user: User was not found!");
                    return seat;
                }

                System.out.println("User was found!");
                seat.setNumber(result.getInt("no"));
                seat.setCategory(result.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to find user: " + e.getMessage());
        }
        return seat;
    }

    public Seat updateSeat(Seat seat) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_STATEMENT)) {
            statement.setInt(1, seat.getNumber());
            statement.setString(2, seat.getCategory());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User was updated successfully!");
                return seat;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to update user: " + e.getMessage());
            return new Seat(seat.getNumber());
        }

        System.out.println("Something went wrong when trying to update user: User was not found!");
        return new Seat(seat.getNumber());
    }

    public boolean deleteSeat(Integer no) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_STATEMENT)) {
            statement.setInt(1, no);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User was deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to delete user: " + e.getMessage());
            return false;
        }

        System.out.println("Something went wrong when trying to delete user: User was not found!");
        return false;
    }
}

