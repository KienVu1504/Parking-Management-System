import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import repositories.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PriceManagementController implements Initializable {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane pricePane;
  @FXML
  private TextField upBicycles1, upBicycles2, upBicycles3, upMotorbike1, upMotorbike2, upMotorbike3, up1seat1, up1seat2, up1seat3, up2seat1, up2seat2, up2seat3, up3seat1, up3seat2, up3seat3;
  @FXML
  private Label error, currentBicycles1, currentBicycles2, currentBicycles3, currentMotorbike1, currentMotorbike2, currentMotorbike3, current1seat1, current1seat2, current1seat3, current2seat1, current2seat2, current2seat3, current3seat1, current3seat2, current3seat3;
  @FXML
  private MenuBar menuBar;

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
      stage = (Stage) pricePane.getScene().getWindow();
      stage.close();
    }
  }

  public void getCurrentData() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;
    PreparedStatement preparedStatement5 = null;
    PreparedStatement preparedStatement6 = null;
    PreparedStatement preparedStatement7 = null;
    PreparedStatement preparedStatement8 = null;
    PreparedStatement preparedStatement9 = null;
    PreparedStatement preparedStatement10 = null;
    PreparedStatement preparedStatement11 = null;
    PreparedStatement preparedStatement12 = null;
    PreparedStatement preparedStatement13 = null;
    PreparedStatement preparedStatement14 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    ResultSet resultSet5 = null;
    ResultSet resultSet6 = null;
    ResultSet resultSet7 = null;
    ResultSet resultSet8 = null;
    ResultSet resultSet9 = null;
    ResultSet resultSet10 = null;
    ResultSet resultSet11 = null;
    ResultSet resultSet12 = null;
    ResultSet resultSet13 = null;
    ResultSet resultSet14 = null;
    try {
      error.setTextFill(Color.BLACK);
      error.setText("Parking Prices Management");
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("select m240 from pricevsslots where type = 'bicycles'");
      preparedStatement1 = connection.prepareStatement("select 240t480 from pricevsslots where type = 'bicycles'");
      preparedStatement2 = connection.prepareStatement("select 480p from pricevsslots where type = 'bicycles'");
      preparedStatement3 = connection.prepareStatement("select m240 from pricevsslots where type = 'motorbike'");
      preparedStatement4 = connection.prepareStatement("select 240t480 from pricevsslots where type = 'motorbike'");
      preparedStatement5 = connection.prepareStatement("select 480p from pricevsslots where type = 'motorbike'");
      preparedStatement6 = connection.prepareStatement("select m90 from pricevsslots where type = '4t8car'");
      preparedStatement7 = connection.prepareStatement("select 90t1440 from pricevsslots where type = '4t8car'");
      preparedStatement8 = connection.prepareStatement("select 1440p from pricevsslots where type = '4t8car'");
      preparedStatement9 = connection.prepareStatement("select m90 from pricevsslots where type = '9t29car'");
      preparedStatement10 = connection.prepareStatement("select 90t1440 from pricevsslots where type = '9t29car'");
      preparedStatement11 = connection.prepareStatement("select 1440p from pricevsslots where type = '9t29car'");
      preparedStatement12 = connection.prepareStatement("select m90 from pricevsslots where type = '30pcar'");
      preparedStatement13 = connection.prepareStatement("select 90t1440 from pricevsslots where type = '30pcar'");
      preparedStatement14 = connection.prepareStatement("select 1440p from pricevsslots where type = '30pcar'");
      resultSet = preparedStatement.executeQuery();
      resultSet1 = preparedStatement1.executeQuery();
      resultSet2 = preparedStatement2.executeQuery();
      resultSet3 = preparedStatement3.executeQuery();
      resultSet4 = preparedStatement4.executeQuery();
      resultSet5 = preparedStatement5.executeQuery();
      resultSet6 = preparedStatement6.executeQuery();
      resultSet7 = preparedStatement7.executeQuery();
      resultSet8 = preparedStatement8.executeQuery();
      resultSet9 = preparedStatement9.executeQuery();
      resultSet10 = preparedStatement10.executeQuery();
      resultSet11 = preparedStatement11.executeQuery();
      resultSet12 = preparedStatement12.executeQuery();
      resultSet13 = preparedStatement13.executeQuery();
      resultSet14 = preparedStatement14.executeQuery();
      if (resultSet.next() && resultSet1.next() && resultSet2.next() && resultSet3.next() && resultSet4.next() && resultSet5.next() && resultSet6.next() && resultSet7.next() && resultSet8.next() && resultSet9.next() && resultSet10.next() && resultSet11.next() && resultSet12.next() && resultSet13.next() && resultSet14.next()) {
        error.setTextFill(Color.BLACK);
        error.setText("Parking Prices Management");
        currentBicycles1.setText(String.valueOf(resultSet.getInt("m240")));
      } else {
        error.setTextFill(Color.RED);
        error.setText("Data error!");
      }
    } catch (SQLException e) {
      Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      error.setTextFill(Color.RED);
      error.setText("Connection error, please try again later!");
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        } else if (resultSet1 != null) {
          resultSet1.close();
        } else if (resultSet2 != null) {
          resultSet2.close();
        } else if (resultSet3 != null) {
          resultSet3.close();
        } else if (resultSet4 != null) {
          resultSet4.close();
        } else if (resultSet5 != null) {
          resultSet5.close();
        } else if (resultSet6 != null) {
          resultSet6.close();
        } else if (resultSet7 != null) {
          resultSet7.close();
        } else if (resultSet8 != null) {
          resultSet8.close();
        } else if (resultSet9 != null) {
          resultSet9.close();
        } else if (resultSet10 != null) {
          resultSet10.close();
        } else if (resultSet11 != null) {
          resultSet11.close();
        } else if (resultSet12 != null) {
          resultSet12.close();
        } else if (resultSet13 != null) {
          resultSet13.close();
        } else if (resultSet14 != null) {
          resultSet14.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        } else if (preparedStatement1 != null) {
          preparedStatement1.close();
        } else if (preparedStatement2 != null) {
          preparedStatement2.close();
        } else if (preparedStatement3 != null) {
          preparedStatement3.close();
        } else if (preparedStatement4 != null) {
          preparedStatement4.close();
        } else if (preparedStatement5 != null) {
          preparedStatement5.close();
        } else if (preparedStatement6 != null) {
          preparedStatement6.close();
        } else if (preparedStatement7 != null) {
          preparedStatement7.close();
        } else if (preparedStatement8 != null) {
          preparedStatement8.close();
        } else if (preparedStatement9 != null) {
          preparedStatement9.close();
        } else if (preparedStatement10 != null) {
          preparedStatement10.close();
        } else if (preparedStatement11 != null) {
          preparedStatement11.close();
        } else if (preparedStatement12 != null) {
          preparedStatement12.close();
        } else if (preparedStatement13 != null) {
          preparedStatement13.close();
        } else if (preparedStatement14 != null) {
          preparedStatement14.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(PriceManagementController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    getCurrentData();
  }

  public void limitLength() {
    upBicycles1.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upBicycles1.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upBicycles1.setText(upBicycles1.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength1() {
    upBicycles2.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upBicycles2.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upBicycles2.setText(upBicycles2.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength2() {
    upBicycles3.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upBicycles3.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upBicycles3.setText(upBicycles3.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength3() {
    upMotorbike1.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upMotorbike1.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upMotorbike1.setText(upMotorbike1.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength4() {
    upMotorbike2.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upMotorbike2.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upMotorbike2.setText(upMotorbike2.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength5() {
    upMotorbike3.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upMotorbike3.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upMotorbike3.setText(upMotorbike3.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength6() {
    up1seat1.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up1seat1.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up1seat1.setText(up1seat1.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength7() {
    up1seat2.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up1seat2.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up1seat2.setText(up1seat2.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength8() {
    up1seat3.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up1seat3.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up1seat3.setText(up1seat3.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength9() {
    up2seat1.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up2seat1.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up2seat1.setText(up2seat1.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength10() {
    up2seat2.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up2seat2.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up2seat2.setText(up2seat2.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength11() {
    up2seat3.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up2seat3.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up2seat3.setText(up2seat3.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength12() {
    up3seat1.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up3seat1.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up3seat1.setText(up3seat1.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength13() {
    up3seat2.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up3seat2.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up3seat2.setText(up3seat2.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void limitLength14() {
    up3seat3.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (up3seat3.getText().length() >= 11) {
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          up3seat3.setText(up3seat3.getText().substring(0, 10));
        } else {
          error.setTextFill(Color.BLACK);
          error.setText("Parking Prices Management");
        }
      }
    });
  }

  public void goToAccountManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
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
