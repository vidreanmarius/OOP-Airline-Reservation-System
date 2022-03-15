package com.example.airlineproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class ConnectionUtil {

    // Replace below database url, username and password with your actual database credentials
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/oop_project";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "Oopproject2021";
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE username = ? and password = ?";
    private static final String SELECT_QUERY2 = "SELECT * FROM flights WHERE fromm = ? and too = ? and date = ?";
    private static final String INSERT_QUERY = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String INSERT_QUERY2 = "INSERT INTO flights (idflights, fromm, too, date, hour, price, clas) VALUES (?, ?, ?, ?, ?, ?, ?)";

    Connection conn = null;

    public static Connection ConnectionDB(){
        try{
            Connection con = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            return con;
        }catch(Exception e){
            return null;
        }
    }

    public static ObservableList<ModelTable> getData(String fromm, String too, DatePicker date){
        Connection con = ConnectionDB();
        ObservableList<ModelTable>list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement(SELECT_QUERY2);
            ps.setString(1, fromm);
            ps.setString(2, too);
            ps.setString(3, ((TextField)date.getEditor()).getText());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new ModelTable(Integer.parseInt(rs.getString("idflights")), rs.getString("clas"), rs.getString("hour"), rs.getString("price")));

            }
        }catch(Exception e){

        }
        return list;
    }



    public boolean validate(String emailId, String password) throws SQLException {

        //  Establishing a Connection

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             //Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, emailId);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public boolean validate2(String FROMM, String TO, DatePicker date) throws SQLException {

        // Establishing a Connection and
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY2)) {
            preparedStatement.setString(1, FROMM);
            preparedStatement.setString(2, TO);
            preparedStatement.setString(3, ((TextField)date.getEditor()).getText());

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch (SQLException e) {

            printSQLException(e);
        }
        return false;
    }

    public void insertRecord(String emailId, String password) throws SQLException {

        // Establishing a Connection and

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, emailId);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);
            //  Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void insertRecord2(Integer id, String fromm, String too, String date, String hour, String price, String clas) throws SQLException {

        // Establishing a Connection and

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY2)) {
            preparedStatement.setInt(1, id );
            preparedStatement.setString(2, fromm);
            preparedStatement.setString(3, too);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, hour);
            preparedStatement.setString(6, price);
            preparedStatement.setString(7, clas);


            System.out.println(preparedStatement);
            //Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}