package util;

import entity.Guest;
import java.time.LocalDate;

public class GuestSort {

    // Method to sort the guests array by check-in date
    public static void sortGuestsByCheckInDate(Guest[] guests) {
        if (guests == null || guests.length == 0) return;
        quickSort(guests, 0, guests.length - 1);
    }

    private static void quickSort(Guest[] guests, int low, int high) {
        if (low < high) {
            int pi = partition(guests, low, high);
            quickSort(guests, low, pi - 1);
            quickSort(guests, pi + 1, high);
        }
    }

    private static int partition(Guest[] guests, int low, int high) {
        Guest pivot = guests[high];
        LocalDate pivotDate = pivot.getCheckIn();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (guests[j].getCheckIn().isBefore(pivotDate)) {
                i++;
                Guest temp = guests[i];
                guests[i] = guests[j];
                guests[j] = temp;
            }
        }

        Guest temp = guests[i + 1];
        guests[i + 1] = guests[high];
        guests[high] = temp;

        return i + 1;
    }
}