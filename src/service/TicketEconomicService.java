package service;


import domain.TicketEconomic;

import java.io.*;

public class TicketEconomicService {
    private static TicketEconomicService instance = null;

    public TicketEconomicService getInstance() {
        if (instance == null)
            instance = new TicketEconomicService();
        return instance;
    }

    public void readTicketsFromFile() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/Data/EconomicTickets.txt"))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFields = currentLine.split(",");
                TicketEconomic ticket = new TicketEconomic(Integer.parseInt(dataFields[0]), dataFields[1],
                        dataFields[2], Integer.parseInt(dataFields[3]));
                ServiceClass.getTicketEconomicList().add(ticket);

            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully read " + ServiceClass.getTicketEconomicList().size() + " tickets!");

    }

    public void writeTicketsToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/Data/EconomicTickets.txt"))) {
            for (TicketEconomic ticket : ServiceClass.getTicketEconomicList()) {
                bufferedWriter.write(ticket.getTicketNo().toString() + "," + ticket.getName() + "," +
                        ticket.getPromoCode() + "," + ticket.getDiscount().toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
    }

}

