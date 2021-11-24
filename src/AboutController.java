import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class AboutController {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane AboutPane;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) AboutPane.getScene().getWindow();
      stage.close();
    }
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

  public void goToHistory() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HistoryScene.fxml")));
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

  public void logout() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void openHomeSite() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/KienVu1504/Paking-Management-System"));
  }

  public void openSupport() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.facebook.com/messages/t/100004800523531"));
  }
}
