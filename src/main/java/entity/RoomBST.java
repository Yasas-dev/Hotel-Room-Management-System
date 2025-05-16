package entity;

public class RoomBST {
    private static RoomNode root;
    private static final Object lock = new Object();

    private static class RoomNode {
        int roomNumber;
        boolean isAvailable;
        RoomNode left, right;

        RoomNode(int roomNumber) {
            this.roomNumber = roomNumber;
            this.isAvailable = true;
        }
    }

    static {
        // Initialize all rooms as available
        synchronized (lock) {
            for (int i = 1; i <= 20; i++) {
                insert(i);
            }
            System.out.println("[SYSTEM] Initialized room BST with 20 rooms");
        }
    }

    private static void insert(int roomNumber) {
        root = insertRec(root, roomNumber);
    }

    private static RoomNode insertRec(RoomNode node, int roomNumber) {
        if (node == null) return new RoomNode(roomNumber);
        if (roomNumber < node.roomNumber)
            node.left = insertRec(node.left, roomNumber);
        else if (roomNumber > node.roomNumber)
            node.right = insertRec(node.right, roomNumber);
        return node;
    }

    public static synchronized void bookRoom(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        if (node != null) {
            node.isAvailable = false;
            System.out.println("[SYSTEM] Booked room: " + roomNumber);
        }
    }

    public static synchronized void releaseRoom(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        if (node != null) {
            node.isAvailable = true;
            System.out.println("[SYSTEM] Released room: " + roomNumber);
        }
    }

    public static synchronized boolean isAvailable(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        boolean available = node != null && node.isAvailable;
        System.out.println("[SYSTEM] Checking room " + roomNumber + " - Available: " + available);
        return available;
    }

    private static RoomNode find(RoomNode node, int roomNumber) {
        if (node == null) return null;
        if (roomNumber == node.roomNumber) return node;
        return roomNumber < node.roomNumber
                ? find(node.left, roomNumber)
                : find(node.right, roomNumber);
    }
}