package domain;

public class TicketEconomic extends Ticket {
    public String promoCode;
    public Integer discount;

    public TicketEconomic(Integer ticketNo, String name, Integer discount, String promoCode) {
        super(ticketNo, name);
        this.promoCode = promoCode;
        this.discount = discount;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String toString() {
        return "E";
    }
}
