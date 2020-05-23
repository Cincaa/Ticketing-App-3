package domain;


public class Ticket {
    private Integer ticketNo;
    private String name;

    public Ticket(Integer ticketNo, String name) {
        this.ticketNo = ticketNo;
        this.name = name;
    }

    public Integer getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(Integer ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "R";
    }
}
