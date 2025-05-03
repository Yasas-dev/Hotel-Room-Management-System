package entity;

import java.time.LocalDate;

public class VipGuest extends Guest {
    private final double rate = 2000;

    public VipGuest(String name, String phone, LocalDate checkIn, LocalDate checkOut, int roomNumber) {
        super(name, phone, checkIn, checkOut, roomNumber);
    }

    @Override
    public double calculatePrice() {
        return rate * getStayDuration();
    }

    @Override
    public String getRoomType() {
        return "vip";
    }
}
