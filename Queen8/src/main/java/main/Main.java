package main;

import java.sql.*;

public class Main {

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/8queens";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Caracal2Postgre";
    private static Connection conn;

    public static final int n = 8;
    public static int[] queenRow = new int[n];
    public static int[] queenCol = new int[n];
    public static int totalCount = 1;

    public static void main(String[] args) {
        //===============================
        //CONFIGS
        //===============================
        try{
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            initDB();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //===============================
        //SOLUTION
        //===============================

        setQueen(0);

        System.out.println("\n\n" + (totalCount - 1));

    }

    private static void initDB() throws SQLException{
        try(Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS QUEENS");
            st.execute("CREATE TABLE QUEENS (ID SERIAL PRIMARY KEY, AXISX INT " +
                    "NOT NULL, AXISY INT NOT NULL, SOLUTION INT NOT NULL)");
        }
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
        try{
            for (int value : queenRow){
                System.out.printf(value + "  ");
            }
            System.out.print("\n");
            for (int value : queenCol){
                System.out.printf(value + "  ");
            }

            for (int i = 0; i < queenRow.length; i++) {
                try(PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO QUEENS (AXISX, AXISY, SOLUTION) VALUES(?, ?, ?)")){
                    ps.setInt(1, queenRow[i]);
                    ps.setInt(2, queenCol[i]);
                    ps.setInt(3, totalCount);
                    ps.executeUpdate();
                }

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
        } catch (SQLException ex){
            ex.printStackTrace();
        }


    }

}
