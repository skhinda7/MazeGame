//*********************************************************
// Class: MGMain
// Author: Simardeep Khinda
// Created: 03/03/2023
// Modified: 03/22/2023 - Called GameController to run the game.
//
// Purpose: This class controls the maze and calls GameController to create the maze if the inputted maze size is an odd integer. It also prints the summary of the maze. Then, it calls GameController to run the actual game.
//
// Attributes: 
//
// Methods: +main(String[]): void
//			+createMaze(): void
//          +playGame(): void
//*********************************************************

import java.util.Scanner;

public class MGMain {
    GameController ctrl = new GameController();

    public static void main(String[] args) {
        MGMain game = new MGMain();

        game.createMaze();
    }

    public void createMaze() {

        int mazeSize;

        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the maze size: ");
            mazeSize = in.nextInt();

            if (mazeSize % 2 == 0) {
                System.out.println("The maze size must be an odd integer.");
            }
        } while (mazeSize % 2 == 0);
        System.out.println("\n");

        ctrl.createMaze(mazeSize, mazeSize);
        playGame();
    }

    // TODO: +createAgent( Agent ): void

    public void playGame() {
        ctrl.printMazeSummary();
        ctrl.playGame();
    }

}
