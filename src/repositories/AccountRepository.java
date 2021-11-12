package repositories;

import models.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
  public List<Account> getAccount() {
    List<Account> accounts = new ArrayList();
    Connection connection;
    ResultSet resultSet;
    try {
      connection = Database.getInstance().getConnection();
      String sql = "select * from account";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Account account = new Account();
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        if (resultSet.getString("role").equals("ad")) {
          account.setRole("admin");
        } else {
          account.setRole("employee");
        }
        accounts.add(account);
      }
      return accounts;
    } catch (SQLException sqlException) {
      System.err.println("Cannot get histories from DB: " + sqlException);
      return accounts;
    }
  }
}
