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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsController implements Initializable {
  private int sum = 0, bicycles = 0, motorbike = 0, seat1Sum = 0, seat2Sum = 0, seat3Sum = 0;
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy"), dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  LocalDateTime now = LocalDateTime.now(), week = now.minusWeeks(1), month = now.minusMonths(1), year = now.minusYears(1);
  DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane statisticsPane;
  @FXML
  private Label parkingFeeLabel, bicyclesLabel, motorbikeLabel, seat1, seat2, seat3, error;
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
      stage = (Stage) statisticsPane.getScene().getWindow();
      stage.close();
    }
  }

  public void getWeeklyData() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      error.setTextFill(Color.BLACK);
      error.setText("Statistics from " + dtf.format(week) + " to " + dtf.format(now));
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM parking LIMIT 0,1");
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        preparedStatement = connection.prepareStatement("select fee from parking where status = '0' and (cast(time_out as datetime) >= ?) and (cast(time_out as datetime) <= ?)");
        preparedStatement.setString(1, dtf2.format(week));
        preparedStatement.setString(2, dtf2.format(now));
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          sum += resultSet.getInt("fee");
        }
        parkingFeeLabel.setText(decimalFormat.format(sum) + " VND");
      } else {
        parkingFeeLabel.setText("0 VND");
        bicyclesLabel.setText("0");
        motorbikeLabel.setText("0");
        seat1.setText("0");
        seat2.setText("0");
        seat3.setText("0");
      }
    } catch (SQLException e) {
      Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      error.setTextFill(Color.RED);
      error.setText("Connection error, please try again!");
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
        Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    getWeeklyData();
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
