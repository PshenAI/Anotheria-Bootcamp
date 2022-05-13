package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static main.Main.n;

class MultiDoublySquare implements Runnable{
    int forI;
    int forJ;
    int compI;
    int compJ;
    int[][] mSquare;

    public MultiDoublySquare(int forI, int forJ, int compI, int compJ, int[][] mSquare) {
        this.forI = forI;
        this.forJ = forJ;
        this.compI = compI;
        this.compJ = compJ;
        this.mSquare = mSquare;
    }

    @Override
    public void run() {
        System.out.println("Thread started!");
        for (int i = forI; i < compI; i++){//All corners and center
            for (int j = forJ; j < compJ; j++){
                mSquare[i][j] = (n*n + 1) - mSquare[i][j];
            }
        }
        System.out.println("Work done!");
    }
}

public class Main {

    public static int n;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the 'n' number for the square: ");
        while(true){
            n = sc.nextInt();
            if(n < 3){
                System.out.println("You've entered wrong number, try again: ");
            } else{
               break;
            }
        }
        int[][] mSquare = new int[n][n];

        //===============================
        //SOLUTION
        //===============================
        long nano1 = System.nanoTime();


        if(n % 2 != 0){
            mSquare = oddMSquare(n);
        } else if(n % 4 == 0){
            doublyEvenMSquare(mSquare);
        } else {
            mSquare =singlyEvenMSquare(n);
        }

        long nano2 = System.nanoTime();

        System.out.println(nano2 - nano1);

        //===============================
        //DISPLAY
        //===============================

        String fileName = "MagicSquare" + n + ".txt";

        File fl = new File(fileName);
        if(fl.createNewFile()){
            try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))){
                for (int i = -1; i < n + 1; i++) {
                    for (int j = 0; j < n; j++) {
                        if(i == -1 || i == n){
                            pw.print("***");
                        } else {
                            pw.print(mSquare[i][j] + "  ");
                        }
                    }
                    pw.println();
                }
            }
        }
    }

    //===============================
    //DOUBLY-EVEN
    //===============================

    private static void doublyEvenMSquare(int[][] mSquare) {

        int[] iArr = {0,0,3*n/4,3*n/4,n/4};
        int[] jArr = {0,3*n/4,0,3*n/4,n/4};
        int[] compI = {n/4,n/4,n,n,3*n/4};
        int[] compJ = {n/4,n,n/4,n,3*n/4};

        //main formula -- (n*n+1)-mSquare[i][j]

        int i, j;

        for ( i = 0; i < n; i++){//filling the square from 1 to n*n
            for ( j = 0; j < n; j++){
                mSquare[i][j] = (n*i) + j + 1;
            }
        }

//        //===============================
//        //MULTITHREAD SOLUTION
//        //===============================
//
//        List<Thread> threadList = new ArrayList<>();
//
//        for (int k = 0; k < 5; k++) {
//            threadList.add(new Thread(new MultiDoublySquare(iArr[k],jArr[k],compI[k],compJ[k], mSquare)));
//        }
//
//        for (int k = 0; k < 5; k++) {
//            threadList.get(k).start();
//        }


        //===============================
        //NON MULTITHREAD SOLUTION
        //===============================

//        for ( i = 0; i < n/4; i++){//top-left corner
//            for ( j = 0; j < n/4; j++){
//                mSquare[i][j] = (n*n + 1) - mSquare[i][j];
//            }
//        }
//
//        for ( i = 0; i < n/4; i++){//top-right corner
//            for ( j = 3 * (n/4); j < n; j++){
//                mSquare[i][j] = (n*n + 1) - mSquare[i][j];
//            }
//        }
//
//        for ( i = 3 * n/4; i < n; i++){//bottom-left corner
//            for ( j = 0; j < n/4; j++){
//                mSquare[i][j] = (n*n + 1) - mSquare[i][j];
//            }
//        }
//
//        for ( i = 3 * n/4; i < n; i++){//bottom-right corner
//            for ( j = 3 * n/4; j < n; j++){
//                mSquare[i][j] = (n*n + 1) - mSquare[i][j];
//            }
//        }
//
//        for ( i = n/4; i < 3 * n/4; i++){//center of square
//            for ( j = n/4; j < 3 * n/4; j++){
//                mSquare[i][j] = (n*n + 1) - mSquare[i][j];
//            }
//        }
    }

    //===============================
    //SINGLY-EVEN
    //===============================

    private static int[][] singlyEvenMSquare(int n) {
        int mSquareSize = n * n;
        int nHalf = n / 2;

        int subSquareSize = mSquareSize / 4;//calc of 4 subsquares
        int[][] subSquare = oddMSquare(nHalf);
        int[] cornerFactors = {0, 2, 3, 1};

        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {//filling main square with 4 magic subsquares
            for (int j = 0; j < n; j++) {
                int quadrant = (i / nHalf) * 2 + (j / nHalf);
                result[i][j] = subSquare[i % nHalf][j % nHalf];
                result[i][j] += cornerFactors[quadrant] * subSquareSize;
            }
        }

        int leftCols = nHalf / 2;
        int rightCols = leftCols - 1;

        for (int i = 0; i < nHalf; i++){//switching key positions
            for (int j = 0; j < n; j++) {
                if (j < leftCols || j >= n - rightCols
                        || (j == leftCols && i == leftCols)) {

                    if (j == 0 && i == leftCols){
                        continue;
                    }

                    int temp = result[i][j];
                    result[i][j] = result[i + nHalf][j];
                    result[i + nHalf][j] = temp;
                }
            }
        }
        return result;
    }

    //===============================
    //ODD
    //===============================

    private static int[][] oddMSquare(int n) {
        int[][] mSquare = new int[n][n];
        int posX = 0, posY = 0;
        for (int i = 1; i <= n*n; i++) {
            if(i == 1){//
                posX = n/2;
                posY = n-1;
                mSquare[posX][posY] = i;
                continue;
            }
            posX = posX - 1;
            posY = posY + 1;
            if(posX == - 1 && posY == n){//3rd rule
                posX = 0; posY = n - 2;
            } else {//1st rule
                if(posX < 0){
                    posX = n - 1;
                }
                if (posY == n){
                    posY = 0;
                }
            }
            if(mSquare[posX][posY] != 0){//2nd rule
                posX = posX + 1; posY = posY - 2;
            }
            mSquare[posX][posY] = i;
        }
        return mSquare;
    }

}
