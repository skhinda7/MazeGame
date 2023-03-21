import java.util.Random;

public class GenericAgent {
    private GenericTile currentTile;
    GenericTile[][] neighborhood;
    Random random = new Random();

    public int move() {
        boolean foundDoor = false;
        int i = 0;

        while (foundDoor == false) {
            if (currentTile.getExits()[0]) {
                foundDoor = true;
            }
            i++;
            System.out.println("Door not found yet. Trying: " + i);
        }
        System.out.println("Door found. i = " + i);
        return i;
    }
}
