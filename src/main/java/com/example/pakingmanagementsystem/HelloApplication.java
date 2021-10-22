package com.example.pakingmanagementsystem;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
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
    Scene scene = new Scene(root,1000,500, Color.WHITESMOKE);
    Image icon = new Image("E:/GitHub/Paking-Management-System/src/images/icon.png");
    stage.getIcons().add(icon);
    stage.setTitle("Parking Management System");
    //stage.setWidth(1000);
    //stage.setHeight(500);
    //stage.setResizable(false);
    //stage.setX(50);
    //stage.setY(50);
    //stage.setFullScreen(true);
    //stage.setFullScreenExitHint("You can't escape unless you press p!");
    //stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("q"));

    stage.setScene(scene);
    stage.show();
  }
}
