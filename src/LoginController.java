import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LoginController {
  private Scene scene;
  private Parent root;
  private Stage stage;
  @FXML
  private TextField usernameTextField, passwordTextField;
  @FXML
  private Button loginButton;
  @FXML
  private AnchorPane LoginPane;
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
      stage = (Stage) LoginPane.getScene().getWindow();
      stage.close();
    }
  }
  public void loginCheck(){

  }
  public void openHomeSite() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/KienVu1504/Paking-Management-System"));
  }
  public void openSupport() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.facebook.com/messages/t/100004800523531"));
  }
}
