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
    private GenericAgent agent = new GenericAgent();
    private int[] agentLocation = new int[2];
    private int turnCounter;
    private int n = maze.length;

    // +createMaze(int, int): void
    public void createMaze(int a, int b) {

        maze = new GenericTile[a][b];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                maze[i][j] = new GenericTile();
                boolean[] doorStatus = new boolean[4];
                for (int k = 0; k < 4; k++) {
                    doorStatus[k] = random.nextBoolean();
                }
                maze[i][j].setExits(doorStatus);
            }
        }
        statusString = new String[a][b][4];

        GenericTile start = maze[0][(b - 1) / 2];
        GenericTile goal = maze[a - 1][(b - 1) / 2];

    }

    // +convertExitsToString(): void
    public void convertExitsToString() {
        String tempString;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                boolean[] stringBool = maze[i][j].getExits();
                for (int k = 0; k < 4; k++) {
                    if (stringBool[k]) {
                        tempString = "U";
                    } else {
                        tempString = "L";
                    }
                    statusString[i][j][k] = tempString;
                }
            }
        }
    }

    // +printMaze(): void
    public void printMaze() {
        convertExitsToString();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                System.out.println("Tile (" + i + "," + j + ") (N, S, E, W) status: (" + statusString[i][j][0] + ", "
                        + statusString[i][j][1] + ", " + statusString[i][j][2] + ", " + statusString[i][j][3] + ")");
            }
        }
    }

    // +printMazeSummary(): void
    public void printMazeSummary() {
        int x = 0;
        int y = (maze.length - 1) / 2;
        int p = (maze.length - 1);
        int q = (maze.length - 1) / 2;

        printMaze();

        System.out.println("There are " + maze.length + " rows and columns in the Maze." + " Start is (" + x + ", " + y
                + "), and the Goal is at (" + p + ", " + q + ").");
    }

    // +playGame(): void
    public void playGame() {
        if (hasAgentWon() == true) {
            System.out.println("Agent has won the game.");
        } else if (hasAgentLost() == true) {
            System.out.println("Agent has lost the game.");
        }
    }

    // +agentIsInGoal(): boolean
    public boolean agentIsInGoal() {
        boolean agentIsInGoal = false;
        int x = (maze.length - 1);
        int y = (maze.length - 1) / 2;

        if (agentLocation[0] == x && agentLocation[1] == y) {
            agentIsInGoal = true;
        }

        return agentIsInGoal;
    }

    // +hasAgentWon(): boolean
    public boolean hasAgentWon() {
        boolean hasAgentWon = false;

        if (agentIsInGoal() == true) {
            hasAgentWon = true;
            System.out.println("Agent has won the game.");
        }
        return hasAgentWon;
    }

    // +hasAgentLost(): boolean
    public boolean hasAgentLost() {
        boolean hasAgentLost = false;
        if (turnCounter == 50 && hasAgentWon() == false) {
            hasAgentLost = true;
        }
        return hasAgentLost;
    }

    // +moveAgent(): void
    public void moveAgent() {

        agent.move();
        printAgentLocation();
        turnCounter++;
    }

    // +isMoveLegal(): boolean
    public boolean isMoveLegal() {
        boolean isMoveLegal = false;
        if (agentLocation[0] >= 0 && agentLocation[0] <= (n - 1)) {
            if (agentLocation[1] >= 0 && agentLocation[1] <= (n - 1)) {
                isMoveLegal = true;
            }
        }
        return isMoveLegal;
    }

    // +isDoorLocked(): boolean
    public boolean isDoorLocked(GenericTile tile, int direction) {
        boolean isDoorLocked = false;

        if (tile.getExits()[direction] == true) {
            isDoorLocked = true;
        }

        return isDoorLocked;
    }

    // +isDoorUnlocked(): boolean
    public void printAgentLocation() {
        System.out.println("Agent is at (" + agentLocation[0] + ", " + agentLocation[1] + ").");
    }

    // +setAgent(GenericAgent): void
    public void setAgent(GenericAgent agent) {
        this.agent = agent;
    }

    // ************************************** Getters and Setters **************************************

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

    public Random getRandom() {
        return this.random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public GenericAgent getAgent() {
        return this.agent;
    }

    public int[] getAgentLocation() {
        return this.agentLocation;
    }

    public void setAgentLocation(int[] agentLocation) {
        this.agentLocation = agentLocation;
    }

    public int getTurnCounter() {
        return this.turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public int getN() {
        return this.n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
