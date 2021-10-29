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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerIn implements Initializable {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
    parkingmanagersystem ParkingSystem = new parkingmanagersystem();
    String[] vehicleType = {"xe đạp(xe đạp điện)", "xe máy(xe máy điện)","ô tô"} ;

    @FXML
    private TextField license;
    @FXML
    private TextField timein;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private Spinner<Integer> seat;
    @FXML
    private Label label;
    @FXML
    private Label warning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seat.setEditable(false);
        warning.setVisible(false);
        type.getItems().addAll(vehicleType);
        Date c1 = new Date();
        timein.setText(dateFormat.format(c1));
        type.setOnAction(this::car);
        try {
            ParkingSystem.updateMonthTicket();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void add(ActionEvent event){
        int seat_ = 0;
        String licen = license.getText();
        String choiceBox = type.getValue();
        if(choiceBox.equals("ô tô")){
            seat_ = seat.getValue();
        }
        ParkingSystem.Input(licen,seat_, choiceBox);
        warning.setText("thêm thành công");
        warning.setVisible(true);
        license.setText("");
        timein.setText("");
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

    public void car(ActionEvent event){
        if (type.getValue().equals("ô tô")){
            seat.setVisible(true);
            label.setVisible(true);
            seat.setEditable(true);
        }else{
            seat.setVisible(false);
            label.setVisible(false);
        }
    }

}
