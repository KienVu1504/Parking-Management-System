import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TicketManagementController {
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
  private TableView<?> ticketTable;
  @FXML
  private TableColumn<?, ?> expiredDateColumn;
  @FXML
  private TableColumn<?, ?> licensePlateColumn;
  @FXML
  private TableColumn<?, ?> statusColumn;

}
