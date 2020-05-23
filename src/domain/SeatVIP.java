package domain;

public class SeatVIP extends Seat {
    private String drink;

    public SeatVIP(Integer number, String category, String drink) {
        super(number, category);
        this.drink = drink;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }
}
