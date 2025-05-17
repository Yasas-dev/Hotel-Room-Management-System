package entity;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class GuestServices {
    private static final String FILE_PATH = "C:\\Users\\USER\\Desktop\\final project\\HotelRoomManagementApp\\src\\main\\webapp\\Guests.txt";

    static {
        // Load booked rooms from file at startup
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int roomNumber = Integer.parseInt(parts[5].trim());
                    RoomBST.bookRoom(roomNumber);
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet - that's fine
            System.out.println("[SYSTEM] No existing guest data found - starting fresh");
        }
    }

    public static boolean isRoomAvailable(int roomNumber) {
        return RoomBST.roomExists(roomNumber) && RoomBST.isAvailable(roomNumber);
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
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[5].trim()) == roomId) {
                    // Update the guest record
                    double oldPrice = Double.parseDouble(parts[6].trim());
                    long extraDays = oldCheckout.until(newCheckout).getDays();
                    double newPrice = oldPrice + (extraDays > 0 ? extraDays * 1000 : 0);

                    line = String.join(",",
                            newName, newPhone, parts[2].trim(), newCheckout.toString(),
                            parts[4].trim(), String.valueOf(roomId), String.valueOf(newPrice));
                    found = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteGuest(int roomNumber) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Integer.parseInt(line.split(",")[5].trim()) != roomNumber) {
                    lines.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                RoomBST.releaseRoom(roomNumber);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}