package com.example.pakingmanagementsystem;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    Scene scene = new Scene(root, Color.rgb(200,60,185));
    stage.setTitle("Parking Management System");
    stage.setScene(scene);
    stage.show();
  }
}