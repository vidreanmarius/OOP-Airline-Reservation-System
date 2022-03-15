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
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private Button button_logIn;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private Button button_register;

    @FXML
    public void register(javafx.event.ActionEvent event) throws SQLException {

        Window owner = button_register.getScene().getWindow();

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

        ConnectionUtil jdbcDao = new ConnectionUtil();
        jdbcDao.insertRecord( emailId, password);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                "Welcome " + tf_username.getText());
    }

    private static void showAlert(Alert.AlertType alertType, javafx.stage.Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }



    @FXML
    protected void onLogInUpButtonClick() throws Exception {
        Parent content = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

        Stage window = (Stage)button_logIn.getScene().getWindow();
        window.setScene(new Scene(content, 562, 648));


    }

}

