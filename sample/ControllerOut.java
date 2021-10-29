package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class ControllerOut implements Initializable {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");

    @FXML
    private TextField license;
    @FXML
    private TextField type;
    @FXML
    private TextField timeout;
    @FXML
    private TextField timein;
    @FXML
    private TextField timesend;
    @FXML
    private TextField price;
    @FXML
    private Label warning;

    String lice;
    long times;
    double pric;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeout.setEditable(false);
        timein.setEditable(false);
        timesend.setEditable(false);
        type.setEditable(false);
        warning.setVisible(true);
    }

    public void add(ActionEvent event) throws SQLException {
        parkingmanagersystem parkingSystem = new parkingmanagersystem();
        Connection con = null;
        ResultSet rs = null;
        ResultSet rls = null;
        String parkingTime;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "");
            Date c1 = new Date();
            timeout.setText(dateFormat.format(c1));
            lice = license.getText();
            long expiration = 0;
            PreparedStatement preparedStatement = con.prepareStatement("select * from t_parking where bien_so_xe = ?" +
                    "and status = 0");
            preparedStatement.setString(1, lice);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String type_ = rs.getString("loai_xe");
                preparedStatement = con.prepareStatement("select * from month_ticket WHERE bien_so_xe = ? and loai_xe = ?");
                preparedStatement.setString(1, lice);
                preparedStatement.setString(2, type_);
                rls = preparedStatement.executeQuery();
                if (rls.next()){
                    expiration = dateFormat.parse(rls.getString("ngay_het_han")).getTime() - c1.getTime();
                }
                    Date date = rs.getDate("thoi_gian_vao");
                    System.out.println(date);
                    times = c1.getTime() - date.getTime();
                    long hours = TimeUnit.MILLISECONDS.toHours(times);
                    System.out.println(hours);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(times) - (hours * 60);
                    System.out.println(minutes);
                    long second = TimeUnit.MILLISECONDS.toSeconds(times) - (minutes * 60) - (hours * 60 * 60);
                    System.out.println(second);
                    parkingTime = hours + ":" + minutes + ":" + second;
                    if (expiration <= 0){
                        if (minutes >= 30){
                            hours++;
                        }
                        if (rs.getString("loai_xe").equals("xe đạp(xe đạp điện)")){
                            if (hours <= 4){
                                pric = 3000;
                            }else if(hours <= 8){
                                pric = (double)(((hours - 4) / 4)) *2 + 3000;
                            }else {
                                pric = 2000 + 3000 +((double)((hours - 8)/8)* 5000);
                            }
                        }else if(rs.getString("loai_xe").equals("xe máy(xe máy điện)")) {
                            if (hours <= 4){
                                pric = 6000;
                            }else if(hours <= 8){
                                pric = (double)((hours - 4) * 4000 / 4) + 6000;
                            }else {
                                pric = (double)((((hours - 8) * 9000) / 8)) + 10000;
                            }
                        }else{
                            minutes = hours * 60;
                            if (Integer.parseInt(rs.getString("cho_ngoi")) <= 8){
                                if (minutes <= 90){
                                    pric = 25000;
                                }else if(minutes <= 24 * 60){
                                    pric = (double)((minutes - 90) / 60) * 10000 + 25000;
                                }else{
                                    pric = (double)((((minutes - 90) / 60) - 24) / 12) * 75000 + (25000 + 230000) ;
                                }
                            }else if(Integer.parseInt(rs.getString("cho_ngoi")) <= 24){
                                if (minutes <= 90){
                                    pric = 40000;
                                }else if(minutes <= 24 * 60){
                                    pric = (double)((minutes - 90) / 60) * 15000 + 40000;
                                }else{
                                    pric = (double)((((minutes - 90) / 60) - 24) / 12) * 150000 + (40000 + 345000);
                                }
                            }else{
                                if (minutes <= 90){
                                    pric = 50000;
                                }else{
                                    pric = (double)((minutes - 90) / 60) * 20000 + 50000;
                                }
                            }
                        }
                    }else{
                        pric = 0;
                    }

                String typ = rs.getString("loai_xe");
                type.setText(typ);
                timein.setText(dateFormat.format(rs.getDate("thoi_gian_vao")));
                timeout.setText(dateFormat.format(c1));
                timesend.setText(parkingTime);
                price.setText(String.valueOf(pric));
                parkingSystem.Output(lice, parkingTime, pric);
                warning.setText("thêm thành công");
                warning.setVisible(true);
                license.setText("");
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }finally {
            if (rls != null){
                rls.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();

            }
        }



    }
    public void cancel(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
