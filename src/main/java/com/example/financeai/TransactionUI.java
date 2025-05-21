package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        primaryStage.setTitle("Transaction");

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        double windowWidth = screenWidth * 0.9;
        double windowHeight = screenHeight * 0.9;

        BorderPane rootLayout = createContent(primaryStage);
        Scene scene = new Scene(rootLayout, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static BorderPane createContent(Stage stage) {
        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: white;");

        VBox sidebar = createSidebar(stage);
        rootLayout.setLeft(sidebar);

        VBox mainContent = createMainContent(stage);
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        rootLayout.setCenter(scrollPane);
        return rootLayout;
    }

    private static VBox createMainContent(Stage stage) {
        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #F8F9FA;");

        HBox topBar = createTopBar();
        mainContent.getChildren().add(topBar);

        HBox contentWrapper = new HBox(30);
        contentWrapper.setPadding(new Insets(20, 0, 0, 0));

        VBox leftContent = createLeftColumn(stage);
        HBox.setHgrow(leftContent, Priority.ALWAYS);

        VBox rightContent = createRightColumn();
        rightContent.setPrefWidth(400);

        contentWrapper.getChildren().addAll(leftContent, rightContent);
        mainContent.getChildren().add(contentWrapper);

        return mainContent;
    }

    private static HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        topBar.setPadding(new Insets(0, 0, 20, 0));

        Label titleLabel = new Label("Transaction Management");
        titleLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 26));
        titleLabel.setStyle("-fx-text-fill: black;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(titleLabel, spacer);
        return topBar;
    }

    private static VBox createLeftColumn(Stage stage) {
        VBox leftVBox = new VBox(15);
        leftVBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        leftVBox.setPadding(new Insets(20));

        BorderPane titlePane = new BorderPane();
        Label transactionTitle = new Label("Transaction List");
        transactionTitle.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        transactionTitle.setStyle("-fx-text-fill: black");
        Button importButton = new Button("Import CSV");
        importButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
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
        rightVBox.setPadding(new Insets(20));
        rightVBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

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
        title.setFont(Font.font("Arial Black", FontWeight.BOLD, 16));
        title.setStyle("-fx-text-fill: black");
        
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
        title.setStyle("-fx-text-fill: black");
        
        ListView<String> categoryList = new ListView<>();
        categoryList.getItems().addAll("Food & Beverage", "Shopping", "Transport", "Entertainment", "Other");
        categoryList.setPrefHeight(150);
        
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        
        addButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: black;");
        editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: black;");
        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: black;");
        
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton);
        
        box.getChildren().addAll(title, categoryList, buttonBox);
        return box;
    }

    private static VBox createQuickAddBox() {
        VBox box = new VBox(10);
        
        Label title = new Label("Quick Add Transaction");
        title.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        title.setStyle("-fx-text-fill: black");
        
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
        addButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: black; -fx-font-weight: bold;");
        
        box.getChildren().addAll(title, datePicker, descField, amountField, categoryCombo, addButton);
        return box;
    }

    private static VBox createSidebar(Stage stage) {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #DEF2CD;");
        sidebar.setPrefWidth(200);

        Label financeTitle = new Label("FinanceAI");
        financeTitle.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        financeTitle.setStyle("-fx-text-fill: black");
        VBox.setMargin(financeTitle, new Insets(0, 0, 20, 0));

        Button homeButton = createSidebarButton("Home", "file:image/Icon home.png", 18);
        Button trackButton = createSidebarButton("Track", "file:image/Icon search.png", 18);
        Button transactionButton = createSidebarButton("Transaction", "file:image/Icon book open.png", 18);
        Button dashboardButton = createSidebarButton("Dashboard", "file:image/dashboard.png", 18);
        Button budgetButton = createSidebarButton("Budget", "file:image/Icon heart.png", 18);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button settingsButton = createSidebarButton("Settings", "file:image/Icon gear.png", 18);
        Button logoutButton = createSidebarButton("Log out", "file:image/Icon right from bracket.png", 18);

        transactionButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        transactionButton.setOnMouseEntered(null);
        transactionButton.setOnMouseExited(null);

        homeButton.setOnAction(event -> {
            HomeUI homeUI = new HomeUI();
            Stage homeStage = new Stage();
            homeUI.start(homeStage);
            stage.close();
        });

        trackButton.setOnAction(event -> {
            TrackUI trackUI = new TrackUI();
            Stage trackStage = new Stage();
            trackUI.start(trackStage);
            stage.close();
        });

        dashboardButton.setOnAction(event -> {
            DashboardUI dashboardUI = new DashboardUI();
            Stage dashboardStage = new Stage();
            dashboardUI.start(dashboardStage);
            stage.close();
        });

        budgetButton.setOnAction(event -> {
            BudgetUI budgetUI = new BudgetUI();
            Stage budgetStage = new Stage();
            budgetUI.start(budgetStage);
            stage.close();
        });

        settingsButton.setOnAction(event -> {
            SettingsUI settingsUI = new SettingsUI();
            Stage settingsStage = new Stage();
            settingsUI.start(settingsStage);
            stage.close();
        });

        logoutButton.setOnAction(event -> {
            LoginUI loginUI = new LoginUI();
            Stage loginStage = new Stage();
            loginUI.start(loginStage);
            stage.close();
        });

        sidebar.getChildren().addAll(
                financeTitle,
                homeButton,
                trackButton,
                transactionButton,
                dashboardButton,
                budgetButton,
                spacer,
                settingsButton,
                logoutButton
        );

        return sidebar;
    }

    private static Button createSidebarButton(String text, String imagePath, double iconSize) {
        Button button = new Button();
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);

        Font buttonFont = Font.font("Arial", 16);

        ImageView icon = new ImageView(new Image(imagePath));
        icon.setFitHeight(iconSize);
        icon.setFitWidth(iconSize);
        icon.setPreserveRatio(true);

        HBox buttonContent = new HBox(10);
        buttonContent.setAlignment(Pos.CENTER_LEFT);

        Label buttonLabel = new Label(text);
        buttonLabel.setFont(buttonFont);
        buttonLabel.setStyle("-fx-text-fill: black;");

        buttonContent.getChildren().addAll(icon, buttonLabel);

        button.setGraphic(buttonContent);
        button.setText("");
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        String defaultStyle = "-fx-background-color: transparent; -fx-text-fill: black;";
        String hoverStyle = "-fx-background-color: #C8E6C9; -fx-text-fill: black;";
        String activeStyle = "-fx-background-color: white; -fx-text-fill: black;";

        button.setStyle(defaultStyle);
        button.setOnMouseEntered(e -> {
            if (!button.getStyle().equals(activeStyle)) {
                button.setStyle(hoverStyle);
            }
        });
        button.setOnMouseExited(e -> {
            if (!button.getStyle().equals(activeStyle)) {
                button.setStyle(defaultStyle);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}