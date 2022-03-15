package com.example.airlineproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Button button_signUp;

    @FXML
    private Button button_loggin;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, javafx.stage.Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    protected void change(Button button, String name) throws Exception {
        Parent content = FXMLLoader.load(getClass().getResource(name));

        Stage window = (Stage) button.getScene().getWindow();
        window.setScene(new Scene(content, 562, 648));


    }


    @FXML
    protected void onSignUpButtonClick() throws Exception {
        Parent content = FXMLLoader.load(getClass().getResource("Sign-Up.fxml"));

        Stage window = (Stage) button_signUp.getScene().getWindow();
        window.setScene(new Scene(content, 562, 648));


    }

    public void whenlogin(javafx.event.ActionEvent event) throws Exception {
        Window owner = button_loggin.getScene().getWindow();

        System.out.println(tf_username.getText());
        System.out.println(tf_password.getText());

        if (tf_username.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (tf_password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = tf_username.getText();
        String password = tf_password.getText();

        String emailId2 = emailId.substring(0, 5);

        ConnectionUtil jdbcDao = new ConnectionUtil();
        System.out.println(emailId2);

        if (!emailId2.equals("admin")) {
            boolean flag = jdbcDao.validate(emailId, password);

            if (!flag) {
                infoBox("Please enter correct Email and Password", null, "Failed");
            } else {
                change(button_loggin, "Logged.fxml");
            }
        } else {
            boolean flag2 = jdbcDao.validate(emailId, password);

            if (!flag2) {
                infoBox("Please enter correct Email and Password", null, "Failed");
            } else {
                change(button_loggin, "Admin.fxml");
            }
        }
    }
}