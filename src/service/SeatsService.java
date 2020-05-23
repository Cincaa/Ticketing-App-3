package service;

import domain.Seat;
import domain.SeatEconomic;
import domain.SeatVIP;

import java.io.*;

public class SeatsService {
    private static SeatsService instance = null;

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
        //System.out.println("Successfully wrote " + ServiceClass.getTicketList().size() + " seats!");
    }

}

