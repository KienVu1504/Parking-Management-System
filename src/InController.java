import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
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
  private Label carSeatsLabel;
  @FXML
  private Button getInTimeButton;
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
    } else {
      carSeatsLabel.setDisable(false);
      carSeats1.setDisable(false);
      carSeats2.setDisable(false);
      carSeats3.setDisable(false);
    }
  }
  public void getTimeIn(){
    Calendar calendar = Calendar.getInstance();
    timeInField.setText(String.valueOf(calendar.getTime()));
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
}
