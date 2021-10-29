package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerMonthTicket implements Initializable {
    parkingmanagersystem parkingSystem = new parkingmanagersystem();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
    String[] typ = {"xe đạp(xe đạp điện)", "xe máy(xe máy điện)", "ô tô"};
    @FXML
    private TextField license;
    @FXML
    private Spinner<Integer> seat;
    @FXML
    private TextField expiration;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private Label warning;
    @FXML
    private Label label;
    String licen;
    String type_;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.getItems().addAll(typ);
        type.setOnAction(this::setSeat);
        expiration.setText(dateFormat.format(new Date()));
        warning.setVisible(false);
    }

    public void setSeat(ActionEvent event){
        if (type.getValue().equals("ô tô")){
            seat.setVisible(true);
            label.setVisible(true);
        }else{
            seat.setVisible(false);
            label.setVisible(false);
        }
    }

    public void add(ActionEvent event){
        Calendar expiration_ =  Calendar.getInstance();
        if (type.getValue().equals("ô tô")) {
            if (seat.getValue() >= 9) {
                warning.setText("vé tháng chỉ hỗ trợ cho xe ô tô có từ 4 đến 8 chỗ");
                warning.setVisible(true);
                return;
            }
        }
        type_ = type.getValue();
        licen = license.getText();
        expiration_.roll(Calendar.MONTH, 1);
        parkingSystem.monthAdd(licen, type_, expiration_);
        String warning_ = "thêm thành công";
        if (type.getValue().equals("xe đạp(xe đạp điện)")){
            warning_ = warning_ + "giá tiền: 100.000";
        }else if(type.getValue().equals("xe máy(xe máy điện)")){
            warning_ = warning_ + "giá tiền: 200.000";
        }else {
            warning_ = warning_ + "giá tiền: 16.000.000";
        }
        warning.setText(warning_);
        warning.setVisible(true);
        license.setText("");
        expiration.setText("");
        warning.setText("");
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
