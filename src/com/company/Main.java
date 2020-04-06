package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            String newMessage = getUserInput();
            setSqlData(newMessage);
            getSqlData();
        }
    }

    private static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Notiz eingeben: ");
        return scanner.nextLine();
    }

    private static Connection getConnectionToDB() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/note?user=root";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static void getSqlData() {
        Statement stmt = null;
        String query = "select * from notes";
        Connection conn = getConnectionToDB();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String message = rs.getString("message");
                Date date = rs.getDate("created_at");
                System.out.println(date + " " + message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setSqlData(String newMessage) {
        Statement stmt = null;
        String query = "INSERT INTO `notes`(`message`) VALUES ('" + newMessage + "')";
        Connection conn = getConnectionToDB();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
