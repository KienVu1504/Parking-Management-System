import animatefx.animation.BounceIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
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
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
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
  int numberOfItemPerPage = 25;
  private int numberOfPage = (historyRepository.getNumberOfPage()) / numberOfItemPerPage;
  private int pageNumber = 0;

  public HistoryController() throws SQLException {
  }

  public void pressBack() {
    pageNumber = pageNumber <= 0 ? pageNumber : pageNumber - 1;
    if (searchBox.getText().isEmpty()) {
      histories = historyRepository.getHistories(pageNumber, numberOfItemPerPage);
    } else {
      histories = historyRepository.getHistoriesFiltered(pageNumber, numberOfItemPerPage, searchBox.getText());
    }
    setTableValue();
    pageNumberLabel.setText(String.valueOf(pageNumber + 1));
  }

  public void pressNext() {
    pageNumber = (pageNumber <= numberOfPage - 1) ? pageNumber + 1 : pageNumber;
    if (searchBox.getText().isEmpty()) {
      histories = historyRepository.getHistories(pageNumber, numberOfItemPerPage);
    } else {
      histories = historyRepository.getHistoriesFiltered(pageNumber, numberOfItemPerPage, searchBox.getText());
    }
    setTableValue();
    pageNumberLabel.setText(String.valueOf(pageNumber + 1));
  }

  ObservableList<History> historyControllerObservableList = null;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    resetHistoryPage();
  }

  public void resetHistoryPage() {
    histories = historyRepository.getHistories(pageNumber, numberOfItemPerPage);
    setTableValue();
  }

  public void setTableValue() {
    backButton.setDisable(pageNumber <= 0);
    historyControllerObservableList = FXCollections.observableArrayList(histories);
    nextButton.setDisable(historyControllerObservableList.size() < 25);
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
  }

  @FXML
  private TextField searchBox;

  @FXML
  private Label pageNumberLabel, errorLabel, errorLabel1;

  public void limitLength() {
    if (searchBox.getLength() == 0 || searchBox.getText().equals("") || searchBox.getText().length() == 0 || searchBox.getText().isBlank() || searchBox.getText().isEmpty()) {
      resetHistoryPage();
    } else {
      searchBox.lengthProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.intValue() > oldValue.intValue()) {
          if (searchBox.getText().length() > 10) {
            new BounceIn(errorLabel).play();
            new BounceIn(errorLabel1).play();
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
      search();
    }
  }

  public void search() {
    historyControllerObservableList = null;
    histories = historyRepository.getHistoriesFiltered(pageNumber = 0, numberOfItemPerPage, searchBox.getText());
    pageNumberLabel.setText(String.valueOf(pageNumber + 1));
    setTableValue();
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
