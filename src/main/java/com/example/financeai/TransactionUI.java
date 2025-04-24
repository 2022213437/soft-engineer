package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TransactionUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        double windowWidth = screenWidth * 0.9;
        double windowHeight = screenHeight * 0.9;

        BorderPane root = createContent(primaryStage);

        Scene scene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static BorderPane createContent(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        HBox titleBar = createCustomTitleBar(stage);
        root.setTop(titleBar);

        HBox mainContent = new HBox(20);
        mainContent.setPadding(new Insets(20, 0, 0, 0));

        VBox leftColumn = createLeftColumn(stage);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);

        VBox rightColumn = createRightColumn();
        rightColumn.setMinWidth(280);
        rightColumn.setMaxWidth(280);

        mainContent.getChildren().addAll(leftColumn, rightColumn);
        root.setCenter(mainContent);

        return root;
    }

    private static HBox createCustomTitleBar(Stage stage) {
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
            DashboardUI dashboardUI = new DashboardUI();
            Stage dashboardStage = new Stage();
            dashboardUI.start(dashboardStage);
            stage.close();
        });
        dashboardLink.setStyle("-fx-text-fill: white;");

        Hyperlink homeLink = new Hyperlink("Home");
        homeLink.setOnAction(event -> {
            HomeUI homeUI = new HomeUI();
            Stage homeStage = new Stage();
            homeUI.start(homeStage);
            stage.close();
        });
        homeLink.setStyle("-fx-text-fill: white;");

        Hyperlink transactionLink = new Hyperlink("Transaction Classification");
        transactionLink.setUnderline(true);
        transactionLink.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Hyperlink settingsLink = new Hyperlink("Settings");
        settingsLink.setStyle("-fx-text-fill: white;");

        settingsLink.setOnAction(event -> {
            stage.getScene().setRoot(SettingsUI.createContent(stage));
        });

        navLinks.getChildren().addAll(dashboardLink, homeLink, transactionLink, settingsLink);

        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: #812F33; -fx-text-fill: white; -fx-font-weight: bold;");
        closeButton.setOnAction(e -> stage.close());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        titleBar.setOnMousePressed((MouseEvent event) -> {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double[] offsets = new double[2];
            offsets[0] = event.getSceneX();
            offsets[1] = event.getSceneY();
            titleBar.setUserData(offsets);
        });

        titleBar.setOnMouseDragged((MouseEvent event) -> {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double[] offsets = (double[]) titleBar.getUserData();
            if (offsets != null) {
                currentStage.setX(event.getScreenX() - offsets[0]);
                currentStage.setY(event.getSceneY() - offsets[1]);
            }
        });


        titleBar.getChildren().addAll(titleLabel, spacer, navLinks, closeButton);
        return titleBar;
    }

    private static BorderPane createHeader() {
        BorderPane headerPane = new BorderPane();
        headerPane.setPadding(new Insets(0, 0, 15, 0));
        headerPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));
        return headerPane;
    }

    private static VBox createLeftColumn(Stage stage) {
        VBox leftVBox = new VBox(15);

        BorderPane titlePane = new BorderPane();
        Label transactionTitle = new Label("Transaction Classification");
        transactionTitle.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        Button importButton = new Button("Import CSV");
        importButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold;");
        titlePane.setLeft(transactionTitle);
        titlePane.setRight(importButton);

        importButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择 CSV 文件导入");

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV 文件 (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            java.io.File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                System.out.println("已选择文件: " + file.getAbsolutePath());
            } else {
                System.out.println("文件选择已取消。");
            }
        });

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

    private static HBox createTransactionRow(String descriptionLine, String amountLine) {
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
        confirmButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white;");
        Button editButton = new Button("Edit");
        editButton.setFont(Font.font("Candara", FontWeight.BOLD, 12));

        row.getChildren().addAll(details, spacer, confirmButton, editButton);
        return row;
    }

    private static VBox createRightColumn() {
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

    private static VBox createManageCategoriesBox() {
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

    private static VBox createAddTransactionBox() {
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