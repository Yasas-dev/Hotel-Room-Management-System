package entity;

import java.io.*;
import java.time.LocalDate;

public class GuestServices {
    private static final String FILE_PATH = "C:\\Users\\USER\\Desktop\\final project\\HotelRoomManagementApp\\src\\main\\webapp\\Guests.txt";
    private static final int MAX_GUESTS = 20; // Reasonable upper limit

    static {
        loadBookedRooms();
    }

    private static void loadBookedRooms() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    RoomBST.bookRoom(Integer.parseInt(parts[5].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("[SYSTEM] Starting with fresh guest data");
        }
    }

    public static boolean isRoomAvailable(int roomNumber) {
        return RoomBST.isAvailable(roomNumber);
    }

    public static boolean registerGuest(Guest guest) {
        if (!isRoomAvailable(guest.getRoomNumber())) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(guest.getSummary());
            writer.newLine();
            RoomBST.bookRoom(guest.getRoomNumber());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateGuest(int roomId, String newName, String newPhone,
                                      LocalDate newCheckout, LocalDate oldCheckout) {
        try {
            // Read all guests into an array
            String[] allGuests = readAllGuests();
            boolean found = false;

            // Process each guest
            for (int i = 0; i < allGuests.length; i++) {
                if (allGuests[i] == null) continue;

                String[] parts = allGuests[i].split(",");
                if (Integer.parseInt(parts[5].trim()) == roomId) {
                    double oldPrice = Double.parseDouble(parts[6].trim());
                    long extraDays = oldCheckout.until(newCheckout).getDays();
                    double newPrice = oldPrice + (extraDays > 0 ? extraDays * 1000 : 0);

                    allGuests[i] = String.join(",",
                            newName, newPhone, parts[2].trim(), newCheckout.toString(),
                            parts[4].trim(), String.valueOf(roomId), String.valueOf(newPrice));
                    found = true;
                    break;
                }
            }

            if (found) {
                writeAllGuests(allGuests);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteGuest(int roomNumber) {
        try {
            String[] allGuests = readAllGuests();
            boolean found = false;
            int count = 0;

            // First pass - count valid guests (excluding our target)
            for (String guest : allGuests) {
                if (guest != null && Integer.parseInt(guest.split(",")[5].trim()) != roomNumber) {
                    count++;
                } else if (guest != null) {
                    found = true;
                }
            }

            if (!found) return false;

            // Create new array without the deleted guest
            String[] updatedGuests = new String[count];
            int index = 0;
            for (String guest : allGuests) {
                if (guest != null && Integer.parseInt(guest.split(",")[5].trim()) != roomNumber) {
                    updatedGuests[index++] = guest;
                }
            }

            writeAllGuests(updatedGuests);
            RoomBST.releaseRoom(roomNumber);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper methods for file operations
    private static String[] readAllGuests() throws IOException {
        String[] guests = new String[MAX_GUESTS];
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null && i < MAX_GUESTS) {
                guests[i++] = line;
            }
        }
        return guests;
    }

    private static void writeAllGuests(String[] guests) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String guest : guests) {
                if (guest != null) {
                    writer.write(guest);
                    writer.newLine();
                }
            }
        }
    }
}