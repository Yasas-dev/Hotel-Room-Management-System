package entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Guest {
    protected String name;
    protected String phone;
    protected LocalDate checkIn;
    protected LocalDate checkOut;
    protected int roomNumber;

    public Guest(String name, String phone, LocalDate checkIn, LocalDate checkOut, int roomNumber) {
        this.name = name;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomNumber = roomNumber;
    }

    public long getStayDuration() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public abstract double calculatePrice();
    public abstract String getRoomType();

    // CSV summary
    public String getSummary() {
        return name + "," + phone + "," + checkIn + "," + checkOut + "," +
                getRoomType() + "," + roomNumber + "," + calculatePrice();
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
