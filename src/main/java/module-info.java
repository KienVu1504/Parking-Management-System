module com.example.pakingmanagementsystem {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;


  opens com.example.pakingmanagementsystem to javafx.fxml;
  exports com.example.pakingmanagementsystem;
}