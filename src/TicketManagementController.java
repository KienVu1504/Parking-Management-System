import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Ticket;
import repositories.Database;
import repositories.TicketRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketManagementController implements Initializable {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane ticketManagerPane;

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
      stage = (Stage) ticketManagerPane.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private TextField addUserTextField;
  @FXML
  private RadioButton up1Month, up1Year, up6Months, add1Month, add1Year, add6Months;
  @FXML
  private TextField addLicensePlateTextField, upLicensePlateTextField, upStatusTextField;
  @FXML
  private Label error1, error2, error3, error;
  @FXML
  private MenuBar menuBar;
  @FXML
  private TableView<Ticket> ticketTable;
  @FXML
  private TableColumn<Ticket, String> expiredDateColumn;
  @FXML
  private TableColumn<Ticket, String> licensePlateColumn;
  @FXML
  private TableColumn<Ticket, String> statusColumn;

  private List<Ticket> tickets = new ArrayList();
  private TicketRepository ticketRepository = new TicketRepository();

  public TicketManagementController() {
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    resetTable();
  }

  public void resetTable() {
    tickets = ticketRepository.getTicket();
    ObservableList<Ticket> ticketObservableList = FXCollections.observableArrayList(tickets);
    licensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("license_plate"));
    expiredDateColumn.setCellValueFactory(new PropertyValueFactory<>("expired_date"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    ticketTable.setItems(ticketObservableList);
  }

  public void addLicenseLimitLength() {
    addLicensePlateTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (addLicensePlateTextField.getText().length() > 10) {
          error1.setTextFill(Color.RED);
          error.setTextFill(Color.RED);
          error.setText("License Plate length must be <= 10!");
          error1.setText("!");
          // if its 11th character then just setText to previous one
          addLicensePlateTextField.setText(addLicensePlateTextField.getText().substring(0, 10));
        } else {
          error.setText("");
          error1.setText("");
        }
      }
    });
  }

  public void addCheck() {
    if (addLicensePlateTextField.getText().isEmpty()) {
      error1.setTextFill(Color.RED);
      error.setTextFill(Color.RED);
      error.setText("Please enter license plate!");
      error1.setText("!");
    } else {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        connection = Database.getInstance().getConnection();
        preparedStatement = connection.prepareStatement("select * from ticket where license_plate = ?");
        preparedStatement.setString(1, addLicensePlateTextField.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          error1.setTextFill(Color.RED);
          error.setTextFill(Color.RED);
          error.setText("Ticket existing!");
          error1.setText("!");
        } else {
          preparedStatement = connection.prepareStatement("insert into ticket(license_plate, expired_date) values(?, ?)");
          preparedStatement.setString(1, addLicensePlateTextField.getText());
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
          LocalDateTime now = LocalDateTime.now();
          if (add1Month.isSelected()) {
            now = now.plusMonths(1);
          } else if (add6Months.isSelected()) {
            now = now.plusMonths(6);
          } else if (add1Year.isSelected()) {
            now = now.plusYears(1);
          }
          preparedStatement.setString(2, dtf.format(now));
          int kq = preparedStatement.executeUpdate();
          error1.setText("");
          if (kq > 0) {
            error.setTextFill(Color.GREEN);
            error.setText("Added " + addLicensePlateTextField.getText() + "!");
            resetTable();
          } else {
            error.setTextFill(Color.RED);
            error.setText("We can't add your ticket at this time. Please try again!");
          }
        }
      } catch (SQLException e) {
        Logger.getLogger(TicketManagementController.class.getName()).log(Level.SEVERE, null, e);
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
          Logger.getLogger(TicketManagementController.class.getName()).log(Level.SEVERE, null, e);
        }
      }
    }
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

  public void goToAccountManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
