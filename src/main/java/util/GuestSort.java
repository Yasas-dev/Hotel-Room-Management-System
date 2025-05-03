package util;

import entity.Guest;
import java.util.List;
import java.time.LocalDate;

public class GuestSort {

    // Method to sort the guests list by check-in date
    public static void sortGuestsByCheckInDate(List<Guest> guests) {
        quickSort(guests, 0, guests.size() - 1);
    }

    private static void quickSort(List<Guest> guests, int low, int high) {
        if (low < high) {
            int pi = partition(guests, low, high);

            quickSort(guests, low, pi - 1);
            quickSort(guests, pi + 1, high);
        }
    }

    private static int partition(List<Guest> guests, int low, int high) {
        Guest pivot = guests.get(high);
        LocalDate pivotDate = pivot.getCheckIn();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (guests.get(j).getCheckIn().isBefore(pivotDate)) {
                i++;
                Guest temp = guests.get(i);
                guests.set(i, guests.get(j));
                guests.set(j, temp);
            }
        }

        Guest temp = guests.get(i + 1);
        guests.set(i + 1, guests.get(high));
        guests.set(high, temp);

        return i + 1;
    }
}
