package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FinanceAIApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FinanceAI");

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        double windowWidth = screenWidth * 0.8;
        double windowHeight = screenHeight * 0.8;

        VBox leftPane = new VBox(20);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setStyle("-fx-background-color: #689842;");

        Image robotImage = new Image("file:image/robot.png");
        ImageView robotImageView = new ImageView(robotImage);
        robotImageView.setFitWidth(200);
        robotImageView.setPreserveRatio(true);

        Label titleLabel = new Label("FinanceAI helps you manage your money");
        titleLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: white;");

        Label descriptionLabel = new Label("Track all your expenses and savings effortlessly.");
        descriptionLabel.setFont(Font.font("HP Simplified", 16));
        descriptionLabel.setStyle("-fx-text-fill: white;");

        leftPane.getChildren().addAll(robotImageView, titleLabel, descriptionLabel);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 30, 30, 30));

        Label title = new Label("Log in");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        grid.add(title, 0, 0, 2, 1);

        Label savingsLabel = new Label("Set Savings Goals");
        savingsLabel.setFont(Font.font("Candara", 18));
        savingsLabel.setPadding(new Insets(5, 0, 0, 0)); // 添加上边距
        grid.add(savingsLabel, 0, 1);

        TextField savingsTextField = new TextField();
        savingsTextField.setPromptText("Enter your transaction data here");
        savingsTextField.setPrefWidth(300);
        savingsTextField.setPrefHeight(40);
        savingsTextField.setFont(Font.font("Candara", 14));
        grid.add(savingsTextField, 0, 2, 2, 1);

        Label pwLabel = new Label("Password");
        pwLabel.setFont(Font.font("Candara", 18));
        grid.add(pwLabel, 0, 3);

        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Enter your password here");
        pwBox.setPrefWidth(300);
        pwBox.setPrefHeight(40);
        pwBox.setFont(Font.font("Candara", 14));
        grid.add(pwBox, 0, 4, 2, 1);

        Button loginButton = new Button("Log in");
        loginButton.setPrefWidth(300);
        loginButton.setPrefHeight(40);
        loginButton.setFont(Font.font("Candara", 14));
        grid.add(loginButton, 0, 5, 2, 1);

        loginButton.setOnAction(event -> {
            Stage stage = new Stage();
            ExpenseOverviewApp expenseOverviewApp = new ExpenseOverviewApp();
            expenseOverviewApp.start(stage);
            primaryStage.close();
        });

        Hyperlink forgotPwLink = new Hyperlink("Forgot your password?");
        javafx.scene.layout.GridPane.setHalignment(forgotPwLink, javafx.geometry.HPos.LEFT);
        forgotPwLink.setFont(Font.font("Candara", 14));
        grid.add(forgotPwLink, 0, 6, 2, 1);

        HBox content = new HBox();
        content.getChildren().addAll(leftPane, grid);

        leftPane.prefWidthProperty().bind(content.widthProperty().multiply(0.45));
        grid.prefWidthProperty().bind(content.widthProperty().multiply(0.55));

        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(grid, Priority.ALWAYS);

        Label topRightTitle = new Label("FinanceAI");
        topRightTitle.setFont(Font.font("Arial Black", FontWeight.NORMAL, 20));

        BorderPane root = new BorderPane();
        root.setCenter(content);
        HBox topBox = new HBox(topRightTitle);
        topBox.setAlignment(Pos.TOP_RIGHT);
        root.setTop(topBox);

        BorderPane.setAlignment(topRightTitle, Pos.TOP_RIGHT);
        BorderPane.setMargin(topRightTitle, new Insets(10, 10, 0, 0));

        Scene scene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}