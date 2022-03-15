package com.example.airlineproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_from;

    @FXML
    private TextField tf_to;

    @FXML
    private DatePicker date;

    @FXML
    private TextField tf_hour;

    @FXML
    private TextField tf_price;

    @FXML
    private ChoiceBox<String> choice_class;

    @FXML
    private Button button_add;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice_class.setValue("economic");
        choice_class.getItems().add("economic");
        choice_class.getItems().add("bussines");

    }



    @FXML
    public void register2(javafx.event.ActionEvent event) throws SQLException {

        Window owner = button_add.getScene().getWindow();


        if (tf_from.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter take-off place");
            return;
        }
        if (tf_to.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a destination");
            return;
        }

        if (choice_class.getSelectionModel().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose a class");
            return;
        }

        if (date.getValue().toString().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please select a date");
            return;
        }

        if (tf_hour.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter an hour");
            return;
        }

        if (tf_price.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter the price");
            return;
        }


        String from = tf_from.getText();
        String to = tf_to.getText();
        String datee = date.getValue().toString();
        String hour = tf_hour.getText();
        String price = tf_price.getText();
        String clas = choice_class.getSelectionModel().getSelectedItem();
        Integer idd = Integer.parseInt(tf_id.getText());


        ConnectionUtil jdbcDao = new ConnectionUtil();
        jdbcDao.insertRecord2( idd,from, to, datee, hour, price, clas);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "message!",
                "Added Succesfully");
    }

    private static void showAlert(Alert.AlertType alertType, javafx.stage.Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
