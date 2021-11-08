import javafx.event.ActionEvent;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import repositories.Database;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
  private Scene scene;
  private Parent root;
  private Stage stage;
  @FXML
  private TextField usernameTextField, passwordTextField;
  @FXML
  private Label errorLabel, usernameErrorLabel, passwordErrorLabel;
  @FXML
  private Button loginButton;
  @FXML
  private AnchorPane AdminPane;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    // Get the Stage.
    stage = (Stage) alert.getDialogPane().getScene().getWindow();
    // Add a custom icon.
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) AdminPane.getScene().getWindow();
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
    //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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

  public void usernameLimitLength() {
    usernameTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (usernameTextField.getText().length() > 25) {
          errorLabel.setText("Username length must be <= 25!");
          usernameErrorLabel.setText("!");
          // if its 11th character then just setText to previous one
          usernameTextField.setText(usernameTextField.getText().substring(0, 25));
        } else {
          errorLabel.setText("");
          usernameErrorLabel.setText("");
        }
      }
    });
  }

  public void passwordLimitLength() {
    passwordTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (passwordTextField.getText().length() > 50) {
          errorLabel.setText("Password length must be <= 50!");
          passwordErrorLabel.setText("!");
          // if its 11th character then just setText to previous
          // one
          passwordTextField.setText(passwordTextField.getText().substring(0, 50));
        } else {
          errorLabel.setText("");
          passwordErrorLabel.setText("");
        }
      }
    });
  }

  public void loginCheck(ActionEvent event, String username, String password) {
    if (usernameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty()) {
      usernameErrorLabel.setText("!");
      passwordErrorLabel.setText("!");
      errorLabel.setTextFill(javafx.scene.paint.Color.RED);
      errorLabel.setText("Please fill all field!");
    } else if (usernameTextField.getText().isEmpty()) {
      usernameErrorLabel.setText("!");
      passwordErrorLabel.setText("");
      errorLabel.setTextFill(javafx.scene.paint.Color.RED);
      errorLabel.setText("Enter your username!");
    } else if (passwordTextField.getText().isEmpty()) {
      passwordErrorLabel.setText("!");
      usernameErrorLabel.setText("");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Enter your password!");
    } else {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        connection = Database.getInstance().getConnection();
        preparedStatement = connection.prepareStatement("SELECT password FROM account WHERE username = ? AND role = 'ad'");
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) {
          errorLabel.setText("Wrong username!");
          usernameErrorLabel.setText("!");
          passwordErrorLabel.setText("");
        } else {
          while (resultSet.next()) {
            String retriedPassword = resultSet.getString("password");
            if (retriedPassword.equals(password)) {
              Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminCenterScene.fxml")));
              //Stage stage = (Stage) menuBar.getScene().getWindow();
              stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              Scene scene = new Scene(root);
              stage.setScene(scene);
              stage.show();
            } else {
              errorLabel.setText("Wrong password!");
              passwordErrorLabel.setText("!");
              usernameErrorLabel.setText("");
            }
          }
        }
      } catch (SQLException | IOException e) {
        e.printStackTrace();
      } finally {
        if (resultSet != null) {
          try {
            resultSet.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
        if (preparedStatement != null) {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
        if (connection != null) {
          try {
            connection.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loginButton.setOnAction(event -> loginCheck(event, usernameTextField.getText(), passwordTextField.getText()));
  }

  public void openHomeSite() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/KienVu1504/Paking-Management-System"));
  }

  public void openSupport() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.facebook.com/messages/t/100004800523531"));
  }
}
