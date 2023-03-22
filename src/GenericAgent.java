//*********************************************************
// Class: GenericAgent
// Author: Simardeep Khinda
// Created: 03/20/2023
// Modified: 
//
// Purpose: This class creates a generic agent with no special actions. 
//
// Attributes: 
//			-currentTile: GenericTile
//	        -neighborhood: GenericTile[][]
//		
// Methods: +move(): int
//*********************************************************

import java.util.Random;

public class GenericAgent {
    private GenericTile currentTile;
    GenericTile[][] neighborhood;
    Random random = new Random();

    public int move() {
        return random.nextInt(4);
    }
}
