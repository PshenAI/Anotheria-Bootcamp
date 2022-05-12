package main;

public class Main {

    public static int[] allX = { -2, -1, 1, 2, 2, 1, -1, -2 };
    public static int[] allY = { 1, 2, 2, 1, -1, -2, -2, -1 };

    public static int[][] board = new int[8][8];
    public static int[] takenRowCell = new int[64];
    public static int[] takenColCell = new int[64];


    public static int currentX = 0, currentY = 0, counter = 0, bLength = board.length;

    public static void main(String[] args) {

        //===============================
        //BOARD FILLING
        //===============================

        displayBoard();

        //===============================
        //SOLUTION
        //===============================

        takeStep();

        //===============================
        //DISPLAY
        //===============================

        displayBoard();

    }


    public static void takeStep() {
        moveForward();

        int allMoves, potentialX, potentialY;

        for (allMoves = 0; allMoves <= 7; allMoves++) {
            if(counter >= 64){
                return;
            }
            potentialX = currentX + allX[allMoves];
            potentialY = currentY + allY[allMoves];

            if ( potentialX < 0 || potentialX > 7 || potentialY < 0 || potentialY > 7 ){//within border check
                continue;
            }
            if ( board[potentialX][potentialY] > 0 ){//check if knight already 've been here
                continue;
            }
            currentX = potentialX; currentY = potentialY;
            takeStep();
        }
        counter-- ;

        moveBackward();
    }

    public static void moveBackward(){
        board[takenRowCell[counter]][takenColCell[counter]] = 0;
        counter--;
        currentX = takenRowCell[counter]; currentY = takenColCell[counter];
        counter++;
    }

    public static void moveForward(){
        board[currentX][currentY] = counter;
        takenRowCell[counter] = currentX;
        takenColCell[counter] = currentY;
        counter++;
    }

    public static void displayBoard(){
        for (int k = 0; k < bLength; k++) {
            for (int l = 0; l < bLength; l++) {
                System.out.print(board[k][l] + " ");
            }
            System.out.println();
        }
        System.out.println("\n\n\n");
    }
}
