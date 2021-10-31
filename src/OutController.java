import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutController {
  private Scene scene;
  private Parent root;
  private Stage stage;
  @FXML
  private Label errorLabel1, errorLabel2, errorLabel;
  @FXML
  private Button getOutTimeButton,doneButton;
  @FXML
  private TextField ticketTextField, seatTextField, timeOutField, licensePlateTextField, vehicleTypeTextField, timeInTextField, parkingFeeTextField;
  @FXML
  private AnchorPane OutPane;
  public void closeAPP(){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    // Get the Stage.
    stage = (Stage) alert.getDialogPane().getScene().getWindow();
    // Add a custom icon.
    stage.getIcons().add(new Image("images/sgd.png"));
    if(alert.showAndWait().get() == ButtonType.OK){
      stage = (Stage) OutPane.getScene().getWindow();
      stage.close();
    }
  }
  public void limitLength() {
    licensePlateTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (licensePlateTextField.getText().length() > 10) {
          errorLabel.setTextFill(Color.RED);
          errorLabel.setText("License Plate length must be <= 10!");
          // if its 11th character then just setText to previous
          // one
          licensePlateTextField.setText(licensePlateTextField.getText().substring(0, 10));
        } else {
          errorLabel1.setText("");
        }
      }
    });
  }
  public void outCheck(){

  }
  @FXML
  private MenuBar menuBar;
  public void goToIn() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  public void getTimeOut(){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    timeOutField.setText(dtf.format(now));
  }
  public void licensePlateSearch(){
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsystem", "root", "");
      preparedStatement = connection.prepareStatement("select * from parking where license_plate = ?");
      preparedStatement.setString(1, licensePlateTextField.getText());
      resultSet = preparedStatement.executeQuery();
      if (!resultSet.next()) {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Can't find " + licensePlateTextField.getText());
        errorLabel1.setTextFill(Color.RED);
        errorLabel1.setText("!");
      } else {
        errorLabel1.setText("");
        preparedStatement = connection.prepareStatement("select * from parking where license_plate = ?");
        preparedStatement.setString(1, licensePlateTextField.getText());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          vehicleTypeTextField.setText(resultSet.getString("type"));
          seatTextField.setText(resultSet.getString("seat"));
          if (resultSet.getInt("ticket") == 0) {
            ticketTextField.setText("No");
          } else {
            ticketTextField.setText("Yes");
          }
          timeInTextField.setText(resultSet.getString("time_in"));
        }
      }
    } catch (SQLException e) {
      Logger.getLogger(OutController.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(OutController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }
  public void goToHistory() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HistoryScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  public void goToAdmin() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  public void goToHelp() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HelpScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  public void goToAbout() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AboutScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  public void logout() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
