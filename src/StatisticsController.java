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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsController implements Initializable {
  private int sum = 0, bicycles = 0, motorbike = 0, seat1Sum = 0, seat2Sum = 0, seat3Sum = 0;
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy"), dft1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  LocalDateTime now = LocalDateTime.now(), week = now.minusWeeks(1), month = now.minusMonths(1), year = now.minusYears(1);
  DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane statisticsPane;
  @FXML
  private Label parkingFeeLabel, bicyclesLabel, motorbikeLabel, seat1, seat2, seat3, error;
  @FXML
  private MenuBar menuBar;
  @FXML
  private Button weekButton, monthButton, yearButton, allButton;

  public void closeAPP() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Close!");
    alert.setHeaderText("You're about to close the application!");
    alert.setContentText("Do you want to exit?");
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("images/sgd.png"));
    if (alert.showAndWait().orElse(null) == ButtonType.OK) {
      stage = (Stage) statisticsPane.getScene().getWindow();
      stage.close();
    }
  }

  public void getWeeklyData() {
    sum = 0;
    bicycles = 0;
    motorbike = 0;
    seat1Sum = 0;
    seat2Sum = 0;
    seat3Sum = 0;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;
    PreparedStatement preparedStatement5 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    ResultSet resultSet5 = null;
    try {
      new BounceIn(error).play();
      error.setTextFill(Color.BLACK);
      error.setText("Statistics from " + dtf.format(week) + " to " + dtf.format(now));
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM parking LIMIT 0,1");
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        preparedStatement = connection.prepareStatement("select * from parking where status = '0' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement.setString(1, dft1.format(week));
        preparedStatement.setString(2, dft1.format(now));
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          sum += resultSet.getInt("fee");
        }
        new BounceIn(parkingFeeLabel).play();
        parkingFeeLabel.setText(decimalFormat.format(sum) + " VND");
        preparedStatement1 = connection.prepareStatement("select type from parking where status = '0' and type = 'Bicycles' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement1.setString(1, dft1.format(week));
        preparedStatement1.setString(2, dft1.format(now));
        resultSet1 = preparedStatement1.executeQuery();
        while (resultSet1.next()) {
          bicycles += 1;
        }
        new BounceIn(bicyclesLabel).play();
        bicyclesLabel.setText(String.valueOf(bicycles));
        preparedStatement2 = connection.prepareStatement("select type from parking where status = '0' and type = 'Motorbike' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement2.setString(1, dft1.format(week));
        preparedStatement2.setString(2, dft1.format(now));
        resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()) {
          motorbike += 1;
        }
        new BounceIn(motorbikeLabel).play();
        motorbikeLabel.setText(String.valueOf(motorbike));
        preparedStatement3 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '4-8' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement3.setString(1, dft1.format(week));
        preparedStatement3.setString(2, dft1.format(now));
        resultSet3 = preparedStatement3.executeQuery();
        while (resultSet3.next()) {
          seat1Sum += 1;
        }
        new BounceIn(seat1).play();
        seat1.setText(String.valueOf(seat1Sum));
        preparedStatement4 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '9-29' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement4.setString(1, dft1.format(week));
        preparedStatement4.setString(2, dft1.format(now));
        resultSet4 = preparedStatement4.executeQuery();
        while (resultSet4.next()) {
          seat2Sum += 1;
        }
        new BounceIn(seat2).play();
        seat2.setText(String.valueOf(seat2Sum));
        preparedStatement5 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '30+' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement5.setString(1, dft1.format(week));
        preparedStatement5.setString(2, dft1.format(now));
        resultSet5 = preparedStatement5.executeQuery();
        while (resultSet5.next()) {
          seat3Sum += 1;
        }
        new BounceIn(seat3).play();
        seat3.setText(String.valueOf(seat3Sum));
      } else {
        parkingFeeLabel.setText("0 VND");
        bicyclesLabel.setText("0");
        motorbikeLabel.setText("0");
        seat1.setText("0");
        seat2.setText("0");
        seat3.setText("0");
      }
    } catch (SQLException e) {
      Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Connection error, please try again!");
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
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  public void getMonthlyData() {
    sum = 0;
    bicycles = 0;
    motorbike = 0;
    seat1Sum = 0;
    seat2Sum = 0;
    seat3Sum = 0;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;
    PreparedStatement preparedStatement5 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    ResultSet resultSet5 = null;
    try {
      new BounceIn(error).play();
      error.setTextFill(Color.BLACK);
      error.setText("Statistics from " + dtf.format(month) + " to " + dtf.format(now));
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM parking LIMIT 0,1");
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        preparedStatement = connection.prepareStatement("select * from parking where status = '0' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement.setString(1, dft1.format(month));
        preparedStatement.setString(2, dft1.format(now));
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          sum += resultSet.getInt("fee");
        }
        new BounceIn(parkingFeeLabel).play();
        parkingFeeLabel.setText(decimalFormat.format(sum) + " VND");
        preparedStatement1 = connection.prepareStatement("select type from parking where status = '0' and type = 'Bicycles' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement1.setString(1, dft1.format(month));
        preparedStatement1.setString(2, dft1.format(now));
        resultSet1 = preparedStatement1.executeQuery();
        while (resultSet1.next()) {
          bicycles += 1;
        }
        new BounceIn(bicyclesLabel).play();
        bicyclesLabel.setText(String.valueOf(bicycles));
        preparedStatement2 = connection.prepareStatement("select type from parking where status = '0' and type = 'Motorbike' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement2.setString(1, dft1.format(month));
        preparedStatement2.setString(2, dft1.format(now));
        resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()) {
          motorbike += 1;
        }
        new BounceIn(motorbikeLabel).play();
        motorbikeLabel.setText(String.valueOf(motorbike));
        preparedStatement3 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '4-8' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement3.setString(1, dft1.format(month));
        preparedStatement3.setString(2, dft1.format(now));
        resultSet3 = preparedStatement3.executeQuery();
        while (resultSet3.next()) {
          seat1Sum += 1;
        }
        new BounceIn(seat1).play();
        seat1.setText(String.valueOf(seat1Sum));
        preparedStatement4 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '9-29' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement4.setString(1, dft1.format(month));
        preparedStatement4.setString(2, dft1.format(now));
        resultSet4 = preparedStatement4.executeQuery();
        while (resultSet4.next()) {
          seat2Sum += 1;
        }
        new BounceIn(seat2).play();
        seat2.setText(String.valueOf(seat2Sum));
        preparedStatement5 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '30+' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement5.setString(1, dft1.format(month));
        preparedStatement5.setString(2, dft1.format(now));
        resultSet5 = preparedStatement5.executeQuery();
        while (resultSet5.next()) {
          seat3Sum += 1;
        }
        new BounceIn(seat3).play();
        seat3.setText(String.valueOf(seat3Sum));
      } else {
        parkingFeeLabel.setText("0 VND");
        bicyclesLabel.setText("0");
        motorbikeLabel.setText("0");
        seat1.setText("0");
        seat2.setText("0");
        seat3.setText("0");
      }
    } catch (SQLException e) {
      Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Connection error, please try again!");
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
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  public void getYearlyData() {
    sum = 0;
    bicycles = 0;
    motorbike = 0;
    seat1Sum = 0;
    seat2Sum = 0;
    seat3Sum = 0;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;
    PreparedStatement preparedStatement5 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    ResultSet resultSet5 = null;
    try {
      new BounceIn(error).play();
      error.setTextFill(Color.BLACK);
      error.setText("Statistics from " + dtf.format(year) + " to " + dtf.format(now));
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM parking LIMIT 0,1");
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        preparedStatement = connection.prepareStatement("select * from parking where status = '0' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement.setString(1, dft1.format(year));
        preparedStatement.setString(2, dft1.format(now));
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          sum += resultSet.getInt("fee");
        }
        new BounceIn(parkingFeeLabel).play();
        parkingFeeLabel.setText(decimalFormat.format(sum) + " VND");
        preparedStatement1 = connection.prepareStatement("select type from parking where status = '0' and type = 'Bicycles' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement1.setString(1, dft1.format(year));
        preparedStatement1.setString(2, dft1.format(now));
        resultSet1 = preparedStatement1.executeQuery();
        while (resultSet1.next()) {
          bicycles += 1;
        }
        new BounceIn(bicyclesLabel).play();
        bicyclesLabel.setText(String.valueOf(bicycles));
        preparedStatement2 = connection.prepareStatement("select type from parking where status = '0' and type = 'Motorbike' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement2.setString(1, dft1.format(year));
        preparedStatement2.setString(2, dft1.format(now));
        resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()) {
          motorbike += 1;
        }
        new BounceIn(motorbikeLabel).play();
        motorbikeLabel.setText(String.valueOf(motorbike));
        preparedStatement3 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '4-8' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement3.setString(1, dft1.format(year));
        preparedStatement3.setString(2, dft1.format(now));
        resultSet3 = preparedStatement3.executeQuery();
        while (resultSet3.next()) {
          seat1Sum += 1;
        }
        new BounceIn(seat1).play();
        seat1.setText(String.valueOf(seat1Sum));
        preparedStatement4 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '9-29' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement4.setString(1, dft1.format(year));
        preparedStatement4.setString(2, dft1.format(now));
        resultSet4 = preparedStatement4.executeQuery();
        while (resultSet4.next()) {
          seat2Sum += 1;
        }
        new BounceIn(seat2).play();
        seat2.setText(String.valueOf(seat2Sum));
        preparedStatement5 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '30+' and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') >= ?) and (STR_TO_DATE(time_out, '%d-%m-%Y %H:%i:%s') <= ?)");
        preparedStatement5.setString(1, dft1.format(year));
        preparedStatement5.setString(2, dft1.format(now));
        resultSet5 = preparedStatement5.executeQuery();
        while (resultSet5.next()) {
          seat3Sum += 1;
        }
        new BounceIn(seat3).play();
        seat3.setText(String.valueOf(seat3Sum));
      } else {
        parkingFeeLabel.setText("0 VND");
        bicyclesLabel.setText("0");
        motorbikeLabel.setText("0");
        seat1.setText("0");
        seat2.setText("0");
        seat3.setText("0");
      }
    } catch (SQLException e) {
      Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Connection error, please try again!");
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
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  public void getAllData() {
    sum = 0;
    bicycles = 0;
    motorbike = 0;
    seat1Sum = 0;
    seat2Sum = 0;
    seat3Sum = 0;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;
    PreparedStatement preparedStatement5 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    ResultSet resultSet5 = null;
    try {
      new BounceIn(error).play();
      error.setTextFill(Color.BLACK);
      error.setText("All Statistics!");
      connection = Database.getInstance().getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM parking LIMIT 0,1");
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        preparedStatement = connection.prepareStatement("select * from parking where status = '0'");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          sum += resultSet.getInt("fee");
        }
        new BounceIn(parkingFeeLabel).play();
        parkingFeeLabel.setText(decimalFormat.format(sum) + " VND");
        preparedStatement1 = connection.prepareStatement("select type from parking where status = '0' and type = 'Bicycles'");
        resultSet1 = preparedStatement1.executeQuery();
        while (resultSet1.next()) {
          bicycles += 1;
        }
        new BounceIn(bicyclesLabel).play();
        bicyclesLabel.setText(String.valueOf(bicycles));
        preparedStatement2 = connection.prepareStatement("select type from parking where status = '0' and type = 'Motorbike'");
        resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()) {
          motorbike += 1;
        }
        new BounceIn(motorbikeLabel).play();
        motorbikeLabel.setText(String.valueOf(motorbike));
        preparedStatement3 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '4-8'");
        resultSet3 = preparedStatement3.executeQuery();
        while (resultSet3.next()) {
          seat1Sum += 1;
        }
        new BounceIn(seat1).play();
        seat1.setText(String.valueOf(seat1Sum));
        preparedStatement4 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '9-29'");
        resultSet4 = preparedStatement4.executeQuery();
        while (resultSet4.next()) {
          seat2Sum += 1;
        }
        new BounceIn(seat2).play();
        seat2.setText(String.valueOf(seat2Sum));
        preparedStatement5 = connection.prepareStatement("select type from parking where status = '0' and type = 'Car' and seat = '30+'");
        resultSet5 = preparedStatement5.executeQuery();
        while (resultSet5.next()) {
          seat3Sum += 1;
        }
        new BounceIn(seat3).play();
        seat3.setText(String.valueOf(seat3Sum));
      } else {
        parkingFeeLabel.setText("0 VND");
        bicyclesLabel.setText("0");
        motorbikeLabel.setText("0");
        seat1.setText("0");
        seat2.setText("0");
        seat3.setText("0");
      }
    } catch (SQLException e) {
      Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
    } catch (NullPointerException nullPointerException) {
      new BounceIn(error).play();
      error.setTextFill(Color.RED);
      error.setText("Connection error, please try again!");
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
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  public void weekClick() {
    getWeeklyData();
    weekButton.setTextFill(Color.web("#e91874"));
    monthButton.setTextFill(Color.BLACK);
    yearButton.setTextFill(Color.BLACK);
    allButton.setTextFill(Color.BLACK);
  }

  public void monthClick() {
    getMonthlyData();
    monthButton.setTextFill(Color.web("#e91874"));
    weekButton.setTextFill(Color.BLACK);
    yearButton.setTextFill(Color.BLACK);
    allButton.setTextFill(Color.BLACK);
  }

  public void yearClick() {
    getYearlyData();
    yearButton.setTextFill(Color.web("#e91874"));
    monthButton.setTextFill(Color.BLACK);
    weekButton.setTextFill(Color.BLACK);
    allButton.setTextFill(Color.BLACK);
  }

  public void allClick() {
    getAllData();
    allButton.setTextFill(Color.web("#e91874"));
    monthButton.setTextFill(Color.BLACK);
    weekButton.setTextFill(Color.BLACK);
    yearButton.setTextFill(Color.BLACK);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    getWeeklyData();
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

  public void goToSlotsManagement() throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SlotsManagementScene.fxml")));
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
