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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Account;
import repositories.AccountRepository;
import repositories.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountManagementController implements Initializable {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane accountManagementPane;

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
      stage = (Stage) accountManagementPane.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private TextField addUserTextField;
  @FXML
  private RadioButton addAdmin, addEmployee, upAdmin, upEmployee;
  @FXML
  private PasswordField addPassTextField, upPassTextField;
  @FXML
  private TextField upUserTextField;
  @FXML
  private Label error1, error2, error3, error4, error5, error6, error7, error8, error9;
  @FXML
  private MenuBar menuBar;
  @FXML
  private TableView<Account> accountTable;
  @FXML
  private TableColumn<Account, String> usernameColumn;
  @FXML
  private TableColumn<Account, String> passwordColumn;
  @FXML
  private TableColumn<Account, String> roleColumn;
  private List<Account> accounts = new ArrayList();
  private AccountRepository accountRepository = new AccountRepository();

  public AccountManagementController() {
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    resetTable();
  }

  public void resetTable() {
    accounts = accountRepository.getAccount();
    ObservableList<Account> accountObservableList = FXCollections.observableArrayList(accounts);
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    accountTable.setItems(accountObservableList);
  }

  public void usernameLimitLength() {
    addUserTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (addUserTextField.getText().length() > 25) {
          error1.setTextFill(Color.RED);
          error2.setTextFill(Color.RED);
          error2.setText("Username length must be <= 25!");
          error1.setText("!");
          // if its 11th character then just setText to previous one
          addUserTextField.setText(addUserTextField.getText().substring(0, 25));
        } else {
          error1.setText("");
          error2.setText("");
        }
      }
    });
  }

  public void passwordLimitLength() {
    addPassTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (addPassTextField.getText().length() > 50) {
          error4.setTextFill(Color.RED);
          error3.setTextFill(Color.RED);
          error4.setText("Password length must be <= 50!");
          error3.setText("!");
          // if its 11th character then just setText to previous
          // one
          addPassTextField.setText(addPassTextField.getText().substring(0, 50));
        } else {
          error3.setText("");
          error4.setText("");
        }
      }
    });
  }

  public void addCheck() {
    if (addUserTextField.getText().isEmpty() && addPassTextField.getText().isEmpty()) {
      error1.setTextFill(Color.RED);
      error2.setTextFill(Color.RED);
      error3.setTextFill(Color.RED);
      error4.setTextFill(Color.RED);
      error1.setText("!");
      error3.setText("!");
      error2.setText("Enter username!");
      error4.setText("Enter password!");
    } else if (addUserTextField.getText().isEmpty() && !addPassTextField.getText().isEmpty()) {
      error1.setTextFill(Color.RED);
      error2.setTextFill(Color.RED);
      error1.setText("!");
      error2.setText("Enter username!");
    } else if (!addUserTextField.getText().isEmpty() && addPassTextField.getText().isEmpty()) {
      error3.setTextFill(Color.RED);
      error4.setTextFill(Color.RED);
      error3.setText("!");
      error4.setText("Enter password!");
    } else if (!addUserTextField.getText().isEmpty() && !addPassTextField.getText().isEmpty()) {
      if (addUserTextField.getText().length() < 5 && addPassTextField.getText().length() < 8) {
        error1.setTextFill(Color.RED);
        error2.setTextFill(Color.RED);
        error3.setTextFill(Color.RED);
        error4.setTextFill(Color.RED);
        error1.setText("!");
        error3.setText("!");
        error2.setText("Username must > 5 characters!");
        error4.setText("Password must > 8 characters!");
      } else if (addUserTextField.getText().length() >= 5 && addPassTextField.getText().length() < 8) {
        error3.setTextFill(Color.RED);
        error4.setTextFill(Color.RED);
        error3.setText("!");
        error4.setText("Password must > 8 characters!");
      } else if (addUserTextField.getText().length() < 5 && addPassTextField.getText().length() >= 8) {
        error1.setTextFill(Color.RED);
        error2.setTextFill(Color.RED);
        error1.setText("!");
        error2.setText("Username must > 5 characters!");
      } else if (addUserTextField.getText().length() >= 5 && addPassTextField.getText().length() >= 8) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
          connection = Database.getInstance().getConnection();
          preparedStatement = connection.prepareStatement("select * from account where username = ?");
          preparedStatement.setString(1, addUserTextField.getText());
          resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            error1.setTextFill(Color.RED);
            error2.setTextFill(Color.RED);
            error1.setText("!");
            error2.setText("Username existing!");
          } else {
            preparedStatement = connection.prepareStatement("insert into account(username, password, role) values(?, ?, ?)");
            preparedStatement.setString(1, addUserTextField.getText());
            preparedStatement.setString(2, addPassTextField.getText());
            if (addAdmin.isSelected()) {
              preparedStatement.setString(3, "ad");
            } else if (addEmployee.isSelected()) {
              preparedStatement.setString(3, "ep");
            }
            int kq = preparedStatement.executeUpdate();
            if (kq > 0) {
              error4.setTextFill(Color.GREEN);
              error4.setText("Added " + addUserTextField.getText() + "!");
              resetTable();
            } else {
              error4.setTextFill(Color.RED);
              error4.setText("We can't add your contact at this time. Please try again!");
            }
          }
        } catch (SQLException e) {
          Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
          try {
            if (resultSet != null) {
              resultSet.close();
            }
            if (preparedStatement != null) {
              preparedStatement.close();
            }
            if (connection != null) {
              connection.close();
            }
          } catch (SQLException e) {
            Logger.getLogger(AccountManagementController.class.getName()).log(Level.SEVERE, null, e);
          }
        }
      }
    }
  }

  public void upUsernameLimitLength() {
    upUserTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (upUserTextField.getText().length() > 25) {
          error5.setTextFill(Color.RED);
          error6.setTextFill(Color.RED);
          error6.setText("Username length must be <= 25!");
          error5.setText("!");
          // if its 11th character then just setText to previous one
          upUserTextField.setText(upUserTextField.getText().substring(0, 25));
        } else {
          error5.setText("");
          error6.setText("");
        }
      }
    });
  }

  public void upPasswordLimitLength() {
    upPassTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        // Check if the new character is greater than LIMIT
        if (upPassTextField.getText().length() > 50) {
          error8.setTextFill(Color.RED);
          error7.setTextFill(Color.RED);
          error8.setText("Password length must be <= 50!");
          error7.setText("!");
          // if its 11th character then just setText to previous
          // one
          upPassTextField.setText(upPassTextField.getText().substring(0, 50));
        } else {
          error7.setText("");
          error8.setText("");
        }
      }
    });
  }

  public void searchUsername() {
    if (upUserTextField.getText().isEmpty()) {
      error5.setTextFill(Color.RED);
      error6.setTextFill(Color.RED);
      error5.setText("!");
      error6.setText("Please enter username!");
    } else {
      error5.setText("");
      error6.setText("");
    }
  }

  public void updateCheck() {
    if (upUserTextField.getText().isEmpty()){
      error5.setTextFill(Color.RED);
      error6.setTextFill(Color.RED);
      error5.setText("!");
      error6.setText("Please enter username!");
    }
  }

  public void deleteCheck() {

  }

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

  public void goToHistory() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HistoryScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToAdminPackingHistory() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminCenterScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void adminLogout() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToVehicleManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("VehicleManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToTicketManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TicketManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
