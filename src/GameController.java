//*********************************************************
// Class: GameController
// Author: Simardeep Khinda
// Created: 03/03/2023
// Modified: 
//
// Purpose: GameController initializes and creates an array of GenericTiles. It creates random locked and unlocked doors for each tile. The methods are called in MGMain.
//
// Attributes: 
//			-maze: GenericTile[][]
//          -statusString: String[][][]
//
// Methods: +createMaze(int, int): void
//          +convertExitsToString(): void
//          +printMaze(): void
//          +printMazeSummary(): void
//
//*********************************************************

import java.util.Random;

public class GameController {
    private GenericTile[][] maze;
    private String[][][] statusString;
    Random random = new Random();

    //TODO: -agent: GenericAgent 

    //TODO: -agentLocation: int[2]
    
    //TODO: -turnCounter: int


    public void createMaze(int a, int b) {

        maze = new GenericTile[a][b];

            for(int i = 0; i < maze.length; i++) {
                for(int j = 0; j < maze.length; j++) {
                    maze[i][j] = new GenericTile();
                    boolean[] doorStatus = new boolean[4];
                    for(int k = 0; k < 4; k++) {
                        doorStatus[k] = random.nextBoolean();
                    }
                    maze[i][j].setExits(doorStatus);
                }
            }
            statusString = new String[a][b][4];

            GenericTile start = maze[0][(b-1)/2];
            GenericTile goal = maze[a-1][(b-1)/2];

        }        

    public void convertExitsToString() {
        String tempString;

        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze.length; j++) {
                boolean[] stringBool = maze[i][j].getExits();
                for(int k = 0; k < 4; k++) {
                    if( stringBool[k]) {
                        tempString = "U";
                    }
                    else {
                        tempString = "L";
                    }
                    statusString[i][j][k] = tempString;
                }
            }
        }
    }

    public void printMaze() {
        convertExitsToString();
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze.length; j++) {
                System.out.println("Tile (" + i + "," + j + ") (N, S, E, W) status: (" + statusString[i][j][0] + ", " + statusString[i][j][1] + ", " + statusString[i][j][2] + ", " + statusString[i][j][3] + ")");
            }
        } 
    }

    public void printMazeSummary() {
        int x = 0;
        int y = (maze.length - 1) / 2;
        int p = (maze.length - 1);
        int q = (maze.length - 1) / 2;

        printMaze();

        System.out.println("There are " + maze.length + " rows and columns in the Maze." + " Start is (" + x + ", " + y + "), and the Goal is at (" + p + ", " + q + ").");
    }

    public GenericTile[][] getMaze() {
        return this.maze;
    }

    public void setMaze(GenericTile[][] maze) {
        this.maze = maze;
    }

    public String[][][] getStatusString() {
        return this.statusString;
    }

    public void setStatusString(String[][][] statusString) {
        this.statusString = statusString;
    }

    //TODO: +playGame(): void
    
    //TODO: +agentIsInGoal(): boolean
    
    //TODO: +hasAgentWon(): boolean
    
    //TODO: +hasAgentLost(): boolean
    
    //TODO: +moveAgent(): void
    
    //TODO: +isMoveLegal(): boolean
    
    //TODO: +isDoorLocked(GenericTile, int): boolean
    
    //TODO: +printAgentLocation(): void
    
    //TODO: +setAgent(Agent): void
}


