import animatefx.animation.BounceIn;
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
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlotsController implements Initializable {
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane slotsPane;
  @FXML
  private TextField bicyclesField, motorbikeField, carField, seat1, seat2, seat3, upBicycles, upMotorbike, upSeat1, upSeat2, upSeat3;
  @FXML
  private Label error, currentBicycles, currentMotorbike, currentSeat1, currentSeat2, currentSeat3;
  @FXML
  private MenuBar menuBar;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) slotsPane.getScene().getWindow();
      stage.close();
    }
  }

  public void limitLength() {
    upBicycles.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upBicycles.getText().length() >= 11) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upBicycles.setText(upBicycles.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength1() {
    upMotorbike.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upMotorbike.getText().length() >= 11) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upMotorbike.setText(upMotorbike.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength2() {
    upSeat1.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upSeat1.getText().length() >= 11) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upSeat1.setText(upSeat1.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength3() {
    upSeat2.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upSeat2.getText().length() >= 11) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upSeat2.setText(upSeat2.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void limitLength4() {
    upSeat3.lengthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() > oldValue.intValue()) {
        if (upSeat3.getText().length() >= 11) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("Length must be <= 11 characters!");
          upSeat3.setText(upSeat3.getText().substring(0, 10));
        } else {
          error.setText("");
        }
      }
    });
  }

  public void getCurrentSlots() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    try {
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = 'bicycles'");
      preparedStatement1 = connection.prepareStatement("select slots from pricevsslots where type = 'motorbike'");
      preparedStatement2 = connection.prepareStatement("select slots from pricevsslots where type = '4t8car'");
      preparedStatement3 = connection.prepareStatement("select slots from pricevsslots where type = '9t29car'");
      preparedStatement4 = connection.prepareStatement("select slots from pricevsslots where type = '30pcar'");
      resultSet = preparedStatement.executeQuery();
      resultSet1 = preparedStatement1.executeQuery();
      resultSet2 = preparedStatement2.executeQuery();
      resultSet3 = preparedStatement3.executeQuery();
      resultSet4 = preparedStatement4.executeQuery();
      if (resultSet.next() && resultSet1.next() && resultSet2.next() && resultSet3.next() && resultSet4.next()) {
        currentBicycles.setText("/ " + resultSet.getInt("slots"));
        upBicycles.setPromptText(String.valueOf(resultSet.getInt("slots")));
        currentMotorbike.setText("/ " + resultSet1.getInt("slots"));
        upMotorbike.setPromptText(String.valueOf(resultSet1.getInt("slots")));
        currentSeat1.setText("/ " + resultSet2.getInt("slots"));
        upSeat1.setPromptText(String.valueOf(resultSet2.getInt("slots")));
        currentSeat2.setText("/ " + resultSet3.getInt("slots"));
        upSeat2.setPromptText(String.valueOf(resultSet3.getInt("slots")));
        currentSeat3.setText("/ " + resultSet4.getInt("slots"));
        upSeat3.setPromptText(String.valueOf(resultSet4.getInt("slots")));
      } else {
        currentBicycles.setText("Data error!");
        currentMotorbike.setText("Data error!");
        currentSeat1.setText("Data error!");
        currentSeat2.setText("Data error!");
        currentSeat3.setText("Data error!");
      }
    } catch (SQLException e) {
      Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      new BounceIn(error).play();
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
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  public void getData() throws SQLException {
    Connection connection = Database.getInstance().getConnection();
    Statement stmt = connection.createStatement();
    String query = "select count(*) from parking where status = 1 and type = 'Bicycles'";
    ResultSet rs = stmt.executeQuery(query);
    rs.next();
    bicyclesField.setText(String.valueOf(rs.getInt(1)));
    String query1 = "select count(*) from parking where status = 1 and type = 'Motorbike'";
    ResultSet rs1 = stmt.executeQuery(query1);
    rs1.next();
    motorbikeField.setText(String.valueOf(rs1.getInt(1)));
    String query2 = "select count(*) from parking where status = 1 and type = 'Car'";
    ResultSet rs2 = stmt.executeQuery(query2);
    rs2.next();
    carField.setText(String.valueOf(rs2.getInt(1)));
    String query3 = "select count(*) from parking where status = 1 and type = 'Car' and seat = '4-8'";
    ResultSet rs3 = stmt.executeQuery(query3);
    rs3.next();
    seat1.setText(String.valueOf(rs3.getInt(1)));
    String query4 = "select count(*) from parking where status = 1 and type = 'Car' and seat = '9-29'";
    ResultSet rs4 = stmt.executeQuery(query4);
    rs4.next();
    seat2.setText(String.valueOf(rs4.getInt(1)));
    String query5 = "select count(*) from parking where status = 1 and type = 'Car' and seat = '30+'";
    ResultSet rs5 = stmt.executeQuery(query5);
    rs5.next();
    seat3.setText(String.valueOf(rs5.getInt(1)));
  }

  public void fullCheck() {
    if (bicyclesField.getText().equals(upBicycles.getPromptText())) {
      currentBicycles.setTextFill(Color.RED);
    } else {
      currentBicycles.setTextFill(Color.BLACK);
    }
    if (motorbikeField.getText().equals(upMotorbike.getPromptText())) {
      currentMotorbike.setTextFill(Color.RED);
    } else {
      currentMotorbike.setTextFill(Color.BLACK);
    }
    if (seat1.getText().equals(upSeat1.getPromptText())) {
      currentSeat1.setTextFill(Color.RED);
    } else {
      currentSeat1.setTextFill(Color.BLACK);
    }
    if (seat2.getText().equals(upSeat2.getPromptText())) {
      currentSeat2.setTextFill(Color.RED);
    } else {
      currentSeat2.setTextFill(Color.BLACK);
    }
    if (seat3.getText().equals(upSeat3.getPromptText())) {
      currentSeat3.setTextFill(Color.RED);
    } else {
      currentSeat3.setTextFill(Color.BLACK);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      getData();
      getCurrentSlots();
      fullCheck();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void updateBicyclesCheck() {
    if (upBicycles.getText().isEmpty()) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Nothings to update!");
    } else {
      try {
        if (Integer.parseInt(upBicycles.getText()) < Integer.parseInt(bicyclesField.getText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value must be > " + bicyclesField.getText() + "!");
        } else if (Integer.parseInt(upBicycles.getText()) == Integer.parseInt(upBicycles.getPromptText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value can't be equal " + upBicycles.getPromptText() + "!");
        } else {
          Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;
          try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots LIMIT 0,1");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
              new BounceIn(error).play();
              error.setTextFill(Color.RED);
              error.setText("List is empty!");
            } else {
              preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = 'bicycles'");
              resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                preparedStatement = connection.prepareStatement("update pricevsslots set slots=? where type = 'bicycles'");
                preparedStatement.setString(1, upBicycles.getText());
                int kq = preparedStatement.executeUpdate();
                new BounceIn(error).play();
                if (kq > 0) {
                  error.setTextFill(Color.GREEN);
                  error.setText("Update successfully!");
                  upBicycles.setText("");
                  getData();
                  getCurrentSlots();
                  fullCheck();
                } else {
                  error.setTextFill(Color.RED);
                  error.setText("Update error, Please try again later!");
                }
              } else {
                new BounceIn(error).play();
                error.setTextFill(Color.RED);
                error.setText("Database error, please try again later!");
              }
            }
          } catch (SQLException e) {
            Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
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
              Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
            }
          }
        }
      } catch (NumberFormatException numberFormatException) {
        new BounceIn(error).play();
        error.setTextFill(Color.RED);
        error.setText("Please enter a valid number!");
      }
    }
  }

  public void updateMotorbikeCheck() {
    if (upMotorbike.getText().isEmpty()) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Nothings to update!");
    } else {
      try {
        if (Integer.parseInt(upMotorbike.getText()) < Integer.parseInt(motorbikeField.getText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value must be > " + motorbikeField.getText() + "!");
        } else if (Integer.parseInt(upMotorbike.getText()) == Integer.parseInt(upMotorbike.getPromptText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value can't be equal " + upMotorbike.getPromptText() + "!");
        } else {
          Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;
          try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots LIMIT 0,1");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
              new BounceIn(error).play();
              error.setTextFill(Color.RED);
              error.setText("List is empty!");
            } else {
              preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = 'motorbike'");
              resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                preparedStatement = connection.prepareStatement("update pricevsslots set slots=? where type = 'motorbike'");
                preparedStatement.setString(1, upMotorbike.getText());
                int kq = preparedStatement.executeUpdate();
                new BounceIn(error).play();
                if (kq > 0) {
                  error.setTextFill(Color.GREEN);
                  error.setText("Update successfully!");
                  upMotorbike.setText("");
                  getData();
                  getCurrentSlots();
                  fullCheck();
                } else {
                  error.setTextFill(Color.RED);
                  error.setText("Update error, Please try again later!");
                }
              } else {
                new BounceIn(error).play();
                error.setTextFill(Color.RED);
                error.setText("Database error, please try again later!");
              }
            }
          } catch (SQLException e) {
            Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
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
              Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
            }
          }
        }
      } catch (NumberFormatException numberFormatException) {
        new BounceIn(error).play();
        error.setTextFill(Color.RED);
        error.setText("Please enter a valid number!");
      }
    }
  }

  public void updateSeat1Check() {
    if (upSeat1.getText().isEmpty()) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Nothings to update!");
    } else {
      try {
        if (Integer.parseInt(upSeat1.getText()) < Integer.parseInt(seat1.getText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value must be > " + seat1.getText() + "!");
        } else if (Integer.parseInt(upSeat1.getText()) == Integer.parseInt(upSeat1.getPromptText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value can't be equal " + upSeat1.getPromptText() + "!");
        } else {
          Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;
          try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots LIMIT 0,1");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
              new BounceIn(error).play();
              error.setTextFill(Color.RED);
              error.setText("List is empty!");
            } else {
              preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = '4t8car'");
              resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                preparedStatement = connection.prepareStatement("update pricevsslots set slots=? where type = '4t8car'");
                preparedStatement.setString(1, upSeat1.getText());
                int kq = preparedStatement.executeUpdate();
                new BounceIn(error).play();
                if (kq > 0) {
                  error.setTextFill(Color.GREEN);
                  error.setText("Update successfully!");
                  upSeat1.setText("");
                  getData();
                  getCurrentSlots();
                  fullCheck();
                } else {
                  error.setTextFill(Color.RED);
                  error.setText("Update error, Please try again later!");
                }
              } else {
                new BounceIn(error).play();
                error.setTextFill(Color.RED);
                error.setText("Database error, please try again later!");
              }
            }
          } catch (SQLException e) {
            Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
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
              Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
            }
          }
        }
      } catch (NumberFormatException numberFormatException) {
        new BounceIn(error).play();
        error.setTextFill(Color.RED);
        error.setText("Please enter a valid number!");
      }
    }
  }

  public void updateSeat2Check() {
    if (upSeat2.getText().isEmpty()) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Nothings to update!");
    } else {
      try {
        if (Integer.parseInt(upSeat2.getText()) < Integer.parseInt(seat2.getText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value must be > " + seat2.getText() + "!");
        } else if (Integer.parseInt(upSeat2.getText()) == Integer.parseInt(upSeat2.getPromptText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value can't be equal " + upSeat2.getPromptText() + "!");
        } else {
          Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;
          try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots LIMIT 0,1");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
              new BounceIn(error).play();
              error.setTextFill(Color.RED);
              error.setText("List is empty!");
            } else {
              preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = '9t29car'");
              resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                preparedStatement = connection.prepareStatement("update pricevsslots set slots=? where type = '9t29car'");
                preparedStatement.setString(1, upSeat2.getText());
                int kq = preparedStatement.executeUpdate();
                new BounceIn(error).play();
                if (kq > 0) {
                  error.setTextFill(Color.GREEN);
                  error.setText("Update successfully!");
                  upSeat2.setText("");
                  getData();
                  getCurrentSlots();
                  fullCheck();
                } else {
                  error.setTextFill(Color.RED);
                  error.setText("Update error, Please try again later!");
                }
              } else {
                new BounceIn(error).play();
                error.setTextFill(Color.RED);
                error.setText("Database error, please try again later!");
              }
            }
          } catch (SQLException e) {
            Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
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
              Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
            }
          }
        }
      } catch (NumberFormatException numberFormatException) {
        new BounceIn(error).play();
        error.setTextFill(Color.RED);
        error.setText("Please enter a valid number!");
      }
    }
  }

  public void updateSeat3Check() {
    if (upSeat3.getText().isEmpty()) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Nothings to update!");
    } else {
      try {
        if (Integer.parseInt(upSeat3.getText()) < Integer.parseInt(seat3.getText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value must be > " + seat3.getText() + "!");
        } else if (Integer.parseInt(upSeat3.getText()) == Integer.parseInt(upSeat3.getPromptText())) {
          new BounceIn(error).play();
          error.setTextFill(Color.RED);
          error.setText("New value can't be equal " + upSeat3.getPromptText() + "!");
        } else {
          Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;
          try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM pricevsslots LIMIT 0,1");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
              new BounceIn(error).play();
              error.setTextFill(Color.RED);
              error.setText("List is empty!");
            } else {
              preparedStatement = connection.prepareStatement("select slots from pricevsslots where type = '30pcar'");
              resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                preparedStatement = connection.prepareStatement("update pricevsslots set slots=? where type = '30pcar'");
                preparedStatement.setString(1, upSeat3.getText());
                int kq = preparedStatement.executeUpdate();
                new BounceIn(error).play();
                if (kq > 0) {
                  error.setTextFill(Color.GREEN);
                  error.setText("Update successfully!");
                  upSeat3.setText("");
                  getData();
                  getCurrentSlots();
                  fullCheck();
                } else {
                  error.setTextFill(Color.RED);
                  error.setText("Update error, Please try again later!");
                }
              } else {
                new BounceIn(error).play();
                error.setTextFill(Color.RED);
                error.setText("Database error, please try again later!");
              }
            }
          } catch (SQLException e) {
            Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
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
              Logger.getLogger(SlotsController.class.getName()).log(Level.SEVERE, null, e);
            }
          }
        }
      } catch (NumberFormatException numberFormatException) {
        new BounceIn(error).play();
        error.setTextFill(Color.RED);
        error.setText("Please enter a valid number!");
      }
    }
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

  public void goToStatistics() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StatisticsScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void goToPricesManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PriceManagementScene.fxml")));
    Stage stage = (Stage) menuBar.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
