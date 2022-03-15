package com.example.airlineproject;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.pdf.*;


public class LoggedInController implements Initializable {

    @FXML
    private Button button_search;

    @FXML
    private Button button_print;

    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<String> choice_from;

    @FXML
    private ChoiceBox<String> choice_to;

    @FXML
    private TableView <ModelTable>table;

    @FXML
    private TableColumn<ModelTable, Integer> col_id;

    @FXML
    private TableColumn<ModelTable, String> col_class;

    @FXML
    private TableColumn<ModelTable, String> col_hour;

    @FXML
    private TableColumn<ModelTable, String> col_price;



    private String[] locations = {"Bucharest", "London", "Paris", "Cluj"};

    ObservableList<ModelTable> oblist;

    int index = -1;
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice_from.setValue("Bucharest");
        choice_from.getItems().addAll(locations);

        choice_to.setValue("Bucharest");
        choice_to.getItems().addAll(locations);

    }

    @FXML
    public void whenSearch(javafx.event.ActionEvent event) throws Exception {
        Window owner = button_search.getScene().getWindow();

        System.out.println(choice_from.getSelectionModel().getSelectedItem());
        System.out.println(choice_to.getSelectionModel().getSelectedItem());
        System.out.println(date.getValue());

        if (choice_from.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter Starting Location!");
            return;
        }
        if (choice_to.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a destination!");
            return;
        }

        String frommm = choice_from.getSelectionModel().getSelectedItem();
        String too = choice_to.getSelectionModel().getSelectedItem();

        ConnectionUtil jdbcDao = new ConnectionUtil();
        boolean flag = jdbcDao.validate2(frommm, too, date);

        if (!flag) {
            infoBox("Route not found!", null, "Failed");
        } else {
            System.out.println("mergeeeeee");
            col_id.setCellValueFactory(new PropertyValueFactory<ModelTable, Integer>("idflights"));
            col_class.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("clas"));
            col_hour.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("hour"));
            col_price.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("price"));

            oblist = ConnectionUtil.getData(frommm, too, date);
            table.setItems(oblist);
        }
    }

    @FXML
    public void whenPrint(javafx.event.ActionEvent event)throws Exception{
        Window owner = button_print.getScene().getWindow();

        if(table.getSelectionModel().getSelectedItem() != null){
            System.out.println("ar trebui sa mearga printu");
            ModelTable selection = table.getSelectionModel().getSelectedItem();
            Integer id = selection.getIdflights();
            String fromm = choice_from.getSelectionModel().getSelectedItem();
            String too = choice_to.getSelectionModel().getSelectedItem();
            String clas = selection.getClas();
            String hour = selection.getHour();
            String price = selection.getPrice();

            try {
                String file_name = "C:\\Users\\vid_m\\OneDrive\\Desktop\\Faculta 2\\OOP\\Airline-Reservation-System\\AirlineProject\\intinerary.pdf";
                Document document = new Document();

                PdfWriter.getInstance(document, new FileOutputStream(file_name));

                document.open();

                Paragraph para1 = new Paragraph("Thanks for booking with us!\n");
                Paragraph para2 = new Paragraph("Your chosen flight is:\n");
                Paragraph para3 = new Paragraph("Your flight number is " +id+". "+fromm+"->"+too+" on "+ clas+" class " + " at "+ hour+ " with "+price+ "$");
                document.add(para1);
                document.add(para2);
                document.add(para3);


                document.close();

                System.out.println("finished generating");

            }catch(Exception e){
                System.err.println(e);
            }

        }

    }


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

}
