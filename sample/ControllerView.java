package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;



public class ControllerView implements Initializable {

    @FXML
    private TableView<ControllerView> table;
    @FXML
    private TableColumn<ControllerView, String> STTcol;
    @FXML
    private TableColumn<ControllerView, String> licensecol;
    @FXML
    private TableColumn<ControllerView, String> typecol;
    @FXML
    private TableColumn<ControllerView, String> timeincol;
    @FXML
    private TableColumn<ControllerView, String> timeoutcol;
    @FXML
    private TableColumn<ControllerView, String> timesendcol;
    @FXML
    private TableColumn<ControllerView, Double> pricecol;
    @FXML
    private TableColumn<ControllerView, String> statuscol;
    @FXML
    private TableColumn<ControllerView, String> seatcol;
    @FXML
    private TableColumn<ControllerView, String> monthTicketcol;

    private ObservableList<ControllerView> list;

    private List<ControllerView> list1 = new ArrayList();

    private String STT, license, type, timei;
    private String timeo;
    private String times;
    private double price;
    private String status;
    private String monthTicket;
    private String seat;

    public void setMonthTicket(String monthTicket) {
        this.monthTicket = monthTicket;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setSTT(String STT) {
        this.STT = STT;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimei(String timei) {
        this.timei = timei;
    }

    public void setTimeo(String timeo) {
        this.timeo = timeo;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSTT() {
        return STT;
    }

    public String getLicense() {
        return license;
    }

    public String getType() {
        return type;
    }

    public String getTimes() {
        return times;
    }

    public String getStatus() {
        return status;
    }

    public String getTimei() {
        return timei;
    }

    public String getTimeo() {
        return timeo;
    }

    public double getPrice() {
        return price;
    }

    public String getMonthTicket() {
        return monthTicket;
    }

    public String getSeat() {
        return seat;
    }

    public ControllerView(){

    }

    public ControllerView(String STT, String license, String type, String timei, String timeo, String times,
                          double price, String status, String monthTicket, String seat) {
        this.STT = STT;
        this.license = license;
        this.type = type;
        this.timei = timei;
        this.timeo = timeo;
        this.times = times;
        this.price = price;
        this.status = status;
        this.monthTicket = monthTicket;
        this.seat = seat;
    }

    @Override
  public void initialize(URL location, ResourceBundle resourceBundle) {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "root", "");
            PreparedStatement stm = con.prepareStatement("select * from t_parking");
            rs = stm.executeQuery();
        while (rs.next()){
            setSTT(rs.getString("STT"));
            setLicense(rs.getString("bien_so_xe"));
            setType(rs.getString("loai_xe"));
            setTimei(rs.getString("thoi_gian_vao"));
            if (rs.getString("thoi_gian_ra") == null){
                setTimeo("empty");
            }else{
                setTimeo(rs.getString("thoi_gian_ra"));
            }
            if(rs.getString("thoi_gian_gui") == null){
                setTimes("empty");
            }else{
                setTimes(rs.getString("thoi_gian_gui"));
            }
            setPrice(rs.getDouble("gia_tien"));
            if (rs.getInt("status") == 0){
                setStatus("parking");
            }else{
                setStatus("parked");
            }
            setMonthTicket(rs.getString("ve_thang"));
            setSeat(rs.getString("so_cho_ngoi"));

            list1.add(new ControllerView(getSTT(), getLicense(), getType(), getTimei(), getTimeo(), getTimes(),
                     getPrice(), getStatus(), getMonthTicket(), getSeat()));
        }
            list = FXCollections.observableArrayList(list1);
            STTcol.setCellValueFactory(new PropertyValueFactory<>("STT"));
            licensecol.setCellValueFactory(new PropertyValueFactory<>("license"));
            typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
            timeincol.setCellValueFactory(new PropertyValueFactory<>("timei"));
            timeoutcol.setCellValueFactory(new PropertyValueFactory<>("timeo"));
            timesendcol.setCellValueFactory(new PropertyValueFactory<>("times"));
            pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
            statuscol.setCellValueFactory(new PropertyValueFactory<>("status"));
            seatcol.setCellValueFactory(new PropertyValueFactory<>("seat"));
            monthTicketcol.setCellValueFactory(new PropertyValueFactory<>("monthTicket"));
            table.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
    }

    public void in(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("in.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void out(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("out.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ticket(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("monthTicket.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
