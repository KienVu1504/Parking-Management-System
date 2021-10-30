import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
  private Scene scene;
  private Parent root;
  private Stage stage;
  @FXML
  private Label errorLabel;
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
  public void loginCheck(ActionEvent event, String username, String password){
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsystem", "root", "");
      preparedStatement = connection.prepareStatement("SELECT password FROM account WHERE username = ?");
      preparedStatement.setString(1, username);
      resultSet = preparedStatement.executeQuery();
      if (!resultSet.isBeforeFirst()){
        errorLabel.setText("Wrong username!");
      } else {
        while (resultSet.next()){
          String retriedPassword = resultSet.getString("password");
          if (retriedPassword.equals(password)){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InScene.fxml")));
            //Stage stage = (Stage) menuBar.getScene().getWindow();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
          } else {
            errorLabel.setText("Wrong password!");
          }
        }
      }
    } catch (SQLException | IOException e){
      e.printStackTrace();
    } finally {
      if (resultSet != null){
        try {
          resultSet.close();
        } catch (SQLException e){
          e.printStackTrace();
        }
      }
      if (preparedStatement != null){
        try {
          preparedStatement.close();
        } catch (SQLException e){
          e.printStackTrace();
        }
      }
      if (connection != null){
        try {
          connection.close();
        } catch (SQLException e){
          e.printStackTrace();
        }
      }
    }
  }
  public void openHomeSite() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/KienVu1504/Paking-Management-System"));
  }
  public void openSupport() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.facebook.com/messages/t/100004800523531"));
  }
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        loginCheck(event, usernameTextField.getText(), passwordTextField.getText());
      }
    });
  }
}
