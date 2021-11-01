import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Date;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  private TextField parkingTimeTextField, ticketTextField, seatTextField, timeOutField, licensePlateTextField, vehicleTypeTextField, timeInTextField, parkingFeeTextField;
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
    if (licensePlateTextField.getText().length() == 0 || timeOutField.getText().length() == 0){
      if (licensePlateTextField.getText().length() == 0 || seatTextField.getText().length() != 0){
        errorLabel1.setTextFill(Color.RED);
        errorLabel1.setText("!");
      }
      if (timeOutField.getText().length() == 0){
        errorLabel2.setTextFill(Color.RED);
        errorLabel2.setText("!");
      }
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Please fill all text field before submit!");
    } else {
      errorLabel.setTextFill(Color.GREEN);
      errorLabel.setText("OK");
    }
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
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    timeOutField.setText(dtf.format(now));
    errorLabel2.setText("");
  }
  public void findDifference(){
    // SimpleDateFormat converts the
    // string format to date object
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    // Try Block
    try {
      // parse method is used to parse
      // the text from a string to
      // produce the date
      Date d1 = (Date) sdf.parse(timeInTextField.getText());
      Date d2 = (Date) sdf.parse(timeOutField.getText());
      // Calculate time difference
      // in milliseconds
      long difference_In_Time = d2.getTime() - d1.getTime();
      // Calculate time difference in
      // seconds, minutes, hours, years,
      // and days
      long difference_In_Seconds = (difference_In_Time / 1000) % 60;
      long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
      long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
      long difference_In_Years = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));
      long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
      // Print the date difference in
      // years, in days, in hours, in
      // minutes, and in seconds
      int parkingTime = (int) ((difference_In_Time / 1000) / 60);
      parkingTimeTextField.setText(String.valueOf(parkingTime));
    }
    // Catch the Exception
    catch (ParseException e) {
      e.printStackTrace();
    }
  }
  public void feeCal(){
    
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
        errorLabel.setText("");
        getTimeOut();
        findDifference();
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
