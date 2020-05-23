package domain;

public class Seat {
    private Integer number;
    private String category;

    //simulate default parameters
    public Seat(Integer number, String category) {
        this.number = number;
        this.category = category;
    }

    public Seat(Integer number) {
        this.number = number;
        this.category = null;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
