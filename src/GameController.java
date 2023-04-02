//*********************************************************
// Class: GameController
// Author: Simardeep Khinda
// Created: 03/03/2023
// Modified: 03/22/2023 - Added agent to move through the maze
//           03/29/2023 - Created Static, Rotating, and Solid tiles
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
//          +checkAtLeastOne(): void
//          +setType(GenericTile): void
//          +pickFileType(): GenericTile
//          +convertExitsToString(): void
//          +printMaze(): void
//          +printMazeSummary(): void
//          +playGame(): void
//          +agentIsInGoal(): boolean
//          +hasAgentWon(): boolean
//          +hasAgentLost(): boolean
//          +moveAgent(): void
//          +checkForRotating(int, int): void
//          +checkForSolid(int, int): void
//          +checkForSpecialAction(GenericTile): void
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
            GenericTile solidTile = maze[random.nextInt(maze.length)][random.nextInt(maze.length)];
            solidTile = new SolidTile();
            setType(solidTile);

        }
        if (foundStatic == false) {
            GenericTile staticTile = maze[random.nextInt(maze.length)][random.nextInt(maze.length)];
            staticTile = new StaticTile();
            setType(staticTile);

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

    public void playGame() {
        while (!hasAgentLost() && !hasAgentWon()) {
            moveAgent();
        }
        if (hasAgentWon()) {
            System.out.println("\nAgent has won the game!\n");
        } else if (hasAgentLost()) {
            System.out.println("\nAgent has lost the game.\n");
        }
    }

    public boolean agentIsInGoal() {
        boolean agentIsInGoal = false;
        int x = (maze.length - 1) / 2;
        int y = (maze.length - 1);

        if (agentLocation[0] == x && agentLocation[1] == y) {
            agentIsInGoal = true;
        }

        return agentIsInGoal;
    }

    public boolean hasAgentWon() {
        boolean hasAgentWon = false;

        if (agentIsInGoal()) {
            hasAgentWon = true;
        }
        return hasAgentWon;
    }

    public boolean hasAgentLost() {
        boolean hasAgentLost = false;
        if (turnCounter >= 50) {
            hasAgentLost = true;
        }
        return hasAgentLost;
    }

    public void moveAgent() {
        int direction = agent.move(); // Initial random direction
        GenericTile location = maze[agentLocation[0]][agentLocation[1]];

        if (direction == 0 && (agentLocation[1] > 0) && isMoveLegal(direction)) { // North
            turnCounter++;
            if (!(checkForSolid(agentLocation[0], (agentLocation[1] - 1)))) {
                agentLocation[1]--;
                maze[agentLocation[0]][agentLocation[1] + 1].exitAction();
                location.enterAction();
                printAgentLocation();
                checkForRotating(agentLocation[0], agentLocation[1] + 1);
                checkForSpecialAction(location);
            }
        } else if (direction == 1 && (agentLocation[0] < maze.length - 1) && isMoveLegal(direction)) { // East
            turnCounter++;
            if (!(checkForSolid((agentLocation[0] + 1), agentLocation[1]))) {
                agentLocation[0]++;
                maze[agentLocation[0] - 1][agentLocation[1]].exitAction();
                location.enterAction();
                printAgentLocation();
                checkForRotating(agentLocation[0] - 1, agentLocation[1]);
                checkForSpecialAction(location);
            }
        } else if (direction == 2 && (agentLocation[1] < maze.length - 1) && isMoveLegal(direction)) { // South
            turnCounter++;
            if (!(checkForSolid(agentLocation[0], (agentLocation[1] + 1)))) {
                agentLocation[1]++;
                maze[agentLocation[0]][agentLocation[1] - 1].exitAction();
                location.enterAction();
                printAgentLocation();
                checkForRotating(agentLocation[0], agentLocation[1] - 1);
                checkForSpecialAction(location);
            }
        } else if (direction == 3 && (agentLocation[0] > 0) && isMoveLegal(direction)) { // West
            turnCounter++;
            if (!(checkForSolid((agentLocation[0] - 1), agentLocation[1]))) {
                agentLocation[0]--;
                maze[agentLocation[0] + 1][agentLocation[1]].exitAction();
                location.enterAction();
                printAgentLocation();
                checkForRotating(agentLocation[0] + 1, agentLocation[1]);
                checkForSpecialAction(location);
            }
        }
    }

    public void checkForRotating(int agentX, int agentY) {
        if (maze[agentX][agentY] instanceof RotatingTile) {
            System.out.println("Tile (" + agentX + ", " + agentY + ") rotates clockwise");
        }
    }

    public boolean checkForSolid(int agentX, int agentY) {
        boolean isSolid = false;
        if (maze[agentX][agentY] instanceof SolidTile) {
            System.out.println("Turn: " + turnCounter + " | Tile (" + agentX + ", " + agentY + ") prevents entrance");
            isSolid = true;
        }
        return isSolid;
    }

    public void checkForSpecialAction(GenericTile location) {
        int chanceOfSpecial = random.nextInt(99);

        if (chanceOfSpecial == 0) {
            location.specialAction();
            System.out.println("Special action occurred at: (" + agentLocation[0] + ", " + agentLocation[1] + ")");
        }
    }

    // +isMoveLegal(): boolean
    public boolean isMoveLegal(int direction) {
        boolean isMoveLegal = false;
        if (!isDoorLocked(maze[agentLocation[0]][agentLocation[1]], direction)) {
            isMoveLegal = true;
        } else {
            turnCounter++;
            System.out.println("Turn: " + turnCounter + " | Invalid move");
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
                "Turn: " + turnCounter + " | Agent enters tile (" + agentLocation[0] + ", " + agentLocation[1] + ")");
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
