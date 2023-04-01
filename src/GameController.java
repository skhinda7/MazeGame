//*********************************************************
// Class: GameController
// Author: Simardeep Khinda
// Created: 03/03/2023
// Modified: 03/22/2023 - Added agent to move through the maze
//
// Purpose: GameController initializes and creates an array of GenericTiles. It creates random locked and unlocked doors for each tile. Furthermore, it runs an agent through the maze and checks if the agent made it to the goal.
//
// Attributes: 
//			-maze: GenericTile[][]
//          -statusString: String[][][]
//          -agent: GenericAgent 
//          -agentLocation: int[]
//          -turnCounter: int
//
// Methods: +createMaze(int, int): void
//          +convertExitsToString(): void
//          +printMaze(): void
//          +printMazeSummary(): void
//          +playGame(): void
//          +agentIsInGoal(): boolean
//          +hasAgentWon(): boolean
//          +hasAgentLost(): boolean
//          +moveAgent(): void
//          +isMoveLegal(): boolean
//          +isDoorLocked(): boolean
//          +printAgentLocation(): void
//          +setAgent(GenericAgent): void
//
//*********************************************************

import java.util.Random;

public class GameController {
    private GenericTile[][] maze;
    private String[][][] statusString;
    private GenericAgent agent = new GenericAgent();
    Random random = new Random();
    private int[] agentLocation = new int[2];
    private int turnCounter;

    // +createMaze(int, int): void
    public void createMaze(int a, int b) {
        maze = new GenericTile[a][b];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                maze[i][j] = pickTileType();
                setType(maze[i][j]);
                boolean[] doorStatus = new boolean[4];
                for (int k = 0; k < 4; k++) {
                    doorStatus[k] = random.nextBoolean();
                }
                maze[i][j].setExits(doorStatus);
            }
        }
        checkAtleastOne();
        statusString = new String[a][b][4];

        GenericTile start = maze[0][(b - 1) / 2];
        GenericTile goal = maze[a - 1][(b - 1) / 2];

        agentLocation[0] = (b - 1) / 2;
        agentLocation[1] = 0;

    }

    public void checkAtleastOne() {
        boolean foundRotating = false;
        boolean foundSolid = false;
        boolean foundStatic = false;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] instanceof RotatingTile) {
                    foundRotating = true;
                }
                if (maze[i][j] instanceof SolidTile) {
                    foundSolid = true;
                }
                if (maze[i][j] instanceof StaticTile) {
                    foundStatic = true;
                }
            }
        }
        if (foundRotating == false) {
            GenericTile rotateTile = maze[random.nextInt(maze.length)][random.nextInt(maze.length)];
            rotateTile = new RotatingTile();
            setType(rotateTile);
        }
        if (foundSolid == false) {
            maze[random.nextInt(maze.length)][random.nextInt(maze.length)] = new SolidTile();

        }
        if (foundStatic == false) {
            maze[random.nextInt(maze.length)][random.nextInt(maze.length)] = new StaticTile();

        }
    }

    public void setType(GenericTile tile) {
        if (tile instanceof StaticTile) {
            tile.setDescription("Static");
        } else if (tile instanceof RotatingTile) {
            tile.setDescription("Rotating");
        } else if (tile instanceof SolidTile) {
            tile.setDescription("Solid");
        }
    }

    public GenericTile pickTileType() {
        GenericTile pickedDoor = new GenericTile();
        int randNum = random.nextInt(3);
        switch (randNum) {
            case 0:
                pickedDoor = new RotatingTile();
                break;
            case 1:
                pickedDoor = new SolidTile();
                break;
            case 2:
                pickedDoor = new StaticTile();
                break;
        }
        return pickedDoor;
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
                System.out.println("Tile (" + i + "," + j + ") (N, E, S, W) status: (" + statusString[i][j][0] + ", "
                        + statusString[i][j][1] + ", " + statusString[i][j][2] + ", " + statusString[i][j][3]
                        + ") | Type: " + maze[i][j].getDescription());
            }
            System.out.println("\n");
        }
    }

    // +printMazeSummary(): void
    public void printMazeSummary() {
        int x = (maze.length - 1) / 2;
        int y = 0;
        int p = (maze.length - 1) / 2;
        int q = (maze.length - 1);

        printMaze();

        System.out.println("There are " + maze.length + " rows and columns in the Maze." + " Start is (" + x + ", " + y
                + "), and the Goal is at (" + p + ", " + q + ").");
        System.out.println("Agent's starting position: (" + agentLocation[0] + ", " + agentLocation[1] + ")\n");

    }

    // +playGame(): void
    public void playGame() {
        while (!hasAgentLost() && !hasAgentWon()) {
            moveAgent();
        }
        if (hasAgentWon()) {
            System.out.println("\nAgent has won the game!");
        } else if (hasAgentLost()) {
            System.out.println("\nAgent has lost the game.");
        }
    }

    // +agentIsInGoal(): boolean
    public boolean agentIsInGoal() {
        boolean agentIsInGoal = false;
        int x = (maze.length - 1) / 2;
        int y = (maze.length - 1);

        if (agentLocation[0] == x && agentLocation[1] == y) {
            agentIsInGoal = true;
        }

        return agentIsInGoal;
    }

    // +hasAgentWon(): boolean
    public boolean hasAgentWon() {
        boolean hasAgentWon = false;

        if (agentIsInGoal()) {
            hasAgentWon = true;
        }
        return hasAgentWon;
    }

    // +hasAgentLost(): boolean
    public boolean hasAgentLost() {
        boolean hasAgentLost = false;
        if (turnCounter >= 50) {
            hasAgentLost = true;
        }
        return hasAgentLost;
    }

    // +moveAgent(): void
    public void moveAgent() {
        int direction = agent.move(); // Initial random direction
        GenericTile location = maze[agentLocation[0]][agentLocation[1]];

        int chanceOfSpecial = random.nextInt(99);

        if (direction == 0 && (agentLocation[1] > 0) && isMoveLegal(direction)) { // North
            if (maze[(agentLocation[0])][(agentLocation[1] - 1)] instanceof SolidTile) {
                System.out
                        .println("Saw a solid tile at (" + agentLocation[0] + ", " + (agentLocation[1] - 1) + ")");
            } else {
                agentLocation[1]--;

                maze[agentLocation[0]][agentLocation[1] + 1].exitAction();
                turnCounter++;
                location.enterAction();
                if (location instanceof RotatingTile) {
                    System.out.println(
                            "Tile (" + agentLocation[0] + ", " + (agentLocation[1] + 1) + ") rotates clockwise");
                }
                if (chanceOfSpecial == 0) {
                    location.specialAction();
                }
                printAgentLocation();

            }
        } else if (direction == 1 && (agentLocation[0] < maze.length - 1) && isMoveLegal(direction)) { // East
            if (maze[(agentLocation[0] + 1)][agentLocation[1]] instanceof SolidTile) {
                System.out
                        .println("Saw a solid tile at (" + (agentLocation[0] + 1) + ", " + agentLocation[1] + ")");
            } else {
                agentLocation[0]++;
                maze[agentLocation[0] - 1][agentLocation[1]].exitAction();
                turnCounter++;
                location.enterAction();
                if (location instanceof RotatingTile) {
                    System.out.println(
                            "Tile (" + (agentLocation[0] - 1) + ", " + agentLocation[1] + ") rotates clockwise");
                }
                if (chanceOfSpecial == 0) {
                    location.specialAction();
                }
                printAgentLocation();
            }

        } else if (direction == 2 && (agentLocation[1] < maze.length - 1) && isMoveLegal(direction)) { // South
            if (maze[agentLocation[0]][(agentLocation[1] + 1)] instanceof SolidTile) {
                System.out
                        .println("Saw a solid tile at (" + agentLocation[0] + ", " + (agentLocation[1] + 1) + ")");
            } else {
                agentLocation[1]++;
                maze[agentLocation[0]][agentLocation[1] - 1].exitAction();
                turnCounter++;
                location.enterAction();
                if (location instanceof RotatingTile) {
                    System.out.println(
                            "Tile (" + agentLocation[0] + ", " + (agentLocation[1] - 1) + ") rotates clockwise");
                }
                if (chanceOfSpecial == 0) {
                    location.specialAction();
                }
                printAgentLocation();
            }

        } else if (direction == 3 && (agentLocation[0] > 0) && isMoveLegal(direction)) { // West
            if (maze[(agentLocation[0] - 1)][(agentLocation[1])] instanceof SolidTile) {
                System.out.println(
                        "Saw a solid tile at (" + (agentLocation[0] - 1) + ", " + (agentLocation[1]) + ")");
            } else {
                agentLocation[0]--;
                maze[agentLocation[0] + 1][agentLocation[1]].exitAction();
                turnCounter++;
                location.enterAction();

                if (location instanceof RotatingTile) {
                    System.out.println(
                            "Tile (" + agentLocation[0] + 1 + ", " + (agentLocation[1]) + ") rotates clockwise");
                }
                if (chanceOfSpecial == 0) {
                    location.specialAction();
                }
                printAgentLocation();
            }

        }

    }

    public boolean checkSolid(int direction) {
        boolean isSolid = false;
        switch (direction) {
            case 0:
                if (maze[agentLocation[0]][agentLocation[1] + 1].getDescription().equals("Static")) {
                    isSolid = true;
                    System.out.println("Stopped North!");
                }
                break;
            case 1:
                if (maze[agentLocation[0] + 1][agentLocation[1]].getDescription().equals("Static")) {
                    isSolid = true;
                    System.out.println("Stopped East!");
                }
                break;
            case 2:
                if (maze[agentLocation[0]][agentLocation[1] - 1].getDescription().equals("Static")) {
                    isSolid = true;
                    System.out.println("Stopped South!");
                }
                break;
            case 3:
                if (maze[agentLocation[0] - 1][agentLocation[1]].getDescription().equals("Static")) {
                    isSolid = true;
                    System.out.println("Stopped West!");
                }
                break;
        }
        return isSolid;
    }

    // +isMoveLegal(): boolean
    public boolean isMoveLegal(int direction) {
        boolean isMoveLegal = false;
        if (!isDoorLocked(maze[agentLocation[0]][agentLocation[1]], direction)) {
            isMoveLegal = true;
        } else {
            turnCounter++;
            System.out.println("Invalid Move on Turn " + turnCounter);
        }
        return isMoveLegal;
    }

    // +isDoorLocked(): boolean
    public boolean isDoorLocked(GenericTile tile, int direction) {
        boolean isDoorLocked = false;
        boolean[] doors = tile.getExits();

        if (doors[direction]) {
            isDoorLocked = false;
        } else {
            isDoorLocked = true;
        }
        return isDoorLocked;
    }

    // +printAgentLocation(): void
    public void printAgentLocation() {
        System.out.println(
                "Agent location: (" + agentLocation[0] + ", " + agentLocation[1] + ") on Turn " + turnCounter);
    }

    // +setAgent(GenericAgent): void
    public void setAgent(GenericAgent agent) {
        this.agent = agent;
    }

    // Getters and Setters

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

}
