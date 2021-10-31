import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class InController {
  private Scene scene;
  private Parent root;
  private Stage stage;
  @FXML
  private TextField licensePlateTextField, timeInField;
  @FXML
  private RadioButton vehicleBicycles, vehicleTypeCar, vehicleTypeMotorbike, monthlyTicketYes, monthlyTicketNo, carSeats1,carSeats2, carSeats3;
  @FXML
  private Label carSeatsLabel,errorLabel,errorLabel1, errorLabel2;
  @FXML
  private Button getInTimeButton, submitButton, resetButton;
  @FXML
  private AnchorPane InPane;
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
      stage = (Stage) InPane.getScene().getWindow();
      stage.close();
    }
  }
  public void carTypeChecked(){
    //set Car Seats section to disabled state each time "vehicleTypeCar" radiobutton is selected
    if (vehicleBicycles.isSelected() || vehicleTypeMotorbike.isSelected()){
      carSeatsLabel.setDisable(true);
      carSeats1.setDisable(true);
      carSeats2.setDisable(true);
      carSeats3.setDisable(true);
      if (vehicleBicycles.isSelected()){
        randomLP();
      }
    } else {
      carSeatsLabel.setDisable(false);
      carSeats1.setDisable(false);
      carSeats2.setDisable(false);
      carSeats3.setDisable(false);
    }
  }
  public void getTimeIn(){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    timeInField.setText(dtf.format(now));
    errorLabel2.setText("");
    //Calendar calendar = Calendar.getInstance();
    //timeInField.setText(String.valueOf(calendar.getTime()));
  }
  public void goToIn(ActionEvent event) throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InScene.fxml")));
    //Stage stage = (Stage) menuBar.getScene().getWindow();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  @FXML
  private MenuBar menuBar;
  public void goToOut() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OutScene.fxml")));
    //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
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
  public void limitLength() {
    licensePlateTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (licensePlateTextField.getText().length() > 10) {
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
  public void submitIn(){
    if (timeInField.getText().length() == 0 || licensePlateTextField.getText().length() == 0){
      if (licensePlateTextField.getText().length() == 0){
        errorLabel1.setText("!");
      }
      if (timeInField.getText().length() == 0){
        errorLabel2.setText("!");
      }
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Please fill all text field before submit!");
    } else {
      errorLabel.setTextFill(Color.GREEN);
      errorLabel.setText("Submitted!");
    }
  }
  public void randomLP() {
    // chose a Character random from this String
    String AlphaNumericString = "0123456789";
    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(10);
    for (int i = 0; i < 10; i++) {
      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index
        = (int)(AlphaNumericString.length()
        * Math.random());
      // add Character one by one in end of sb
      sb.append(AlphaNumericString
        .charAt(index));
    }
    licensePlateTextField.setText(sb.toString());
  }
}
