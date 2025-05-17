package entity;

public class RoomBST {
    private static RoomNode root;
    private static final int TOTAL_ROOMS = 20;

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
        // Initialize all rooms (1-20) as available
        for (int i = 1; i <= TOTAL_ROOMS; i++) {
            insert(i);
        }
        System.out.println("[SYSTEM] Initialized room BST with " + TOTAL_ROOMS + " rooms");
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

    public static void bookRoom(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        if (node != null) {
            node.isAvailable = false;
        }
    }

    public static void releaseRoom(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        if (node != null) {
            node.isAvailable = true;
        }
    }

    public static boolean isAvailable(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        return node != null && node.isAvailable;
    }

    private static RoomNode find(RoomNode node, int roomNumber) {
        if (node == null) return null;
        if (roomNumber == node.roomNumber) return node;
        return roomNumber < node.roomNumber
                ? find(node.left, roomNumber)
                : find(node.right, roomNumber);
    }

    // New method to check if a room exists (valid room number)
    public static boolean roomExists(int roomNumber) {
        return roomNumber >= 1 && roomNumber <= TOTAL_ROOMS;
    }
}