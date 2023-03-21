//*********************************************************
// Class: MGMain
// Author: Simardeep Khinda
// Created: 03/03/2023
// Modified: 
//
// Purpose: This class controls the maze and calls GameController to create the maze if the inputted maze size is an odd integer. It also prints the summary of the maze.
//
// Attributes: 
//
// Methods: +main(String[]): void
//			+createMaze(): void
//*********************************************************

import java.util.Scanner;

public class MGMain {
    Scanner in = new Scanner(System.in);
    GameController ctrl = new GameController();

    public static void main(String[] args) {
        MGMain game = new MGMain();

        game.createMaze();
    }

    public void createMaze() {

        int mazeSize;

        do {
            System.out.println("Enter the maze size: ");
            mazeSize = in.nextInt();

            if (mazeSize % 2 == 0) {
                System.out.println("The maze size must be an odd integer.");
            } else {
                ctrl.createMaze(mazeSize, mazeSize);
                ctrl.printMazeSummary();
                playGame();
            }
        } while (mazeSize % 2 == 0);
    }

    // TODO: +createAgent( Agent ): void

    public void playGame() {
        ctrl.playGame();
    }

}
