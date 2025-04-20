package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FinanceTrackerUI extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        HBox titleBar = createCustomTitleBar(primaryStage);
        root.setTop(titleBar);

        HBox mainContent = new HBox(20);
        mainContent.setPadding(new Insets(20, 0, 0, 0));

        VBox leftColumn = createLeftColumn();
        HBox.setHgrow(leftColumn, Priority.ALWAYS);

        VBox rightColumn = createRightColumn();
        rightColumn.setMinWidth(280);
        rightColumn.setMaxWidth(280);

        mainContent.getChildren().addAll(leftColumn, rightColumn);
        root.setCenter(mainContent);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createCustomTitleBar(Stage stage) {
        HBox titleBar = new HBox(20);
        titleBar.setStyle("-fx-background-color: #333; -fx-padding: 10;");
        titleBar.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("FinanceAI Tracker");
        titleLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: white;");

        HBox navLinks = new HBox(20);
        navLinks.setAlignment(Pos.CENTER_RIGHT);

        Hyperlink dashboardLink = new Hyperlink("Dashboard");
        dashboardLink.setOnAction(event -> {
            stage.close();

            ExpenseOverviewApp expenseOverviewApp = new ExpenseOverviewApp();
            Stage newStage = new Stage();
            try {
                expenseOverviewApp.start(newStage);
            } catch (Exception e) {
                System.err.println("Error opening ExpenseOverviewApp window:");
                e.printStackTrace();
            }
        });
        dashboardLink.setStyle("-fx-text-fill: white;");

        Hyperlink transactionLink = new Hyperlink("Transaction Classification");
        transactionLink.setUnderline(true);
        transactionLink.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Hyperlink settingsLink = new Hyperlink("Settings");
        settingsLink.setStyle("-fx-text-fill: white;");

        settingsLink.setOnAction(event -> {
            stage.close();

            SettingsUI settingsUI = new SettingsUI();
            Stage newStage = new Stage();
            try {
                settingsUI.start(newStage);
            } catch (Exception e) {
                System.err.println("Error opening SettingsUI window:");
                e.printStackTrace();
            }
        });

        navLinks.getChildren().addAll(dashboardLink, transactionLink, settingsLink);

        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: #812F33; -fx-text-fill: white; -fx-font-weight: bold;");
        closeButton.setOnAction(e -> stage.close());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        titleBar.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        titleBar.getChildren().addAll(titleLabel, spacer, navLinks, closeButton);
        return titleBar;
    }

    private BorderPane createHeader() {
        BorderPane headerPane = new BorderPane();
        headerPane.setPadding(new Insets(0, 0, 15, 0));
        headerPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0)))); // Bottom border
        return headerPane;
    }

    private VBox createLeftColumn() {
        VBox leftVBox = new VBox(15);

        BorderPane titlePane = new BorderPane();
        Label transactionTitle = new Label("Transaction Classification");
        transactionTitle.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        Button importButton = new Button("Import CSV");
        importButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold;"); // Green button
        titlePane.setLeft(transactionTitle);
        titlePane.setRight(importButton);

        Label transactionsLabel = new Label("Transactions");
        transactionsLabel.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        transactionsLabel.setPadding(new Insets(0, 0, 0, 5));

        VBox transactionsList = new VBox(10);
        transactionsList.setStyle("-fx-background-color: #F1F8E9; " +
                "-fx-padding: 15; " +
                "-fx-border-color: #DCEDC8; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 5;");
        transactionsList.getChildren().addAll(
                createTransactionRow("12/10/2023 - Starbucks Coffee", "$5.00 - Suggested: Food & Beverage"),
                createTransactionRow("11/10/2023 - Amazon Purchase", "$120.00 - Suggested: Shopping"),
                createTransactionRow("10/10/2023 - Uber Ride", "$15.00 - Suggested: Transport")
        );

        leftVBox.getChildren().addAll(titlePane, transactionsLabel, transactionsList);
        return leftVBox;
    }

    private HBox createTransactionRow(String descriptionLine, String amountLine) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.setStyle("-fx-background-color: white; " +
                "-fx-border-color: #E0E0E0; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 3;");

        VBox details = new VBox(2);
        Label descLabel = new Label(descriptionLine);
        descLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        Label amountLabel = new Label(amountLine);
        amountLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        amountLabel.setTextFill(Color.GRAY);
        details.getChildren().addAll(descLabel, amountLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button confirmButton = new Button("Confirm");
        confirmButton.setFont(Font.font("Candara", FontWeight.BOLD, 12));
        confirmButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white;"); // Green button
        Button editButton = new Button("Edit");
        editButton.setFont(Font.font("Candara", FontWeight.BOLD, 12));

        row.getChildren().addAll(details, spacer, confirmButton, editButton);
        return row;
    }

    private VBox createRightColumn() {
        VBox rightVBox = new VBox(20);
        rightVBox.setPadding(new Insets(10));
        rightVBox.setStyle("-fx-background-color: #DEf2CD; " +
                "-fx-border-radius: 5;");

        rightVBox.getChildren().addAll(
                createManageCategoriesBox(),
                createAddTransactionBox()
        );

        return rightVBox;
    }

    private VBox createManageCategoriesBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        Label title = new Label("Manage Categories");
        title.setFont(Font.font("Candara", FontWeight.BOLD, 14));

        VBox categoryList = new VBox(5);
        categoryList.getChildren().addAll(
                new Label("Food & Beverage"),
                new Label("Shopping"),
                new Label("Transport"),
                new Label("Utilities")
        );

        box.getChildren().addAll(title, categoryList);
        return box;
    }

    private VBox createAddTransactionBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: #DEf2CD; " +
                "-fx-border-width: 1 0 0 0;");

        Label title = new Label("Add Transaction");
        title.setFont(Font.font("Candara", FontWeight.BOLD, 14));

        Label dateLabel = new Label("Date");
        dateLabel.setFont(Font.font("HP Simplified"));
        TextField dateField = new TextField();

        Label descLabel = new Label("Description");
        descLabel.setFont(Font.font("HP Simplified"));
        TextField descField = new TextField();

        Label idrLabel = new Label("ID");
        idrLabel.setFont(Font.font("HP Simplified"));
        TextField idrField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Candara"));
        submitButton.setMaxWidth(Double.MAX_VALUE);
        submitButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold;");

        box.getChildren().addAll(title, dateLabel, dateField, descLabel, descField, idrLabel, idrField, submitButton);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}