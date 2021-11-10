package repositories;

import models.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketRepository {
  public List<Ticket> getTicket() {
    List<Ticket> tickets = new ArrayList();
    Connection connection;
    ResultSet resultSet;
    try {
      connection = Database.getInstance().getConnection();
      String sql = "select * from ticket";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Ticket ticket = new Ticket();
        ticket.setLicense_plate(resultSet.getString("license_plate"));
        ticket.setExpired_date(resultSet.getString("expired_date"));
        if (resultSet.getString("status").equals("0")) {
          ticket.setStatus("Suspended");
        } else if (resultSet.getString("status").equals("1")) {
          SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
          try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Date d1 = sdf.parse(resultSet.getString("expired_date"));
            Date d2 = sdf.parse(dtf.format(now));
            long difference_In_Time = d2.getTime() - d1.getTime();
            if (difference_In_Time < 0) {
              ticket.setStatus("Active");
            } else {
              ticket.setStatus("Expired");
            }
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
        tickets.add(ticket);
      }
      return tickets;
    } catch (SQLException sqlException) {
      System.err.println("Cannot get histories from DB: " + sqlException);
      return tickets;
    }
  }
}
