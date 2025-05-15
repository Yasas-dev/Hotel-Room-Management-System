package entity;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class GuestServices {
    private static final String FILE_PATH = "C:\\Users\\USER\\Desktop\\final project\\HotelRoomManagementApp\\src\\main\\webapp\\Guests.txt";
    private static final RoomBST roomTree = new RoomBST();

    public static boolean isRoomAvailable(int roomNumber) {
        return roomTree.isAvailable(roomNumber);
    }

    public static boolean registerGuest(Guest guest) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(guest.getSummary());
                writer.newLine();
            }

            roomTree.bookRoom(guest.getRoomNumber());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateGuest(int roomId, String newName, String newPhone,
                                      LocalDate newCheckout, LocalDate oldCheckout) {
        List<String> updatedLines = new ArrayList<>();
        boolean guestFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentRoom = Integer.parseInt(parts[5].trim());

                if (currentRoom == roomId) {
                    String roomType = parts[4].trim();
                    LocalDate checkIn = LocalDate.parse(parts[2].trim());
                    double oldPrice = Double.parseDouble(parts[6].trim());

                    long extraDays = oldCheckout.until(newCheckout).getDays();
                    double newPrice = oldPrice + (extraDays > 0 ? extraDays * 1000 : 0);

                    String updatedLine = String.join(",",
                            newName, newPhone, checkIn.toString(), newCheckout.toString(),
                            roomType, String.valueOf(roomId), String.valueOf(newPrice));

                    updatedLines.add(updatedLine);
                    guestFound = true;
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return guestFound;
    }

    public static boolean deleteGuest(int roomNumberToDelete) {
        List<String> updatedGuests = new ArrayList<>();
        boolean guestDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int roomNumber = Integer.parseInt(parts[5].trim());
                    if (roomNumber != roomNumberToDelete) {
                        updatedGuests.add(line);
                    } else {
                        guestDeleted = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (String guestLine : updatedGuests) {
                writer.write(guestLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (guestDeleted) {
            roomTree.releaseRoom(roomNumberToDelete);
        }

        return guestDeleted;
    }
}