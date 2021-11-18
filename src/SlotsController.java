import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import repositories.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlotsController implements Initializable {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane slotsPane;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    // Get the Stage.
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    // Add a custom icon.
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) slotsPane.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private TextField bicyclesField, motorbikeField, carField, seat1, seat2, seat3, slotsNumber, newSlotsNumber;
  @FXML
  private Label error, error1;
  @FXML
  private MenuBar menuBar;

  public void limitLength() {
    newSlotsNumber.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (newSlotsNumber.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Number of slots must be <= 9999999999!");
          error1.setTextFill(Color.RED);
          error1.setText("!");
          newSlotsNumber.setText(newSlotsNumber.getText().substring(0, 10));
        } else {
          error1.setText("");
          error.setText("");
        }
      }
    });
  }

  public void updateCheck() throws SQLException {
    if (newSlotsNumber.getText().isEmpty()) {
      error1.setTextFill(Color.RED);
      error1.setText("!");
      error.setTextFill(Color.RED);
      error.setText("Please enter new number of slots!");
    } else {
      try {
        if (Integer.parseInt(newSlotsNumber.getText()) <= getTotalParking()) {
          error.setTextFill(Color.RED);
          error.setText("Can't set new number of slots to this value!");
          error1.setTextFill(Color.RED);
          error1.setText("!");
        } else {
          error1.setText("");
          Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;
          try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = 'slotsleft'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
              preparedStatement = connection.prepareStatement("update pricevsslots set slots=? where type = 'slotsleft'");
              preparedStatement.setString(1, newSlotsNumber.getText());
              int kq = preparedStatement.executeUpdate();
              if (kq > 0) {
                error.setTextFill(Color.GREEN);
                error.setText("Update successfully!");
                error1.setText("");
                newSlotsNumber.setText("");
                getCurrentSlots();
                getData();
              } else {
                error.setTextFill(Color.RED);
                error.setText("Can't update number of slots at this time!");
              }
            } else {
              error.setTextFill(Color.RED);
              error.setText("Can't update number of slots at this time!");
            }
          } catch (SQLException e) {
            Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
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
              Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
            }
          }
        }
      } catch (NumberFormatException numberFormatException) {
        error1.setTextFill(Color.RED);
        error1.setText("!");
        error.setTextFill(Color.RED);
        error.setText("Please enter a number!");
      }
    }
  }

  public int getTotalParking() throws SQLException {
    Connection connection = Database.getInstance().getConnection();
    //Creating the Statement object
    Statement stmt = connection.createStatement();
    //Query to get the number of rows in a table
    String query = "select count(*) from parking where status = 1";
    //Executing the query
    ResultSet rs = stmt.executeQuery(query);
    //Retrieving the result
    rs.next();
    return rs.getInt(1);
  }

  public void getData() throws SQLException {
    Connection connection = Database.getInstance().getConnection();
    Statement stmt = connection.createStatement();
    String query = "select count(*) from parking where status = 1 and type = 'Bicycles'";
    ResultSet rs = stmt.executeQuery(query);
    rs.next();
    bicyclesField.setText(String.valueOf(rs.getInt(1)));
    String query1 = "select count(*) from parking where status = 1 and type = 'Motorbike'";
    ResultSet rs1 = stmt.executeQuery(query1);
    rs1.next();
    motorbikeField.setText(String.valueOf(rs1.getInt(1)));
    String query2 = "select count(*) from parking where status = 1 and type = 'Car'";
    ResultSet rs2 = stmt.executeQuery(query2);
    rs2.next();
    carField.setText(String.valueOf(rs2.getInt(1)));
    String query3 = "select count(*) from parking where status = 1 and type = 'Car' and seat = '4-8'";
    ResultSet rs3 = stmt.executeQuery(query3);
    rs3.next();
    seat1.setText(String.valueOf(rs3.getInt(1)));
    String query4 = "select count(*) from parking where status = 1 and type = 'Car' and seat = '9-29'";
    ResultSet rs4 = stmt.executeQuery(query4);
    rs4.next();
    seat2.setText(String.valueOf(rs4.getInt(1)));
    String query5 = "select count(*) from parking where status = 1 and type = 'Car' and seat = '30+'";
    ResultSet rs5 = stmt.executeQuery(query5);
    rs5.next();
    seat3.setText(String.valueOf(rs5.getInt(1)));
  }

  public void getCurrentSlots() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = 'slotsleft'");
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        slotsNumber.setText(String.valueOf(resultSet.getInt("slots")));
      }
    } catch (SQLException e) {
      Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
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
        Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  public void goToAccountManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToIn() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToOut() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OutScene.fxml")));
    //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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

  public void goToHistory() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HistoryScene.fxml")));
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

  public void adminLogout() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminScene.fxml")));
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

  public void goToTicketManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TicketManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      getData();
      getCurrentSlots();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
