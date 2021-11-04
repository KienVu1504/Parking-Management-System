import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class HistoryController implements Initializable {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane HistoryPane;

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
      stage = (Stage) HistoryPane.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private TableView<HistoryController> historyTable;
  @FXML
  private TableColumn<HistoryController, Integer> IdColumn;
  @FXML
  private TableColumn<HistoryController, String> licensePlateColumn;
  @FXML
  private TableColumn<HistoryController, String> vehicleTypeColumn;
  @FXML
  private TableColumn<HistoryController, String> seatColumn;
  @FXML
  private TableColumn<HistoryController, Integer> monthlyTicketColumn;
  @FXML
  private TableColumn<HistoryController, String> timeInColumn;
  @FXML
  private TableColumn<HistoryController, String> timeOutColumn;
  @FXML
  private TableColumn<HistoryController, String> parkingTimeColumn;
  @FXML
  private TableColumn<HistoryController, String> parkingFeeColumn;
  @FXML
  private TableColumn<HistoryController, Integer> statusColumn;
  private List<HistoryController> historyControllerList = new ArrayList();
  private int id, ticket, status;
  private String license_plate, type, seat, time_in, time_out, parking_time, fee;

  public void setId(int id) {
    this.id = id;
  }

  public void setLicense_plate(String license_plate) {
    this.license_plate = license_plate;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setSeat(String seat) {
    this.seat = seat;
  }

  public void setTicket(int ticket) {
    this.ticket = ticket;
  }

  public void setTime_in(String time_in) {
    this.time_in = time_in;
  }

  public void setTime_out(String time_out) {
    this.time_out = time_out;
  }

  public void setParking_time(String parking_time) {
    this.parking_time = parking_time;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public String getLicense_plate() {
    return license_plate;
  }

  public String getType() {
    return type;
  }

  public String getSeat() {
    return seat;
  }

  public int getTicket() {
    return ticket;
  }

  public String getTime_in() {
    return time_in;
  }

  public String getTime_out() {
    return time_out;
  }

  public String getParking_time() {
    return parking_time;
  }

  public String getFee() {
    return fee;
  }

  public int getStatus() {
    return status;
  }

  public HistoryController() {
  }

  public HistoryController(int id, String license_plate, String type, String seat, int ticket, String time_in, String time_out, String parking_time, String fee, int status) {
    this.id = id;
    this.license_plate = license_plate;
    this.type = type;
    this.seat = seat;
    this.ticket = ticket;
    this.time_in = time_in;
    this.time_out = time_out;
    this.parking_time = parking_time;
    this.fee = fee;
    this.status = status;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Connection connection = null;
    ResultSet resultSet = null;
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsystem", "root", "");
      PreparedStatement preparedStatement = connection.prepareStatement("select * from parking");
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        setId(resultSet.getInt("id"));
        setLicense_plate(resultSet.getString("license_plate"));
        setType(resultSet.getString("type"));
        setSeat(resultSet.getString("seat"));
        setTicket(resultSet.getInt("ticket"));
        setTime_in(resultSet.getString("time_in"));
        setTime_out(resultSet.getString("time_out"));
        setParking_time(resultSet.getString("parking_time"));
        setFee(resultSet.getString("fee"));
        setStatus(resultSet.getInt("status"));
        historyControllerList.add(new HistoryController(getId(), getLicense_plate(), getType(), getSeat(), getTicket(), getTime_in(), getTime_out(), getParking_time(), getFee(), getStatus()));
      }
      ObservableList<HistoryController> historyControllerObservableList = FXCollections.observableArrayList(historyControllerList);
      IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
      licensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("license_plate"));
      vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
      seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));
      monthlyTicketColumn.setCellValueFactory(new PropertyValueFactory<>("ticket"));
      timeInColumn.setCellValueFactory(new PropertyValueFactory<>("time_in"));
      timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("time_out"));
      parkingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("parking_time"));
      parkingFeeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
      statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
      historyTable.setItems(historyControllerObservableList);

      //Initial filtered list
      FilteredList<HistoryController> filteredList = new FilteredList<>(historyControllerObservableList, b -> true);
      searchBox.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(historyController -> {
        //If no search value then display all records or whatever records it current have. No change :v
        if (newValue.isEmpty() || newValue.isBlank()) {
          return true;
        }
        String searchKeyWords = newValue.toLowerCase();
        if (historyController.getLicense_plate().toLowerCase().contains(searchKeyWords)) {
          return true; // Means we found a match in license_plate
        } else if (historyController.getType().toLowerCase().contains(searchKeyWords)) {
          return true;
        } else if (historyController.getSeat().toLowerCase().contains(searchKeyWords)) {
          return true;
        } else if (historyController.getTime_in().toLowerCase().contains(searchKeyWords)) {
          return true;
        } else if (historyController.getTime_out().toLowerCase().contains(searchKeyWords)) {
          return true;
        } else if (historyController.getParking_time().toLowerCase().contains(searchKeyWords)) {
          return true;
        } else return historyController.getFee().toLowerCase().contains(searchKeyWords);
      }));
      SortedList<HistoryController> sortedList = new SortedList<>(filteredList);
      // Bind sorted result with TableView
      sortedList.comparatorProperty().bind(historyTable.comparatorProperty());
      //Apply filtered and sorted data to the TableView
      historyTable.setItems(sortedList);
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    } finally {
      if (resultSet != null) {
        try {
          resultSet.close();
        } catch (SQLException sqlException) {
          sqlException.printStackTrace();
        }
      }
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlException) {
          sqlException.printStackTrace();
        }
      }
    }
  }

  @FXML
  private Pagination pagination;

  @FXML
  private TextField searchBox;

  @FXML
  private Label errorLabel, errorLabel1;

  public void limitLength() {
    searchBox.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (searchBox.getText().length() > 10) {
          errorLabel.setText("!");
          errorLabel1.setTextFill(Color.RED);
          errorLabel1.setText("License Plate length must be <= 10!");
          searchBox.setText(searchBox.getText().substring(0, 10));
        } else {
          errorLabel.setText("");
          errorLabel1.setTextFill(Color.BLACK);
          errorLabel1.setText("Parking History");
        }
      }
    });
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

  public void goToOut() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OutScene.fxml")));
    //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
