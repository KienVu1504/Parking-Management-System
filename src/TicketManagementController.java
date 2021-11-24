import animatefx.animation.BounceIn;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) ticketManagerPane.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private RadioButton up1Month, up1Year, up6Months, add1Month, add1Year, add6Months, upActive, upSuspended;
  @FXML
  private TextField addLicensePlateTextField, upLicensePlateTextField, upExpiredDate;
  @FXML
  private Label error1, error2, error3, error, expiredDateLabel, statusLabel, ticketLabel;
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
        if (addLicensePlateTextField.getText().length() > 10) {
          new BounceIn(error).play();
          new BounceIn(error1).play();
          error1.setTextFill(Color.RED);
          error.setTextFill(Color.RED);
          error.setText("License Plate length must be <= 10!");
          error1.setText("!");
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
      new BounceIn(error).play();
      new BounceIn(error1).play();
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
          new BounceIn(error).play();
          new BounceIn(error1).play();
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
          new BounceIn(error).play();
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

  public void searchLicensePlate() {
    error3.setText("");
    if (upLicensePlateTextField.getText().isEmpty()) {
      new BounceIn(error2).play();
      new BounceIn(error3).play();
      error2.setTextFill(Color.RED);
      error3.setTextFill(Color.RED);
      error2.setText("!");
      error3.setText("Please enter license plate!");
    } else {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        connection = Database.getInstance().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM ticket LIMIT 0,1");
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          preparedStatement = connection.prepareStatement("select * from ticket where license_plate = ?");
          preparedStatement.setString(1, upLicensePlateTextField.getText());
          resultSet = preparedStatement.executeQuery();
          if (!resultSet.next()) {
            new BounceIn(error2).play();
            new BounceIn(error3).play();
            error2.setTextFill(Color.RED);
            error3.setTextFill(Color.RED);
            error2.setText("!");
            error3.setText("Can't find " + upLicensePlateTextField.getText());
          } else {
            error2.setText("");
            preparedStatement = connection.prepareStatement("select * from ticket where license_plate = ?");
            preparedStatement.setString(1, upLicensePlateTextField.getText());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
              upExpiredDate.setText(resultSet.getString("expired_date"));
              if (resultSet.getInt("status") == 1) {
                upActive.setSelected(true);
              } else if (resultSet.getInt("status") == 0) {
                upSuspended.setSelected(true);
              }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
              LocalDateTime now = LocalDateTime.now();
              Date d1 = sdf.parse(upExpiredDate.getText());
              Date d2 = sdf.parse(dtf.format(now));
              long difference_In_Time = d2.getTime() - d1.getTime();
              if (difference_In_Time < 0) {
                expiredDateLabel.setTextFill(Color.BLACK);
              } else {
                expiredDateLabel.setTextFill(Color.RED);
              }
            } catch (ParseException e) {
              e.printStackTrace();
            }
            expiredDateLabel.setDisable(false);
            upExpiredDate.setDisable(false);
            statusLabel.setDisable(false);
            upActive.setDisable(false);
            upSuspended.setDisable(false);
            ticketLabel.setDisable(false);
            up1Month.setDisable(false);
            up6Months.setDisable(false);
            up1Year.setDisable(false);
          }
        } else {
          new BounceIn(error3).play();
          error3.setTextFill(Color.RED);
          error3.setText("List is empty!");
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

  public void updateCheck() {
    if (upLicensePlateTextField.getText().isEmpty()) {
      new BounceIn(error2).play();
      new BounceIn(error3).play();
      error2.setTextFill(Color.RED);
      error3.setTextFill(Color.RED);
      error2.setText("!");
      error3.setText("Please enter license plate!");
    } else if (!upLicensePlateTextField.getText().isEmpty() && statusLabel.isDisabled()) {
      new BounceIn(error2).play();
      new BounceIn(error3).play();
      error2.setTextFill(Color.RED);
      error3.setTextFill(Color.RED);
      error2.setText("!");
      error3.setText("Please click search button first!");
    } else if (!upLicensePlateTextField.getText().isEmpty() && !statusLabel.isDisabled()) {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        connection = Database.getInstance().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM ticket LIMIT 0,1");
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
          new BounceIn(error3).play();
          error3.setTextFill(Color.RED);
          error3.setText("List is empty!");
        } else {
          preparedStatement = connection.prepareStatement("select * from ticket where license_plate = ?");
          preparedStatement.setString(1, upLicensePlateTextField.getText());
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            preparedStatement = connection.prepareStatement("update ticket set expired_date=?, status=? where license_plate = ?");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime exd = LocalDateTime.parse(upExpiredDate.getText(), dtf);
            if (!up1Month.isSelected() && !up6Months.isSelected() && !up1Year.isSelected()) {
              preparedStatement.setString(1, upExpiredDate.getText());
            } else {
              if (up1Month.isSelected()) {
                exd = exd.plusMonths(1);
              } else if (up6Months.isSelected()) {
                exd = exd.plusMonths(6);
              } else if (up1Year.isSelected()) {
                exd = exd.plusYears(1);
              }
              preparedStatement.setString(1, dtf.format(exd));
            }
            if (upActive.isSelected()) {
              preparedStatement.setInt(2, 1);
            } else {
              preparedStatement.setInt(2, 0);
            }
            preparedStatement.setString(3, upLicensePlateTextField.getText());
            int kq = preparedStatement.executeUpdate();
            new BounceIn(error3).play();
            if (kq > 0) {
              error3.setTextFill(Color.GREEN);
              error3.setText("Update success!");
              expiredDateLabel.setDisable(true);
              upExpiredDate.setText("");
              upExpiredDate.setDisable(true);
              statusLabel.setDisable(true);
              upActive.setSelected(false);
              upActive.setDisable(true);
              upSuspended.setSelected(false);
              upSuspended.setDisable(true);
              ticketLabel.setDisable(true);
              up1Month.setDisable(true);
              up1Month.setSelected(false);
              up6Months.setSelected(false);
              up6Months.setDisable(true);
              up1Year.setDisable(true);
              up1Year.setSelected(false);
              expiredDateLabel.setTextFill(Color.BLACK);
              resetTable();
            } else {
              error3.setTextFill(Color.RED);
              error3.setText("Update error. Try again!");
            }
          } else {
            new BounceIn(error3).play();
            new BounceIn(error2).play();
            error2.setTextFill(Color.RED);
            error2.setText("!");
            error3.setTextFill(Color.RED);
            error3.setText("Update error. Try again!");
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

  public void deleteCheck() {
    if (upLicensePlateTextField.getText().isEmpty()) {
      new BounceIn(error3).play();
      new BounceIn(error2).play();
      error2.setTextFill(Color.RED);
      error3.setTextFill(Color.RED);
      error2.setText("!");
      error3.setText("Please enter license plate!");
    } else if (!upLicensePlateTextField.getText().isEmpty() && statusLabel.isDisabled()) {
      new BounceIn(error3).play();
      new BounceIn(error2).play();
      error2.setTextFill(Color.RED);
      error3.setTextFill(Color.RED);
      error2.setText("!");
      error3.setText("Please click search button first!");
    } else if (!upLicensePlateTextField.getText().isEmpty() && !statusLabel.isDisabled()) {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        connection = Database.getInstance().getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM ticket LIMIT 0,1");
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
          new BounceIn(error3).play();
          error3.setTextFill(Color.RED);
          error3.setText("List is empty!");
        } else {
          preparedStatement = connection.prepareStatement("select * from ticket where license_plate = ?");
          preparedStatement.setString(1, upLicensePlateTextField.getText());
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            preparedStatement = connection.prepareStatement("delete from ticket where license_plate = ?");
            preparedStatement.setString(1, upLicensePlateTextField.getText());
            int kq = preparedStatement.executeUpdate();
            new BounceIn(error3).play();
            if (kq > 0) {
              error3.setTextFill(Color.GREEN);
              error3.setText("Deleted!");
              expiredDateLabel.setDisable(true);
              upExpiredDate.setText("");
              upExpiredDate.setDisable(true);
              statusLabel.setDisable(true);
              upActive.setSelected(false);
              upActive.setDisable(true);
              upSuspended.setSelected(false);
              upSuspended.setDisable(true);
              ticketLabel.setDisable(true);
              up1Month.setDisable(true);
              up1Month.setSelected(false);
              up6Months.setSelected(false);
              up6Months.setDisable(true);
              up1Year.setDisable(true);
              up1Year.setSelected(false);
              expiredDateLabel.setTextFill(Color.BLACK);
              resetTable();
            } else {
              error3.setTextFill(Color.RED);
              error3.setText("Can't delete " + upLicensePlateTextField.getText() + ". Try again!");
            }
          } else {
            new BounceIn(error3).play();
            error3.setTextFill(Color.RED);
            error3.setText("Not found " + upLicensePlateTextField.getText() + "!");
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

  public void upLicenseLimitLength() {
    upLicensePlateTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        expiredDateLabel.setDisable(true);
        upExpiredDate.setText("");
        upExpiredDate.setDisable(true);
        statusLabel.setDisable(true);
        upActive.setSelected(false);
        upActive.setDisable(true);
        upSuspended.setSelected(false);
        upSuspended.setDisable(true);
        ticketLabel.setDisable(true);
        up1Month.setDisable(true);
        up1Month.setSelected(false);
        up6Months.setSelected(false);
        up6Months.setDisable(true);
        up1Year.setDisable(true);
        up1Year.setSelected(false);
        expiredDateLabel.setTextFill(Color.BLACK);
        if (upLicensePlateTextField.getText().length() > 10) {
          new BounceIn(error3).play();
          new BounceIn(error2).play();
          error3.setTextFill(Color.RED);
          error2.setTextFill(Color.RED);
          error3.setText("License Plate length must be <= 10!");
          error2.setText("!");
          upLicensePlateTextField.setText(upLicensePlateTextField.getText().substring(0, 10));
        } else {
          error3.setText("");
          error2.setText("");
        }
      }
    });
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
