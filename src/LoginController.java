import animatefx.animation.*;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class LoginController implements Initializable {
  private Stage stage;
  @FXML
  private Label errorLabel, usernameErrorLabel, passwordErrorLabel;
  @FXML
  private TextField usernameTextField, passwordTextField;
  @FXML
  private Button loginButton;
  @FXML
  private AnchorPane LoginPane;
  @FXML
  private ImageView image;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) LoginPane.getScene().getWindow();
      stage.close();
    }
  }

  public void usernameLimitLength() {
    usernameTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (usernameTextField.getText().length() > 25) {
          errorLabel.setText("Username length must be <= 25!");
          new BounceIn(errorLabel).play();
          new BounceIn(usernameErrorLabel).play();
          usernameErrorLabel.setText("!");
          // if its 11th character then just setText to previous one
          usernameTextField.setText(usernameTextField.getText().substring(0, 25));
        } else {
          errorLabel.setText("");
          usernameErrorLabel.setText("");
          new BounceOut(errorLabel).play();
          new BounceOut(usernameErrorLabel).play();
        }
      }
    });
  }

  public void passwordLimitLength() {
    passwordTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (passwordTextField.getText().length() > 50) {
          errorLabel.setText("Password length must be <= 50!");
          passwordErrorLabel.setText("!");
          new BounceIn(errorLabel).play();
          new BounceIn(passwordErrorLabel).play();
          passwordTextField.setText(passwordTextField.getText().substring(0, 50));
        } else {
          errorLabel.setText("");
          passwordErrorLabel.setText("");
          new BounceOut(errorLabel).play();
          new BounceOut(passwordErrorLabel).play();
        }
      }
    });
  }

  public void errorAnimated() {
    new Shake(image).play();
    new BounceIn(errorLabel).play();
  }

  public void loginCheck(ActionEvent event, String username, String password) {
    if (usernameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty()) {
      usernameErrorLabel.setText("!");
      passwordErrorLabel.setText("!");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Please fill all field!");
      errorAnimated();
      new BounceIn(usernameErrorLabel).play();
      new BounceIn(passwordErrorLabel).play();
    } else if (usernameTextField.getText().isEmpty()) {
      usernameErrorLabel.setText("!");
      passwordErrorLabel.setText("");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Enter your username!");
      errorAnimated();
      new BounceIn(usernameErrorLabel).play();
      new BounceIn(passwordErrorLabel).play();
    } else if (passwordTextField.getText().isEmpty()) {
      passwordErrorLabel.setText("!");
      usernameErrorLabel.setText("");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Enter your password!");
      errorAnimated();
      new BounceIn(usernameErrorLabel).play();
      new BounceIn(passwordErrorLabel).play();
    } else {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try {
        connection = Database.getInstance().getConnection();
        preparedStatement = connection.prepareStatement("SELECT password FROM account WHERE username = ?");
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) {
          errorLabel.setText("Wrong username!");
          usernameErrorLabel.setText("!");
          passwordErrorLabel.setText("");
          errorAnimated();
          new BounceIn(usernameErrorLabel).play();
          new BounceOut(passwordErrorLabel).play();
        } else {
          while (resultSet.next()) {
            String retriedPassword = resultSet.getString("password");
            if (retriedPassword.equals(password)) {
              Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InScene.fxml")));
              stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              Scene scene = new Scene(root);
              stage.setScene(scene);
              new FadeOut(image).play();
              new BounceOut(errorLabel).play();
              stage.show();
            } else {
              errorAnimated();
              new BounceIn(passwordErrorLabel).play();
              new BounceOut(usernameErrorLabel).play();
              errorLabel.setText("Wrong password!");
              passwordErrorLabel.setText("!");
              usernameErrorLabel.setText("");
            }
          }
        }
      } catch (SQLException | IOException e) {
        e.printStackTrace();
      } catch (NullPointerException nullPointerException) {
        new BounceIn(errorLabel).play();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Connection error, please try again later!");
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

  public void openHomeSite() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/KienVu1504/Paking-Management-System"));
  }

  public void openSupport() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.facebook.com/messages/t/100004800523531"));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loginButton.setOnAction(event -> loginCheck(event, usernameTextField.getText(), passwordTextField.getText()));
  }
}
