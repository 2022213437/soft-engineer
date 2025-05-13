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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionUI extends Application {
    private TableView<Transaction> transactionTable;
    private ObservableList<Transaction> transactionData = FXCollections.observableArrayList();
    private ComboBox<String> categoryFilter;
    
    // Transaction类用于存储交易数据
    public static class Transaction {
        private final String date;
        private final String description;
        private final double amount;
        private String category;
        
        public Transaction(String date, String description, double amount, String category) {
            this.date = date;
            this.description = description;
            this.amount = amount;
            this.category = category;
        }
        
        // Getters
        public String getDate() { return date; }
        public String getDescription() { return description; }
        public double getAmount() { return amount; }
        public String getCategory() { return category; }
        
        // Setter for category
        public void setCategory(String category) { this.category = category; }
    }

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
        Label transactionTitle = new Label("Transaction Management");
        transactionTitle.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        Button importButton = new Button("Import CSV");
        importButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold;");
        titlePane.setLeft(transactionTitle);
        titlePane.setRight(importButton);

        // Create table
        TableView<Transaction> transactionTable = new TableView<>();
        transactionTable.setStyle("-fx-background-color: white; -fx-border-color: #DCEDC8; -fx-border-width: 1;");

        // Create columns
        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(100);

        TableColumn<Transaction, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(200);

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setPrefWidth(100);

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(150);

        transactionTable.getColumns().addAll(dateCol, descCol, amountCol, categoryCol);

        // Add category filter
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        Label filterLabel = new Label("Filter by Category:");
        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().addAll("All", "Food & Beverage", "Shopping", "Transport", "Entertainment", "Other");
        categoryFilter.setValue("All");
        filterBox.getChildren().addAll(filterLabel, categoryFilter);

        importButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select CSV File");

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            java.io.File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    loadCSVData(file, transactionTable);
                } catch (IOException e) {
                    showError("File Read Error", "Unable to read CSV file: " + e.getMessage());
                }
            }
        });

        leftVBox.getChildren().addAll(titlePane, filterBox, transactionTable);
        return leftVBox;
    }

    private static void loadCSVData(java.io.File file, TableView<Transaction> table) throws IOException {
        ObservableList<Transaction> data = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String date = values[0].trim();
                    String description = values[1].trim();
                    double amount = Double.parseDouble(values[2].trim());
                    String category = values.length > 3 ? values[3].trim() : "Uncategorized";
                    data.add(new Transaction(date, description, amount, category));
                }
            }
        }
        table.setItems(data);
    }

    private static void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static VBox createRightColumn() {
        VBox rightVBox = new VBox(20);
        rightVBox.setPadding(new Insets(15));
        rightVBox.setStyle("-fx-background-color: #F5F5F5; " +
                "-fx-border-color: #E0E0E0; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 5;");

        // Add statistics panel
        VBox statsBox = createStatsBox();
        statsBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5;");

        // Add category management panel
        VBox categoryBox = createCategoryManagementBox();
        categoryBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5;");

        // Add quick add transaction panel
        VBox quickAddBox = createQuickAddBox();
        quickAddBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5;");

        rightVBox.getChildren().addAll(statsBox, categoryBox, quickAddBox);
        return rightVBox;
    }

    private static VBox createStatsBox() {
        VBox box = new VBox(10);
        
        Label title = new Label("Statistics Overview");
        title.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(10);
        statsGrid.setVgap(10);
        
        // Add statistics items
        addStatItem(statsGrid, 0, "Total Transactions", "0");
        addStatItem(statsGrid, 1, "Monthly Expenses", "$0.00");
        addStatItem(statsGrid, 2, "Average Transaction", "$0.00");
        addStatItem(statsGrid, 3, "Largest Expense", "$0.00");
        
        box.getChildren().addAll(title, statsGrid);
        return box;
    }

    private static void addStatItem(GridPane grid, int row, String label, String value) {
        Label statLabel = new Label(label);
        statLabel.setStyle("-fx-text-fill: #666666;");
        Label statValue = new Label(value);
        statValue.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        
        grid.add(statLabel, 0, row);
        grid.add(statValue, 1, row);
    }

    private static VBox createCategoryManagementBox() {
        VBox box = new VBox(10);
        
        Label title = new Label("Category Management");
        title.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        
        ListView<String> categoryList = new ListView<>();
        categoryList.getItems().addAll("Food & Beverage", "Shopping", "Transport", "Entertainment", "Other");
        categoryList.setPrefHeight(150);
        
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        
        addButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white;");
        editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton);
        
        box.getChildren().addAll(title, categoryList, buttonBox);
        return box;
    }

    private static VBox createQuickAddBox() {
        VBox box = new VBox(10);
        
        Label title = new Label("Quick Add Transaction");
        title.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");
        
        TextField descField = new TextField();
        descField.setPromptText("Transaction Description");
        
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        
        ComboBox<String> categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll("Food & Beverage", "Shopping", "Transport", "Entertainment", "Other");
        categoryCombo.setPromptText("Select Category");
        
        Button addButton = new Button("Add Transaction");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold;");
        
        box.getChildren().addAll(title, datePicker, descField, amountField, categoryCombo, addButton);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}