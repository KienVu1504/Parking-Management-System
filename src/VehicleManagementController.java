import animatefx.animation.BounceIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import repositories.Database;

import java.sql.Connection;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleManagementController {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane vehicleManagementPane;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) vehicleManagementPane.getScene().getWindow();
      stage.close();
    }
  }

  public void licensePlateSearch() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("select * from parking where license_plate = ? AND status = 1"); //AND status = 1
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
        preparedStatement = connection.prepareStatement("select * from parking where license_plate = ?");
        preparedStatement.setString(1, licensePlateTextField.getText());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          if (resultSet.getString("type").equals("Bicycles")) {
            vehicleBicycles.setSelected(true);
            if (vehicleBicycles.isSelected()) {
              vehicleTypeLabel.setDisable(false);
              vehicleBicycles.setDisable(false);
              vehicleTypeMotorbike.setDisable(false);
              vehicleTypeCar.setDisable(false);
              monthlyTicketLabel.setDisable(false);
              ticketYes.setDisable(false);
              ticketNo.setDisable(false);
            }
          } else if (resultSet.getString("type").equals("Motorbike")) {
            vehicleTypeMotorbike.setSelected(true);
            if (vehicleTypeMotorbike.isSelected()) {
              vehicleTypeLabel.setDisable(false);
              vehicleBicycles.setDisable(false);
              vehicleTypeMotorbike.setDisable(false);
              vehicleTypeCar.setDisable(false);
              monthlyTicketLabel.setDisable(false);
              ticketYes.setDisable(false);
              ticketNo.setDisable(false);
            }
          } else if (resultSet.getString("type").equals("Car")) {
            vehicleTypeCar.setSelected(true);
            if (vehicleTypeCar.isSelected()) {
              vehicleTypeLabel.setDisable(false);
              vehicleBicycles.setDisable(false);
              vehicleTypeMotorbike.setDisable(false);
              vehicleTypeCar.setDisable(false);
              monthlyTicketLabel.setDisable(false);
              ticketYes.setDisable(false);
              ticketNo.setDisable(false);
              carSeatsLabel.setDisable(false);
              carSeats1.setDisable(false);
              carSeats2.setDisable(false);
              carSeats3.setDisable(false);
              if (resultSet.getString("seat").equals("4-8")) {
                carSeats1.setSelected(true);
              } else if (resultSet.getString("seat").equals("9-29")) {
                carSeats2.setSelected(true);
              } else if (resultSet.getString("seat").equals("30+")) {
                carSeats3.setSelected(true);
              }
            }
          }
          if (resultSet.getInt("ticket") == 0) {
            ticketNo.setSelected(true);
          } else {
            ticketYes.setSelected(true);
          }
        }
        errorLabel.setText("");
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

  public void updateCheck() {
    if (licensePlateTextField.getText().length() == 0 && vehicleTypeLabel.isDisable() && monthlyTicketLabel.isDisable()) {
      new BounceIn(errorLabel).play();
      new BounceIn(errorLabel1).play();
      errorLabel1.setTextFill(Color.RED);
      errorLabel1.setText("!");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Please fill all fields before update!");
    } else if (!licensePlateTextField.getText().isEmpty() && vehicleTypeLabel.isDisable() && monthlyTicketLabel.isDisable()) {
      new BounceIn(errorLabel).play();
      new BounceIn(errorLabel1).play();
      errorLabel1.setTextFill(Color.RED);
      errorLabel1.setText("!");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Please click search button!");
    } else {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        connection = Database.getInstance().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM parking LIMIT 0,1");
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
          new BounceIn(errorLabel).play();
          errorLabel.setTextFill(Color.RED);
          errorLabel.setText("List is empty!");
        } else {
          preparedStatement = connection.prepareStatement("select * from parking where license_plate = ? AND status = 1");//AND status = 1
          preparedStatement.setString(1, licensePlateTextField.getText());
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            preparedStatement = connection.prepareStatement("update parking set type=?, seat=?, ticket=? where license_plate = ? AND status = 1"); //AND status = 1
            if (vehicleBicycles.isSelected()) {
              preparedStatement.setString(1, "Bicycles");
              preparedStatement.setString(2, "0");
            } else if (vehicleTypeMotorbike.isSelected()) {
              preparedStatement.setString(1, "Motorbike");
              preparedStatement.setString(2, "0");
            } else if (vehicleTypeCar.isSelected()) {
              preparedStatement.setString(1, "Car");
              if (carSeats1.isSelected()) {
                preparedStatement.setString(2, "4-8");
              } else if (carSeats2.isSelected()) {
                preparedStatement.setString(2, "9-29");
              } else if (carSeats3.isSelected()) {
                preparedStatement.setString(2, "30+");
              }
            }
            if (ticketYes.isSelected()) {
              preparedStatement.setString(3, "1");
            } else if (ticketNo.isSelected()) {
              preparedStatement.setString(3, "0");
            }
            preparedStatement.setString(4, licensePlateTextField.getText());
            int kq = preparedStatement.executeUpdate();
            new BounceIn(errorLabel).play();
            if (kq > 0) {
              errorLabel.setTextFill(Color.GREEN);
              errorLabel.setText("Update success " + licensePlateTextField.getText() + "!");
            } else {
              errorLabel.setTextFill(Color.RED);
              errorLabel.setText("Update error. Try again!");
            }
          } else {
            new BounceIn(errorLabel).play();
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Not found " + licensePlateTextField.getText() + "!");
          }
        }
      } catch (SQLException e) {
        Logger.getLogger(VehicleManagementController.class.getName()).log(Level.SEVERE, null, e);
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
          Logger.getLogger(VehicleManagementController.class.getName()).log(Level.SEVERE, null, e);
        }
      }
    }
  }

  public void carTypeChecked() {
    if (vehicleBicycles.isSelected() || vehicleTypeMotorbike.isSelected()) {
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

  public void limitLength() {
    licensePlateTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (licensePlateTextField.getText().length() > 10) {
          new BounceIn(errorLabel).play();
          new BounceIn(errorLabel1).play();
          errorLabel.setTextFill(Color.RED);
          errorLabel.setText("License Plate length must be <= 10!");
          errorLabel1.setTextFill(Color.RED);
          errorLabel1.setText("!");
          licensePlateTextField.setText(licensePlateTextField.getText().substring(0, 10));
        } else {
          errorLabel1.setText("");
          errorLabel.setText("");
        }
      }
    });
  }

  @FXML
  private TextField licensePlateTextField;
  @FXML
  private Label errorLabel1, errorLabel, carSeatsLabel, vehicleTypeLabel, monthlyTicketLabel;
  @FXML
  private RadioButton vehicleBicycles, vehicleTypeCar, vehicleTypeMotorbike, carSeats1, carSeats2, carSeats3, ticketYes, ticketNo;
  @FXML
  private MenuBar menuBar;

  public void goToIn() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToVehicleManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("VehicleManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToOut() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OutScene.fxml")));
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

  public void goToHelp() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HelpScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToAccountManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToAdminPackingHistory() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminCenterScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToTicketManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TicketManagementScene.fxml")));
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

  public void adminLogout() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToSlotsManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SlotsManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToStatistics() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StatisticsScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToPricesManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PriceManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
