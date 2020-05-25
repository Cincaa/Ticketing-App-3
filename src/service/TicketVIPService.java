package service;


import domain.TicketVIP;

import java.io.*;

public class TicketVIPService {
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

}

