package main;

public class Main {

    public static final int n = 8;
    public static int[] queenRow = new int[n];
    public static int[] queenCol = new int[n];
    public static int totalCount = 0;

    public static void main(String[] args) {

        //===============================
        //SOLUTION
        //===============================

        setQueen(0);

        System.out.println("\n\n" + totalCount);

    }
    private static void setQueen(int currentCol) {

        if (currentCol == n) {
            queenDisplay();
            return;
        }

        for (int i = 0; i < n; i++) {
            queenCol[currentCol] = currentCol;
            queenRow[currentCol] = i;

            if (isTaken(currentCol)) {
                setQueen(currentCol + 1);
            }
        }
    }

    private static boolean isTaken(int currentCol) {//checks if queen  gets in conflict with others

        for (int i = 0; i < currentCol; i++) {
            if (queenRow[i] == queenRow[currentCol]) {//row check
                return false;
            }

            if ((currentCol - i) == Math.abs(queenRow[currentCol] - queenRow[i])) {//diagonal check
                return false;
            }
        }
        return true;
    }

    private static void queenDisplay() {
        System.out.print("\n");

        for (int value : queenRow){
            System.out.printf(value + "  ");
        }
        System.out.print("\n");
        for (int value : queenCol){
            System.out.printf(value + "  ");
        }


        System.out.print("\n\n");


        for (int i = 0; i < n; i++) {
            for (int value : queenRow) {
                if (value == i)
                    System.out.print("Q ");
                else
                    System.out.print("0 ");
            }
            System.out.print("\n");
        }
        totalCount++;
    }

}
