package com.example.pakingmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HelloApplication extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    /*
    Stage stage = new Stage();
    Group root = new Group();
    Scene scene = new Scene(root,1000,500, Color.WHITESMOKE);
    Image icon = new Image("E:/GitHub/Paking-Management-System/src/images/icon.png");

    Image image =new Image("E:/GitHub/Paking-Management-System/src/images/pizza.png");
    ImageView imageView = new ImageView(image);
    imageView.setX(600);
    imageView.setY(200);
    imageView.setFitWidth(100.0);
    imageView.setFitHeight(80.0);

    stage.getIcons().add(icon);
    stage.setTitle("Parking Management System");
    stage.setWidth(1000);
    stage.setHeight(500);
    stage.setResizable(false);
    stage.setX(50);
    stage.setY(50);
    stage.setFullScreen(true);
    stage.setFullScreenExitHint("You can't escape unless you press p!");
    stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("q"));

    Text text = new Text();
    text.setText("WELCOME!");
    text.setX(50);
    text.setY(50);
    text.setFont(Font.font("Verdana",50));
    text.setFill(Color.LIMEGREEN);

    Line line = new Line();
    line.setStartX(200);
    line.setStartY(200);
    line.setEndX(500);
    line.setEndY(200);
    line.setStrokeWidth(5);
    line.setStroke(Color.RED);
    line.setOpacity(0.5);
    line.setRotate(45);

    Rectangle rectangle = new Rectangle();
    rectangle.setX(100);
    rectangle.setY(100);
    rectangle.setHeight(100);
    rectangle.setWidth(100);
    rectangle.setFill(Color.BLUE);
    rectangle.setStrokeWidth(5);
    rectangle.setStroke(Color.BLACK);

    Polygon triangle = new Polygon();
    triangle.getPoints().setAll(
      200.0,200.0,
      300.0,300.0,
      200.0,300.0
    );
    triangle.setFill(Color.PINK);

    Circle circle = new Circle();
    circle.setCenterX(350);
    circle.setCenterY(350);
    circle.setRadius(50);
    circle.setFill(Color.ORANGE);

    root.getChildren().add(text);
    root.getChildren().add(line);
    root.getChildren().add(rectangle);
    root.getChildren().add(triangle);
    root.getChildren().add(circle);
    root.getChildren().add(imageView);

    stage.setScene(scene);
    stage.show();
    */
    try {
      Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
      stage.setTitle("HELLO!");
      //stage.setScene(new Scene(root, 1000, 500));
      Scene scene = new Scene(root);
      //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      try {
        String css = this.getClass().getResource("/application.css").toExternalForm();
        scene.getStylesheets().add(css);
      }catch (Exception e){
        e.printStackTrace();
      }
      stage.setScene(scene);
      stage.show();
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}
