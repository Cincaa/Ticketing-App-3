package domain;

public class TicketVIP extends Ticket {
    private Integer extraFee;

    public TicketVIP(Integer ticketNo, String name, Integer extraFee) {
        super(ticketNo, name);
        this.extraFee = extraFee;
    }

    public Integer getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(Integer extraFee) {
        this.extraFee = extraFee;
    }

    public String toString() {
        return "V";
    }
}
