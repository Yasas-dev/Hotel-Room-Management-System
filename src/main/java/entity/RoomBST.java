package entity;

public class RoomBST {
    private RoomNode root;

    // RoomNode definition
    private static class RoomNode {
        int roomNumber;
        boolean isAvailable;
        RoomNode left, right;

        RoomNode(int roomNumber) {
            this.roomNumber = roomNumber;
            this.isAvailable = true; // all rooms start as available
        }
    }

    public RoomBST() {
        // Build BST for room numbers 1 to 20
        for (int i = 1; i <= 20; i++) {
            insert(i);
        }
    }

    private void insert(int roomNumber) {
        root = insertRec(root, roomNumber);
    }

    private RoomNode insertRec(RoomNode node, int roomNumber) {
        if (node == null) return new RoomNode(roomNumber);
        if (roomNumber < node.roomNumber)
            node.left = insertRec(node.left, roomNumber);
        else if (roomNumber > node.roomNumber)
            node.right = insertRec(node.right, roomNumber);
        return node;
    }

    // Mark room as unavailable (false)
    public void bookRoom(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        if (node != null) {
            node.isAvailable = false;
        }
    }

    // Mark room as available (true)
    public void releaseRoom(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        if (node != null) {
            node.isAvailable = true;
        }
    }

    // Check if room is available
    public boolean isAvailable(int roomNumber) {
        RoomNode node = find(root, roomNumber);
        return node != null && node.isAvailable;
    }

    private RoomNode find(RoomNode node, int roomNumber) {
        if (node == null) return null;
        if (roomNumber == node.roomNumber) return node;
        return roomNumber < node.roomNumber
                ? find(node.left, roomNumber)
                : find(node.right, roomNumber);
    }
}
