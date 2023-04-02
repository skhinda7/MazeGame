//*********************************************************
// Class: RotatingTile
// Author: Simardeep Khinda
// Created: 03/29/2023
// Modified: 
//
// Purpose: This class represents an individual tile with an exit action of rotating doors clockwise. Special action rotates doors counter-clockwise.
//
// Attributes: 
//			-exits: boolean[]
//			-description: String
//		
// Methods: +enterAction(): void
//          +exitAction(): void
//          +specialAction(): void
//
//*********************************************************

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
