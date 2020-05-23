package service;

import domain.Ticket;

import java.io.*;


public class TicketService {
    private static TicketService instance = null;

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
                ServiceClass.getTicketList().add(ticket);

            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully read " + ServiceClass.getTicketList().size() + " tickets!");

    }

    public void writeTicketsToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/Data/RegularTickets.txt"))) {
            for (Ticket ticket : ServiceClass.getTicketList()) {
                bufferedWriter.write(ticket.getTicketNo().toString() + "," + ticket.getName());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
    }

}
