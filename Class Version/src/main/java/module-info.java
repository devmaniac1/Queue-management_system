module com.example.foodiesfavequeuemanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foodiesfavequeuemanagement to javafx.fxml;
    exports com.example.foodiesfavequeuemanagement;
}