package domain;

public class Eveniment {
    private String name;
    private Integer tickets, price;

    public Eveniment(String name, Integer tickets, Integer price) {
        this.name = name;
        this.tickets = tickets;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
