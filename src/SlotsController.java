import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
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
  @FXML
  private TextField bicyclesField, motorbikeField, carField, seat1, seat2, seat3, upBicycles, upMotorbike, upSeat1, upSeat2, upSeat3;
  @FXML
  private Label error, currentBicycles, currentMotorbike, currentSeat1, currentSeat2, currentSeat3;
  @FXML
  private MenuBar menuBar;

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

  public void limitLength() {
    upBicycles.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upBicycles.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Number of slots must be <= 9999999999!");
          upBicycles.setText(upBicycles.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength1() {
    upMotorbike.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upMotorbike.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Number of slots must be <= 9999999999!");
          upMotorbike.setText(upMotorbike.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength2() {
    upSeat1.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upSeat1.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Number of slots must be <= 9999999999!");
          upSeat1.setText(upSeat1.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength3() {
    upSeat2.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upSeat2.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Number of slots must be <= 9999999999!");
          upSeat2.setText(upSeat2.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength4() {
    upSeat3.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upSeat3.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Number of slots must be <= 9999999999!");
          upSeat3.setText(upSeat3.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void getCurrentSlots() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    try {
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = 'bicycles'");
      preparedStatement1 = connection.prepareStatement("select slots from pricevsslots where type = 'motorbike'");
      preparedStatement2 = connection.prepareStatement("select slots from pricevsslots where type = '4t8car'");
      preparedStatement3 = connection.prepareStatement("select slots from pricevsslots where type = '9t29car'");
      preparedStatement4 = connection.prepareStatement("select slots from pricevsslots where type = '30pcar'");
      resultSet = preparedStatement.executeQuery();
      resultSet1 = preparedStatement1.executeQuery();
      resultSet2 = preparedStatement2.executeQuery();
      resultSet3 = preparedStatement3.executeQuery();
      resultSet4 = preparedStatement4.executeQuery();
      if (resultSet.next() && resultSet1.next() && resultSet2.next() && resultSet3.next() && resultSet4.next()) {
        currentBicycles.setText("/ " + resultSet.getInt("slots"));
        upBicycles.setPromptText(String.valueOf(resultSet.getInt("slots")));
        currentMotorbike.setText("/ " + resultSet1.getInt("slots"));
        upMotorbike.setPromptText(String.valueOf(resultSet1.getInt("slots")));
        currentSeat1.setText("/ " + resultSet2.getInt("slots"));
        upSeat1.setPromptText(String.valueOf(resultSet2.getInt("slots")));
        currentSeat2.setText("/ " + resultSet3.getInt("slots"));
        upSeat2.setPromptText(String.valueOf(resultSet3.getInt("slots")));
        currentSeat3.setText("/ " + resultSet4.getInt("slots"));
        upSeat3.setPromptText(String.valueOf(resultSet4.getInt("slots")));
      } else {
        currentBicycles.setText("Data error!");
        currentMotorbike.setText("Data error!");
        currentSeat1.setText("Data error!");
        currentSeat2.setText("Data error!");
        currentSeat3.setText("Data error!");
      }
    } catch (SQLException e) {
      Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      error.setTextFill(Color.RED);
      error.setText("Connection error, please try again later!");
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        } else if (resultSet1 != null) {
          resultSet1.close();
        } else if (resultSet2 != null) {
          resultSet2.close();
        } else if (resultSet3 != null) {
          resultSet3.close();
        } else if (resultSet4 != null) {
          resultSet4.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        } else if (preparedStatement1 != null) {
          preparedStatement1.close();
        } else if (preparedStatement2 != null) {
          preparedStatement2.close();
        } else if (preparedStatement3 != null) {
          preparedStatement3.close();
        } else if (preparedStatement4 != null) {
          preparedStatement4.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
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

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      getData();
      getCurrentSlots();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void updateBicyclesCheck() {
    if (upBicycles.getText().isEmpty()) {
      error.setTextFill(Color.RED);
      error.setText("Nothings to update!");
    } else {

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
}
