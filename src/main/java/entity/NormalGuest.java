package entity;

import java.time.LocalDate;

public class NormalGuest extends Guest {
    private final double rate = 1000;

    public NormalGuest(String name, String phone, LocalDate checkIn, LocalDate checkOut, int roomNumber) {
        super(name, phone, checkIn, checkOut, roomNumber);
    }

    @Override
    public double calculatePrice() {
        return rate * getStayDuration();
    }

    @Override
    public String getRoomType() {
        return "normal";
    }
}
