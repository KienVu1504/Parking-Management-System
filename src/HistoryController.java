import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.History;
import repositories.HistoryRepository;

import java.io.IOException;
import java.net.URL;
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
  private TableView<History> historyTable;
  @FXML
  private TableColumn<History, Integer> IdColumn;
  @FXML
  private TableColumn<History, String> licensePlateColumn;
  @FXML
  private TableColumn<History, String> vehicleTypeColumn;
  @FXML
  private TableColumn<History, String> seatColumn;
  @FXML
  private TableColumn<History, Integer> monthlyTicketColumn;
  @FXML
  private TableColumn<History, String> timeInColumn;
  @FXML
  private TableColumn<History, String> timeOutColumn;
  @FXML
  private TableColumn<History, String> parkingTimeColumn;
  @FXML
  private TableColumn<History, String> parkingFeeColumn;
  @FXML
  private TableColumn<History, Integer> statusColumn;
  @FXML
  private Button backButton, nextButton;
  private List<History> histories = new ArrayList();
  private HistoryRepository historyRepository = new HistoryRepository();

  //internal state
  private int pageNumber = 0;

  public HistoryController() {
  }

  public void pressBack() {
    pageNumber = pageNumber <= 0 ? pageNumber : pageNumber - 1;
    pageNumberLabel.setText(String.valueOf(pageNumber + 1));
  }

  public void pressNext() {
    pageNumber++;
    pageNumberLabel.setText(String.valueOf(pageNumber + 1));
  }

  //select * from parking limit 25 offset 25 * 2
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    int numberOfItemPerPage = 25;
    histories = historyRepository.getHistories(1, numberOfItemPerPage);
    backButton.setDisable(pageNumber <= 0);
    ObservableList<History> historyControllerObservableList = FXCollections.observableArrayList(histories);
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
    //Initial filtered list
    FilteredList<History> filteredList = new FilteredList<>(historyControllerObservableList, b -> true);
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
    SortedList<History> sortedList = new SortedList<>(filteredList);
    // Bind sorted result with TableView
    sortedList.comparatorProperty().bind(historyTable.comparatorProperty());
    //Apply filtered and sorted data to the TableView
    historyTable.setItems(sortedList);
    //historyTable.setItems(historyControllerObservableList);
  }

  @FXML
  private TextField searchBox;

  @FXML
  private Label pageNumberLabel, errorLabel, errorLabel1;

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
