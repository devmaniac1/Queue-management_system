package com.example.foodiesfavequeuemanagement;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {

    @FXML
    private TableColumn<Customer, Integer> burgerCount1;

    @FXML
    private TableColumn<Customer, Integer> burgerCount2;

    @FXML
    private TableColumn<Customer, Integer> burgerCount3;

    @FXML
    private TableColumn<Customer, Integer> burgerCountWaiting;

    @FXML
    private TextField filterField;

    @FXML
    private TableColumn<Customer, String> firstName1;

    @FXML
    private TableColumn<Customer, String> firstName2;

    @FXML
    private TableColumn<Customer, String> firstName3;

    @FXML
    private TableColumn<Customer, String> firstNameWaiting;

    @FXML
    private TableColumn<Customer, String> secondName1;

    @FXML
    private TableColumn<Customer, String> secondName2;

    @FXML
    private TableColumn<Customer, String> secondName3;

    @FXML
    private TableColumn<Customer, String> secondNameWaiting;

    @FXML
    private TableView<Customer> tableView1;

    @FXML
    private TableView<Customer> tableView2;

    @FXML
    private TableView<Customer> tableView3;

    @FXML
    private TableView<Customer> tableViewWaiting;


    public void initialize() {
        /*
        * Display contents to tableview Referred from
        * https://www.youtube.com/watch?v=fnU1AlyuguE
        */

        ObservableList<Customer> list1 = FXCollections.observableArrayList(Main.allQueue[0].getQueue());
        ObservableList<Customer> list2 = FXCollections.observableArrayList(Main.allQueue[1].getQueue());
        ObservableList<Customer> list3 = FXCollections.observableArrayList(Main.allQueue[2].getQueue());
        ObservableList<Customer> waitingList = FXCollections.observableArrayList();
        waitingList.addAll(Arrays.asList(Main.waiting));


        firstName1.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        secondName1.setCellValueFactory(new PropertyValueFactory<Customer, String>("secondName"));
        burgerCount1.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("burgersRequired"));

        tableView1.setItems(list1);

        firstName2.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        secondName2.setCellValueFactory(new PropertyValueFactory<Customer, String>("secondName"));
        burgerCount2.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("burgersRequired"));

        tableView2.setItems(list2);

        firstName3.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        secondName3.setCellValueFactory(new PropertyValueFactory<Customer, String>("secondName"));
        burgerCount3.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("burgersRequired"));

        tableView3.setItems(list3);

        firstNameWaiting.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        secondNameWaiting.setCellValueFactory(new PropertyValueFactory<Customer, String>("secondName"));
        burgerCountWaiting.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("burgersRequired"));

        tableViewWaiting.setItems(waitingList);

/*
* Search Referred From
* https://youtu.be/2M0L6w3tMOY
* */
        FilteredList<Customer> filteredData1 = new FilteredList<>(list1, p -> true);
        FilteredList<Customer> filteredData2 = new FilteredList<>(list2, p -> true);
        FilteredList<Customer> filteredData3 = new FilteredList<>(list3, p -> true);
        FilteredList<Customer> filteredData4 = new FilteredList<>(waitingList, p -> true);

        SortedList<Customer> sortedData1 = new SortedList<>(filteredData1);
        SortedList<Customer> sortedData2 = new SortedList<>(filteredData2);
        SortedList<Customer> sortedData3 = new SortedList<>(filteredData3);
        SortedList<Customer> sortedData4 = new SortedList<>(filteredData4);

        // Bind the sorted lists comparators to the corresponding table comparators
        sortedData1.comparatorProperty().bind(tableView1.comparatorProperty());
        sortedData2.comparatorProperty().bind(tableView2.comparatorProperty());
        sortedData3.comparatorProperty().bind(tableView3.comparatorProperty());
        sortedData4.comparatorProperty().bind(tableViewWaiting.comparatorProperty());

        // Set the sorted and filtered data to their respective tables initially
        tableView1.setItems(sortedData1);
        tableView2.setItems(sortedData2);
        tableView3.setItems(sortedData3);
        tableViewWaiting.setItems(sortedData4);
        // Bind the filterField text property to the filter predicates for each table
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData1.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getSecondName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });

            filteredData2.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getSecondName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });

            filteredData3.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getSecondName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });

            filteredData4.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (customer != null){
                    if (customer.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getSecondName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }

                return false;
            });

            // Clear the selection on all tables
            tableView1.getSelectionModel().clearSelection();
            tableView2.getSelectionModel().clearSelection();
            tableView3.getSelectionModel().clearSelection();
            tableViewWaiting.getSelectionModel().clearSelection();

            // Show the filtered data in the respective table
            if (!newValue.isEmpty()) {
                tableView1.setItems(sortedData1);
                tableView2.setItems(sortedData2);
                tableView3.setItems(sortedData3);
                tableViewWaiting.setItems(sortedData4);
            } else {
                // If search text is empty, display the original data in all tables
                tableView1.setItems(list1);
                tableView2.setItems(list2);
                tableView3.setItems(list3);
                tableViewWaiting.setItems(waitingList);
            }
        });
    }
}
