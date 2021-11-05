package repositories;

import java.sql.*;

public class Database {
  public static final String URL_STRING = "jdbc:mysql://localhost:3306/parkingsystem";
  public static final String DB_USER = "root";
  public static final String DB_PASSWORD = "";
  private static Database instance;

  public static Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

  //Global object/single pattern
  //connect DB with JDBC
  public Connection getConnection() {
    Connection connection;
    try {
      connection = DriverManager.getConnection(URL_STRING, DB_USER, DB_PASSWORD);
      return connection;
    } catch (SQLException sqlException) {
      return null;
    }
  }
}
