package repositories;

import models.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
  public List<History> getHistories(int pageNumber, int numberOfItemPerPage) {
    List<History> histories = new ArrayList();
    Connection connection;
    ResultSet resultSet;
    try {
      connection = Database.getInstance().getConnection();
      String sql = "select * from parking order by id desc limit ? offset ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, numberOfItemPerPage);
      preparedStatement.setInt(2, pageNumber * numberOfItemPerPage);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        History history = new History();
        history.setId(resultSet.getInt("id"));
        history.setLicense_plate(resultSet.getString("license_plate"));
        history.setType(resultSet.getString("type"));
        history.setSeat(resultSet.getString("seat"));
        history.setTicket(resultSet.getInt("ticket"));
        history.setTime_in(resultSet.getString("time_in"));
        history.setTime_out(resultSet.getString("time_out"));
        history.setParking_time(resultSet.getString("parking_time"));
        history.setFee(resultSet.getString("fee"));
        history.setStatus(resultSet.getInt("status"));
        histories.add(history);
      }
      return histories;
    } catch (SQLException sqlException) {
      System.err.println("Cannot get histories from DB: " + sqlException);
      return histories;
    }
  }

  public int getNumberOfPage() throws SQLException {
    //Getting the connection
    Connection connection = Database.getInstance().getConnection();
    //Creating the Statement object
    Statement stmt = connection.createStatement();
    //Query to get the number of rows in a table
    String query = "select count(*) from parking";
    //Executing the query
    ResultSet rs = stmt.executeQuery(query);
    //Retrieving the result
    rs.next();
    int count = rs.getInt(1);
    return count;
  }

  public List<History> getHistoriesFiltered(int pageNumber, int numberOfItemPerPage, String typedString) {
    List<History> histories = new ArrayList();
    Connection connection;
    ResultSet resultSet;
    try {
      connection = Database.getInstance().getConnection();
      String sql = "select * from parking where license_plate like ? order by id desc limit ? offset ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, "%" + typedString + "%");
      preparedStatement.setInt(2, numberOfItemPerPage);
      preparedStatement.setInt(3, pageNumber * numberOfItemPerPage);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        History history = new History();
        history.setId(resultSet.getInt("id"));
        history.setLicense_plate(resultSet.getString("license_plate"));
        history.setType(resultSet.getString("type"));
        history.setSeat(resultSet.getString("seat"));
        history.setTicket(resultSet.getInt("ticket"));
        history.setTime_in(resultSet.getString("time_in"));
        history.setTime_out(resultSet.getString("time_out"));
        history.setParking_time(resultSet.getString("parking_time"));
        history.setFee(resultSet.getString("fee"));
        history.setStatus(resultSet.getInt("status"));
        histories.add(history);
      }
      return histories;
    } catch (SQLException sqlException) {
      System.err.println("Cannot get histories from DB: " + sqlException);
      return histories;
    }
  }
}
