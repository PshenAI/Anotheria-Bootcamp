package main;

import java.util.Arrays;

public class Main {

    private static final int n = 5;
    public static int[][] mSquare = new int[n][n];
    public static int mLength = mSquare.length;

    public static void main(String[] args) {

        //===============================
        //SOLUTION
        //===============================

        int desiredSum = n*((n*n) + 1) / 2;
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

        //===============================
        //DISPLAY
        //===============================

        for (int i = 0; i < mLength; i++) {
            for (int j = 0; j < mLength; j++) {
                System.out.print(mSquare[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean diagCheck1(){//checks diagonals
        int sum = 0;
        for (int j = 0; j < mLength; j++) {
            sum = sum + mSquare[j][j];
        }
        return sum == mLength * mLength;
    }

    public static boolean diagCheck2(){//checks diagonals
        int sum = 0;
        int k = 4;
        for (int j = 0; j < mLength; j++) {
            sum = sum + mSquare[j][k];
            k--;
        }
        return sum == mLength * mLength;
    }


    public static boolean vrtclCheck(int y){//checks vertical line
        int sum = 0;
        for (int j = 0; j < mLength; j++) {
            sum = sum + mSquare[j][y];
        }
        return sum == mLength * mLength;
    }

    public static boolean hrzntCheck(int x){//checks horizontal line
        int sum = 0;
        for (int j = 0; j < mLength; j++) {
            sum = sum + mSquare[x][j];
        }
        return sum == mLength * mLength;
    }

}
