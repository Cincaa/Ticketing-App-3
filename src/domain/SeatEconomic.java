package domain;

public class SeatEconomic extends Seat {
    private String specialEntrance;

    public SeatEconomic(Integer number, String category, String specialEntrance) {

        super(number, category);
        this.specialEntrance = specialEntrance;
    }

    public String getSpecialEntrance() {
        return specialEntrance;
    }

    public void setSpecialEntrance(String specialEntrance) {
        this.specialEntrance = specialEntrance;
    }

}
