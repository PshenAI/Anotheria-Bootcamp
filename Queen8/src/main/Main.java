package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static List<String> takenCells = new ArrayList<>();
    public static List<String> queenCells = new ArrayList<>();
    public static List<String> brokeCells = new ArrayList<>();
    public static int[][] board = new int[8][8];
    public static AtomicBoolean check = new AtomicBoolean();

    public static void main(String[] args) {

        //===============================
        //BOARD FILLING
        //===============================

        int bLength = board.length;
        int qX = 0;
        int qY = 0;

        queenCells.add(qX + " " + qY);
        takenCellsAllocator(qX,qY,board);

        //===============================
        //SOLUTION
        //===============================

        int resCount = 0;
        int count = 0;
        for (int i = 0; i < bLength; i++) {
            if(i != qX){
                for (int j = 0; j < bLength; j++) {
                    if(j != qY){
                        if(count > 0){
                            count = 0;
                        }
                        if(!isTaken(i,j) && !isBroke(i,j)){//sets new queen
                            queenCells.add(i + " " + j);
                            takenCellsAllocator(i,j,board);
                            if(i == 7){
                                resCount++;

                            }
                            count = 1;
                            break;
                        }
                    }
                }
                if(count < 1){//deletes old brokeCells if previous in chain was deleted
                    String notBroke = String.valueOf(i);
                    removeOldQueen(count, notBroke);
                    i = i - 2;
                    count--;
                }
            }
        }

        //===============================
        //DISPLAY
        //===============================

        System.out.println(resCount);
        System.out.println(queenCells);

        for (int i = 0; i < bLength; i++) {
            for (int j = 0; j < bLength; j++) {
                if(isQueen(i,j)){
                    System.out.print("Q ");
                }
                System.out.print("_ ");
            }
            System.out.println();
        }

    }

    public static boolean isQueen(int x, int y){
        check.set(false);
        queenCells.forEach(a ->{
            String[] cell = a.split(" ");
            if(Integer.parseInt(cell[0]) == x &&
                    Integer.parseInt(cell[1]) == y){
                check.set(true);
            }});
        return check.get();
    }

    public static boolean isBroke(int x, int y) {
        check.set(false);
        brokeCells.forEach(a ->{
            String[] cell = a.split(" ");
            if(Integer.parseInt(cell[0]) == x &&
                    Integer.parseInt(cell[1]) == y){
                check.set(true);
            }});
        return check.get();
    }

    public static boolean isTaken(int x, int y){
        check.set(false);
        takenCells.forEach(a ->{
            String[] cell = a.split(" ");
            if(Integer.parseInt(cell[0]) == x &&
            Integer.parseInt(cell[1]) == y){
                check.set(true);
            }});
        return check.get();
    }

    public static void removeOldQueen(int count, String temp){
        String brokeCell = queenCells.get(queenCells.size() - 1);
        String[] lastQueen = brokeCell.split(" ");
        queenCells.remove(brokeCell);
        takenCellsCleaner(Integer.parseInt(lastQueen[0]),Integer.parseInt(lastQueen[1]),board);
        brokeCells.add(brokeCell);
        if(count < -1 && brokeCells.size() != 1){
            brokeCells.removeIf(a -> a.startsWith(temp));
        }
    }

    public static void takenCellsCleaner(int x, int y, int[][] board){
        int[] queenArr = {x,y};
        int bLength = board.length;
        for (int i = 0; i < bLength; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(i == queenArr[0] || j == queenArr[1]){//vertical&horizontal
                    takenCells.remove(i + " " + j);
                }
                for (int k = 1; k < bLength; k++) {//diagonal
                    if((i + k == queenArr[0] && j + k == queenArr[1])
                            || (i - k == queenArr[0] && j - k == queenArr[1])){
                        takenCells.remove(i + " " + j);
                    }
                    if((i + k == queenArr[0] && j - k == queenArr[1])
                            || (i - k == queenArr[0] && j + k == queenArr[1])){
                        takenCells.remove(i + " " + j);
                    }
                }
            }
        }
    }

    public static void takenCellsAllocator(int x, int y, int[][] board){
        int[] queenArr = {x,y};
        int bLength = board.length;
        for (int i = 0; i < bLength; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(i == queenArr[0] || j == queenArr[1]){//vertical&horizontal
                    takenCells.add(i + " " + j);
                }
                for (int k = 1; k < bLength; k++) {//diagonal
                    if((i + k == queenArr[0] && j + k == queenArr[1])
                            || (i - k == queenArr[0] && j - k == queenArr[1])){
                        takenCells.add(i + " " + j);
                    }
                    if((i + k == queenArr[0] && j - k == queenArr[1])
                            || (i - k == queenArr[0] && j + k == queenArr[1])){
                        takenCells.add(i + " " + j);
                    }
                }
            }
        }
    }
}
