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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
