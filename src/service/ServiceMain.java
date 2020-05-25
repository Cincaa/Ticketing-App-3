package service;

import domain.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.*;



public class ServiceMain {

    private static Set<Seat> seatSet = new HashSet<>();
    private static List<Ticket> ticketList = new ArrayList<>();
    private static List<TicketEconomic> ticketEconomicList = new ArrayList<>();
    private static List<TicketVIP> ticketVIPList = new ArrayList<>();

    public ServiceMain() {
        ticketList = new ArrayList<Ticket>();
        seatSet = new HashSet<Seat>();
    }

    public static void logs(String option) {
        Date date = new Date();
        Instant instant = date.toInstant();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/Data/Logs.txt", true))) {
            bufferedWriter.write(option + "," + instant);
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
    }

    public static List<Ticket> getTicketList() {
        return ticketList;
    }

    public static List<TicketEconomic> getTicketEconomicList() {
        return ticketEconomicList;
    }

    public static List<TicketVIP> getTicketVIPList() {
        return ticketVIPList;
    }

    public static Set<Seat> getSeatSet() {
        return seatSet;
    }

    public void addTicketR() {
        //Read input from user
        System.out.println("Name: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();

        TicketService ticketService = new TicketService();
        SeatsService seatsService = new SeatsService();

        //Allocate a ticket number
        Integer no = ticketService.countTickets();
        Ticket t = new Ticket(no, name);
        //ticketList.add(t);
        System.out.println("Your ticket number is: " + no);



        Integer ok = 1;
        Integer seatNo = -1;
        while (ok == 1) {
            ok = 0;
            Random rand = new Random();
            seatNo = rand.nextInt(899) + 100;

            if (seatsService.findSeat(seatNo) == false)
                ok = 1;
        }
        Seat s = new Seat(seatNo, t.toString());
        System.out.println("Seat: " + seatNo);

        seatsService.saveSeat(s);


        //ticketService.writeTicketsToFile();
        ticketService.saveTicket(t);
    }

    public void addTicketVIP() {
        System.out.println("Name: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();

        Integer no = ticketVIPList.size();
        TicketVIP t = new TicketVIP(no, name, 50);
        System.out.println("Your ticket number is: " + no);
        System.out.println("VIP fee: " + 50);
        ticketVIPList.add(t);

        Integer ok = 1;
        Integer seatNo = -1;
        System.out.println("Seat number? (interval range: 100-999) ");

        while (ok == 1) {
            Collections.sort(ticketVIPList, new Comparator<Ticket>() {

                public int compare(Ticket o1, Ticket o2) {
                    return o1.getTicketNo().compareTo(o2.getTicketNo());
                }
            });
            seatNo = in.nextInt();
            ok = 0;
            if (seatNo < 100 || seatNo > 999) {
                ok = 1;
                System.out.println("Seat doesn't exist. Try another number.");
            }

            for (Seat seat : seatSet)
                if (seat.getNumber().equals(seatNo)) {
                    ok = 1;
                    System.out.println("Seat already taken. Try another number.");
                }
        }

        System.out.println("What drink would you like?");
        String drink = in.next();

        Seat s = new SeatVIP(seatNo, t.toString(), drink);
        seatSet.add(s);
        System.out.println("Seat: " + seatNo);
        System.out.println("Drink: " + drink);

        TicketVIPService ticketVIPService = new TicketVIPService();
        ticketVIPService.writeTicketsToFile();
        SeatsService seatsService = new SeatsService();
        seatsService.saveSeat(s);
    }

    public void addTicketE() {
        System.out.println("Name: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();

        System.out.println("Enter promo code: ");
        String promoCode = in.nextLine();

        SeatsService seatsService = new SeatsService();
        TicketEconomicService ticketEconomicService = new TicketEconomicService();

        TicketEconomic t;
        Integer ok = 1;
        Integer seatNo = -1;
        while (ok == 1) {
            ok = 0;
            Random rand = new Random();
            seatNo = rand.nextInt(899) + 100;
            if (seatsService.findSeat(seatNo) == false)
                ok = 1;
        }

        if (promoCode.equals("PROMO")) {
            System.out.println("OK");
            Integer no = ticketEconomicService.countTickets();
            t = new TicketEconomic(no, name, 10, promoCode);
            //ticketEconomicList.add(t);
            System.out.println("Your ticket number is: " + no);
            System.out.println("Discount: " + 10 + "%");

        } else {
            System.out.println("Invalid code.TRY AGAIN");
            return;
        }


        SeatEconomic s = new SeatEconomic(seatNo, t.toString(), "B2");
        //seatSet.add(s);
        System.out.println("Seat: " + seatNo);
        System.out.println("Entrance: " + "B2");


        //ticketEconomicService.writeTicketsToFile();
        ticketEconomicService.saveTicket(t);

        seatsService.saveSeat(s);
    }

    public void availablity() {
        System.out.println("Seat Availability(type seat no): ");
        Scanner in = new Scanner(System.in);
        Integer seatNo = in.nextInt();
        SeatsService seatsService = new SeatsService();
        seatsService.findSeat(seatNo);
    }

    public void refund() {
        System.out.println("Enter ticket number ");
        Scanner in = new Scanner(System.in);
        Integer no = in.nextInt();

        System.out.println("Type category(R - regular; V - VIP;E - Economic): ");
        String category = in.next();
        //Check regular tickets
        if (category.equals("R"))
            for (Ticket t : ticketList)
                if (t.getTicketNo().equals(no)) {
                    ticketList.remove(t);
                    System.out.println("Ticket number " + no + " deleted");
                } else
                    System.out.println("Ticket number not found");
        //Check VIP tickets
        if (category.equals("V"))
            for (TicketVIP t : ticketVIPList)
                if (t.getTicketNo().equals(no)) {
                    ticketList.remove(t);
                    System.out.println("Ticket number " + no + " deleted");
                } else
                    System.out.println("Ticket number not found");
        //Check economic tickets
        if (category.equals("E"))
            for (TicketEconomic t : ticketEconomicList)
                if (t.getTicketNo().equals(no)) {
                    ticketList.remove(t);
                    System.out.println("Ticket number " + no + " deleted");
                } else
                    System.out.println("Ticket number not found");
    }

    public void eInfo() {
        Eveniment e = new Eveniment("Concert", 20, 300);
        System.out.println("Name: " + e.getName());
        System.out.println("Capacity: " + e.getTickets());
        System.out.println("Price: " + e.getPrice());
    }

    public void location() {
        Location l = new Location("Club X", "Batistei 6", 50);
        System.out.println("Name: " + l.getName());
        System.out.println("Club capacity: " + l.getCapacity());
        System.out.println("Address: " + l.getAddress());
    }

    public void vip() {
        System.out.println("One drink included");
        System.out.println("You can choose your seat");
    }

    public void verify() {
        System.out.println("Enter ticket number");
        Scanner in = new Scanner(System.in);
        Integer no = in.nextInt();
        Boolean ok = false;

        System.out.println("Type category(R - regular; V - VIP;E - Economic): ");
        String category = in.next();
        if (category.equals("R"))
            for (Ticket t : ticketList)
                if (t.getTicketNo().equals(no)) {
                    ok = true;
                    break;
                }
        if (category.equals("V"))
            for (TicketVIP t : ticketVIPList)
                if (t.getTicketNo().equals(no)) {
                    ok = true;
                    break;
                }
        if (category.equals("E"))
            for (TicketEconomic t : ticketEconomicList)
                if (t.getTicketNo() == no) {
                    ok = true;
                    break;
                }
        if (ok)
            System.out.println("Ticket number " + no + " is genuine");
        else
            System.out.println("Ticket number doesn't exist");

    }

    public void show() {
        System.out.println();
        System.out.println("----------------MENU----------------");
        System.out.println("0.Exit.");
        System.out.println("1.Buy a Regular Ticket.");
        System.out.println("2.Buy a VIP Ticket.");
        System.out.println("3.Buy an Economic Ticket.");
        System.out.println("4.Show available seats.");
        System.out.println("5.Refund.");
        System.out.println("6.Event info.");
        System.out.println("7.Event location.");
        System.out.println("8.VIP advantages.");
        System.out.println("9.Verify my ticket (ticket no. & category requiered).");
    }
}
