package domain;

import service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiceClass service = new ServiceClass();

        TicketService ticketService = new TicketService();
        ticketService.getInstance();
        TicketEconomicService ticketEconomicService = new TicketEconomicService();
        ticketEconomicService.getInstance();
        TicketVIPService ticketVIPService = new TicketVIPService();
        ticketVIPService.getInstance();
        SeatsService seatsService = new SeatsService();
        seatsService.getInstance();

        ticketService.readTicketsFromFile();

        ticketVIPService.readTicketsFromFile();
        ticketEconomicService.readTicketsFromFile();
        seatsService.readSeatsFromFile();

        Integer option = -1;
        while (option != 0) {
            service.show();
            Scanner in = new Scanner(System.in);
            option = in.nextInt();
            switch (option) {
                case 0:
                    option = 0;
                    ServiceClass.logs("Exit");
                    break;
                case 1:
                    service.addTicketR();
                    ServiceClass.logs("Buy a Regular Ticket");
                    break;
                case 2:
                    service.addTicketVIP();
                    ServiceClass.logs("Buy a VIP Ticket");
                    break;
                case 3:
                    service.addTicketE();
                    ServiceClass.logs("Buy an Economic Ticket");
                    break;
                case 4:
                    service.availablity();
                    ServiceClass.logs("Show available seats");
                    break;
                case 5:
                    service.refund();
                    ServiceClass.logs("Refund");
                    break;
                case 6:
                    service.eInfo();
                    ServiceClass.logs("Event info");
                    break;
                case 7:
                    service.location();
                    ServiceClass.logs("Event location");
                    break;
                case 8:
                    service.vip();
                    ServiceClass.logs("VIP advantages");
                    break;
                case 9:
                    service.verify();
                    ServiceClass.logs("Verify my ticket");
                    break;
                default:
                    System.out.println("Invalid!");
                    ServiceClass.logs("Invalid command");
            }

        }

    }

}