package sample;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class parkingmanagersystem {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");

    void Input(String licen, int seat_, String type) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "");
            PreparedStatement preparedStatement = con.prepareStatement("insert into t_parking (loai_xe, bien_so_xe, " +
                    "thoi_gian_vao, so_cho_ngoi) values(?, ?, now(), ?)");
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, licen);
            preparedStatement.setInt(3, seat_);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    void Output(String licen, String parkingTime, double fee){
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "");
            PreparedStatement preparedStatement = con.prepareStatement("update t_parking set thoi_gian_ra = now(), " +
                    "thoi_gian_gui = ?, gia_tien = ?, status = 1 where bien_so_xe = ?");
            preparedStatement.setString(1, parkingTime);
            preparedStatement.setDouble(2, fee);
            preparedStatement.setString(3, licen);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    void monthAdd(String license, String type, Calendar expiration){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Connection con = null;
        try {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "");
                PreparedStatement stm = con.prepareStatement("insert into month_ticket values (?, ?, ?)");
                stm.setString(1, license);
                stm.setString(2, type);
                stm.setString(3, dateFormat.format(expiration.getTime()));
                stm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    void updateMonthTicket() throws SQLException {
        Connection con = null;
        ResultSet rls = null;
        Date date = new Date();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "");
            PreparedStatement stm = con.prepareStatement("select * from month_ticket");
            rls = stm.executeQuery();
            while (rls.next()){
                if (dateFormat.parse(rls.getString("ngay_het_han")).getTime() - date.getTime() <= 0){
                    stm = con.prepareStatement("delete from month_ticket where ngay_het_han = ?");
                    stm.setString(1, rls.getString("ngay_het_han"));
                    stm.executeUpdate();
                }
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }finally {
            if (rls != null) {
                rls.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }
}
