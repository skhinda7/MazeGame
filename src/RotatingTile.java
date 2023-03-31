public class RotatingTile extends GenericTile {
    public void enterAction() {

    }

    public void exitAction() {
        boolean[] tempExits = new boolean[exits.length];
        for (int i = 0; i < exits.length - 1; i++) {
            tempExits[i + 1] = exits[i];
        }
        tempExits[0] = exits[exits.length - 1];
        exits = tempExits;
    }

    public void specialAction() {
        boolean[] tempExits = new boolean[exits.length];
        for (int i = 3; i > 0; i--) {
            tempExits[i - 1] = exits[i];
        }
        tempExits[exits.length - 1] = exits[0];
        exits = tempExits;
    }
}
