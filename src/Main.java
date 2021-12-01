import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScene.fxml")));
    Scene scene = new Scene(root, 1360, 768);
    Image icon = new Image("images/pngtree-parking-icon-for-your-design-websites-and-projects-png-image_5149413.png");
    stage.setTitle("Parking Management System");
    stage.setResizable(false);
    stage.setFullScreen(false);
    stage.getIcons().add(icon);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> {
      event.consume();
      closeAPP(stage);
    });
  }

  public void closeAPP(Stage stage) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
    stage1.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage.close();
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
