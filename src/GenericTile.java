//*********************************************************
// Class: GenericTile
// Author: Simardeep Khinda
// Created: 03/03/2023
// Modified: 
//
// Purpose: This class represents an individual tile with no special attributes yet. The tile comes with 4 locked/unlocked doors facing each direction.
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

public class GenericTile {
    protected boolean exits[] = new boolean[4];
    protected String description = new String();

    public void enterAction() {
        System.out.println("Entering action");
    }

    public void exitAction() {

    }

    public void specialAction() {

    }

    public boolean[] getExits() {
        return exits;
    }

    public void setExits(boolean[] exits) {
        this.exits = exits;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}