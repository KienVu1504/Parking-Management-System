package com.example.pakingmanagementsystem;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    //Stage stage = new Stage();
    Group root = new Group();
    Scene scene = new Scene(root, Color.WHITESMOKE);
    Image icon = new Image("images/icon.jpg");
    stage.getIcons().add(icon);
    stage.setTitle("Parking Management System");
    stage.setScene(scene);
    stage.show();
  }
}