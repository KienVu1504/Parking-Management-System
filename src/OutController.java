import animatefx.animation.BounceIn;
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
import repositories.Database;

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
  private Label errorLabel1, errorLabel2, errorLabel, errorLabel3, errorLabel4, errorLabel5, errorLabel6, errorLabel7;
  @FXML
  private TextField parkingTimeTextField, ticketTextField, seatTextField, timeOutField, licensePlateTextField, vehicleTypeTextField, timeInTextField, parkingFeeTextField;
  @FXML
  private AnchorPane OutPane;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) OutPane.getScene().getWindow();
      stage.close();
    }
  }

  public void limitLength() {
    licensePlateTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (licensePlateTextField.getText().length() > 10) {
          new BounceIn(errorLabel).play();
          errorLabel.setTextFill(Color.RED);
          errorLabel.setText("License Plate length must be <= 10!");
          new BounceIn(errorLabel1).play();
          errorLabel1.setTextFill(Color.RED);
          errorLabel1.setText("!");
          licensePlateTextField.setText(licensePlateTextField.getText().substring(0, 10));
        } else {
          errorLabel.setText("");
          errorLabel1.setText("");
          errorLabel2.setText("");
          errorLabel3.setText("");
          errorLabel4.setText("");
          errorLabel5.setText("");
          errorLabel6.setText("");
          errorLabel7.setText("");
        }
      }
    });
  }

  public void outCheck() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    if (licensePlateTextField.getText().isEmpty() && vehicleTypeTextField.getText().isEmpty()) {
      new BounceIn(errorLabel1).play();
      errorLabel1.setTextFill(Color.RED);
      errorLabel1.setText("!");
      new BounceIn(errorLabel2).play();
      errorLabel2.setTextFill(Color.RED);
      errorLabel2.setText("!");
      new BounceIn(errorLabel3).play();
      errorLabel3.setTextFill(Color.RED);
      errorLabel3.setText("!");
      new BounceIn(errorLabel4).play();
      errorLabel4.setTextFill(Color.RED);
      errorLabel4.setText("!");
      new BounceIn(errorLabel5).play();
      errorLabel5.setTextFill(Color.RED);
      errorLabel5.setText("!");
      new BounceIn(errorLabel6).play();
      errorLabel6.setTextFill(Color.RED);
      errorLabel6.setText("!");
      new BounceIn(errorLabel7).play();
      errorLabel7.setTextFill(Color.RED);
      errorLabel7.setText("!");
      new BounceIn(errorLabel).play();
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Please fill all text field before submit!");
    } else if (!licensePlateTextField.getText().isEmpty() && vehicleTypeTextField.getText().isEmpty()) {
      new BounceIn(errorLabel1).play();
      new BounceIn(errorLabel2).play();
      new BounceIn(errorLabel3).play();
      new BounceIn(errorLabel4).play();
      new BounceIn(errorLabel5).play();
      new BounceIn(errorLabel6).play();
      new BounceIn(errorLabel7).play();
      new BounceIn(errorLabel).play();
      errorLabel1.setTextFill(Color.RED);
      errorLabel1.setText("!");
      errorLabel2.setTextFill(Color.RED);
      errorLabel2.setText("!");
      errorLabel3.setTextFill(Color.RED);
      errorLabel3.setText("!");
      errorLabel4.setTextFill(Color.RED);
      errorLabel4.setText("!");
      errorLabel5.setTextFill(Color.RED);
      errorLabel5.setText("!");
      errorLabel6.setTextFill(Color.RED);
      errorLabel6.setText("!");
      errorLabel7.setTextFill(Color.RED);
      errorLabel7.setText("!");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Please click search button!");
    } else if (!licensePlateTextField.getText().isEmpty() && !vehicleTypeTextField.getText().isEmpty()) {
      try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsystem", "root", "");
        preparedStatement = connection.prepareStatement("select * from parking where license_plate = ? AND status = 1");
        preparedStatement.setString(1, licensePlateTextField.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          preparedStatement = connection.prepareStatement("update parking set time_out=?, parking_time=?, fee=?, status=? where license_plate = ? AND status = 1");
          preparedStatement.setString(1, timeOutField.getText());
          preparedStatement.setString(2, parkingTimeTextField.getText());
          if (parkingFeeTextField.getText().equals("Free")) {
            preparedStatement.setString(3, "0");
          } else {
            preparedStatement.setString(3, parkingFeeTextField.getText());
          }
          preparedStatement.setString(4, "0");
          preparedStatement.setString(5, licensePlateTextField.getText());
          int kq = preparedStatement.executeUpdate();
          new BounceIn(errorLabel).play();
          if (kq > 0) {
            errorLabel.setTextFill(Color.GREEN);
            errorLabel.setText("Completed!");
          } else {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("We can't submitted your record at this time. Please try again!");
          }
        } else {
          new BounceIn(errorLabel).play();
          new BounceIn(errorLabel1).play();
          errorLabel.setTextFill(Color.RED);
          errorLabel.setText("Can't find " + licensePlateTextField.getText() + "!");
          errorLabel1.setTextFill(Color.RED);
          errorLabel1.setText("!");
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

  public void getTimeOut() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    timeOutField.setText(dtf.format(now));
    errorLabel2.setText("");
  }

  public void findDifference() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    try {
      Date d1 = sdf.parse(timeInTextField.getText());
      Date d2 = sdf.parse(timeOutField.getText());
      long difference_In_Time = d2.getTime() - d1.getTime();
      int parkingTime = (int) ((difference_In_Time / 1000) / 60);
      parkingTimeTextField.setText(String.valueOf(parkingTime));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void findDifferenceTicket() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("select expired_date from ticket where license_plate = ? AND status = 1");
      preparedStatement.setString(1, licensePlateTextField.getText());
      resultSet = preparedStatement.executeQuery();
      if (!resultSet.next()) {
        feeCal();
        if (ticketTextField.getText().equals("Yes")) {
          new BounceIn(errorLabel).play();
          new BounceIn(errorLabel2).play();
          errorLabel2.setTextFill(Color.RED);
          errorLabel2.setText("!");
          errorLabel.setTextFill(Color.RED);
          errorLabel.setText("You don't have a monthly ticket or your monthly ticket is suspended!");
        }
      } else {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
          LocalDateTime now = LocalDateTime.now();
          Date d1 = sdf.parse(resultSet.getString("expired_date"));
          Date d2 = sdf.parse(dtf.format(now));
          long difference_In_Time = d2.getTime() - d1.getTime();
          if (difference_In_Time < 0) {
            if (ticketTextField.getText().equals("Yes")) {
              parkingFeeTextField.setText("Free");
            } else {
              feeCal();
            }
          } else {
            if (!ticketTextField.getText().equals("No")) {
              feeCal();
              new BounceIn(errorLabel).play();
              new BounceIn(errorLabel2).play();
              errorLabel2.setTextFill(Color.RED);
              errorLabel2.setText("!");
              errorLabel.setTextFill(Color.RED);
              errorLabel.setText("Your monthly ticket is expired!");
            } else {
              feeCal();
            }
          }
        } catch (ParseException e) {
          e.printStackTrace();
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

  public void feeCal() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = Database.getInstance().getConnection();
      int parkingFee;
      int parkingTime = Integer.parseInt(parkingTimeTextField.getText());
      if (vehicleTypeTextField.getText().equals("Bicycles")) {
        if (parkingTime <= 240) {
          preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = 'bicycles'");
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            parkingFee = resultSet.getInt("m240") * parkingTime;
          } else {
            parkingFee = 50 * parkingTime;
          }
          parkingFeeTextField.setText(String.valueOf(parkingFee));
        }
        if (parkingTime > 240 && parkingTime <= 480) {
          preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = 'bicycles'");
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            parkingFee = resultSet.getInt("240t480") * parkingTime;
          } else {
            parkingFee = 9 * parkingTime;
          }
          parkingFeeTextField.setText(String.valueOf(parkingFee));
        }
        if (parkingTime > 480) {
          preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = 'bicycles'");
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            parkingFee = resultSet.getInt("480p") * parkingTime;
          } else {
            parkingFee = 11 * parkingTime;
          }
          parkingFeeTextField.setText(String.valueOf(parkingFee));
        }
      }
      if (vehicleTypeTextField.getText().equals("Motorbike")) {
        if (parkingTime <= 240) {
          preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = 'motorbike'");
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            parkingFee = resultSet.getInt("m240") * parkingTime;
          } else {
            parkingFee = 100 * parkingTime;
          }
          parkingFeeTextField.setText(String.valueOf(parkingFee));
        }
        if (parkingTime > 240 && parkingTime <= 480) {
          preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = 'motorbike'");
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            parkingFee = resultSet.getInt("240t480") * parkingTime;
          } else {
            parkingFee = 17 * parkingTime;
          }
          parkingFeeTextField.setText(String.valueOf(parkingFee));
        }
        if (parkingTime > 480) {
          preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = 'motorbike'");
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            parkingFee = resultSet.getInt("480p") * parkingTime;
          } else {
            parkingFee = 19 * parkingTime;
          }
          parkingFeeTextField.setText(String.valueOf(parkingFee));
        }
      }
      if (vehicleTypeTextField.getText().equals("Car")) {
        if (parkingTime <= 90) {
          if (seatTextField.getText().equals("4-8")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '4t8car'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("m90") * parkingTime;
            } else {
              parkingFee = 417 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
          if (seatTextField.getText().equals("9-29")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '9t29car'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("m90") * parkingTime;
            } else {
              parkingFee = 667 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
          if (seatTextField.getText().equals("30+")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '30pcar'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("m90") * parkingTime;
            } else {
              parkingFee = 834 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
        }
        if (parkingTime > 90 && parkingTime <= 1440) {
          if (seatTextField.getText().equals("4-8")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '4t8car'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("90t1440") * parkingTime;
            } else {
              parkingFee = 167 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
          if (seatTextField.getText().equals("9-29")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '9t29car'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("90t1440") * parkingTime;
            } else {
              parkingFee = 250 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
          if (seatTextField.getText().equals("30+")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '30pcar'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("90t1440") * parkingTime;
            } else {
              parkingFee = 334 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
        }
        if (parkingTime > 1440) {
          if (seatTextField.getText().equals("4-8")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '4t8car'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("1440p") * parkingTime;
            } else {
              parkingFee = 105 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
          if (seatTextField.getText().equals("9-29")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '9t29car'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("1440p") * parkingTime;
            } else {
              parkingFee = 209 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
          if (seatTextField.getText().equals("30+")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots where type = '30pcar'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              parkingFee = resultSet.getInt("1440p") * parkingTime;
            } else {
              parkingFee = 334 * parkingTime;
            }
            parkingFeeTextField.setText(String.valueOf(parkingFee));
          }
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

  public void licensePlateSearch() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsystem", "root", "");
      preparedStatement = connection.prepareStatement("select * from parking where license_plate = ? AND status = 1");
      preparedStatement.setString(1, licensePlateTextField.getText());
      resultSet = preparedStatement.executeQuery();
      if (!resultSet.next()) {
        new BounceIn(errorLabel).play();
        new BounceIn(errorLabel1).play();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Can't find " + licensePlateTextField.getText() + "!");
        errorLabel1.setTextFill(Color.RED);
        errorLabel1.setText("!");
      } else {
        errorLabel1.setText("");
        errorLabel2.setText("");
        errorLabel3.setText("");
        errorLabel4.setText("");
        errorLabel5.setText("");
        errorLabel6.setText("");
        errorLabel7.setText("");
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
        findDifferenceTicket();
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

  public void goToOut(ActionEvent event) throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OutScene.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
}
