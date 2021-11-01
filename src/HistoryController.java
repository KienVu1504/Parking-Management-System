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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
  private Scene scene;
  private Parent root;
  private Stage stage;
  @FXML
  private AnchorPane HistoryPane;
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
      stage = (Stage) HistoryPane.getScene().getWindow();
      stage.close();
    }
  }
  @FXML
  private Button searchButton;
  @FXML
  private TableView<HistoryController> historyTable;

  @FXML
  private TableColumn<HistoryController, Integer> monthlyTicketColumn;

  @FXML
  private TableColumn<HistoryController, String> seatColumn;

  @FXML
  private TableColumn<HistoryController, String> parkingTimeColumn;

  @FXML
  private TableColumn<HistoryController, Integer> numbersColumn;

  @FXML
  private TableColumn<HistoryController, String> parkingFeeColumn;
  @FXML
  private TableColumn<HistoryController, Integer> statusColumn;

  @FXML
  private TableColumn<HistoryController, String> timeInColumn;

  @FXML
  private TableColumn<HistoryController, String> timeOutColumn;

  @FXML
  private TableColumn<HistoryController, String> vehicleTypeColumn;

  @FXML
  private TableColumn<HistoryController, String> licensePlateColumn;

  private ObservableList<HistoryController> historyControllers;

  private List<HistoryController> historyControllerList = new ArrayList();

  private int id, ticket, status;
  private String license_plate, type, seat, time_in, time_out, parking_time, fee;
  public void setId(Integer id) {
    this.id = id;
  }

  public void setTicket(Integer ticket) {
    this.ticket = ticket;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setLicense(String license_plate) {
    this.license_plate = license_plate;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setSeat(String seat) {
    this.seat = seat;
  }

  public void setTimeIn(String time_in) {
    this.time_in = time_in;
  }

  public void setTimeOut(String time_out) {
    this.time_out = time_out;
  }

  public void setParkingTime(String parking_time) {
    this.parking_time = parking_time;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }
  public Integer getId(){
    return id;
  }
  public Integer getTicket(){
    return ticket;
  }
  public Integer getStatus(){
    return status;
  }
  public String getLicense(){
    return license_plate;
  }
  public String getType(){
    return type;
  }
  public String getSeat(){
    return seat;
  }
  public String getTimeIn(){
    return time_in;
  }
  public String getTimeOut(){
    return time_out;
  }
  public String getParkingTime(){
    return parking_time;
  }
  public String getFee(){
    return fee;
  }
  public HistoryController(){

  }

  public HistoryController(Integer id, String license_plate, String type, String seat, Integer ticket, String time_in, String time_out, String parking_time, String fee, Integer status) {
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

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Connection con = null;
    ResultSet rs = null;
    try {
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsystem", "root", "");
      PreparedStatement stm = con.prepareStatement("select * from parking");
      rs = stm.executeQuery();
      while (rs.next()){
        setId(rs.getInt("id"));
        setLicense(rs.getString("license_plate"));
        setType(rs.getString("type"));
        setSeat(rs.getString("seat"));
        setTicket(rs.getInt("ticket"));
        setTimeIn(rs.getString("time_in"));
        setTimeOut(rs.getString("time_out"));
        setParkingTime(rs.getString("parking_time"));
        setFee(rs.getString("fee"));
        setStatus(rs.getInt("status"));
        historyControllerList.add(new HistoryController(getId(), getLicense(), getType(), getSeat(), getTicket(), getTimeIn(), getTimeOut(), getFee(), getParkingTime(), getStatus()));
      }
      historyControllers = FXCollections.observableArrayList(historyControllerList);
      numbersColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
      licensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("license_plate"));
      vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
      seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));
      monthlyTicketColumn.setCellValueFactory(new PropertyValueFactory<>("ticket"));
      timeInColumn.setCellValueFactory(new PropertyValueFactory<>("time_in"));
      timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("time_out"));
      parkingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("parking_time"));
      parkingFeeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
      statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
      historyTable.setItems(historyControllers);
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException sqlException) {
          sqlException.printStackTrace();
        }
      }
      if (con != null) {
        try {
          con.close();
        } catch (SQLException sqlException) {
          sqlException.printStackTrace();
        }
      }
    }
  }
}
