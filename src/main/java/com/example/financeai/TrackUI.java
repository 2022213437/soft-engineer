package com.example.financeai;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TrackUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Track");

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

    static BorderPane createContent(Stage stage) {
        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: white;");

        VBox sidebar = createSidebar(stage);
        rootLayout.setLeft(sidebar);

        VBox mainContent = createMainContent();
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        rootLayout.setCenter(scrollPane);
        return rootLayout;
    }

    private static VBox createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #F8F9FA;");

        HBox topBar = createTopBar();
        VBox trackContent = createTrackContent();

        mainContent.getChildren().addAll(topBar, trackContent);
        return mainContent;
    }

    private static HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        topBar.setPadding(new Insets(0, 0, 20, 0));

        HBox searchContainer = new HBox(10);
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        searchContainer.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        searchContainer.setPadding(new Insets(8, 15, 8, 15));

        ImageView searchIcon = new ImageView(new Image("file:image/search.png"));
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-background-color: transparent; -fx-font-size: 14px; -fx-text-fill: #333333;");

        searchContainer.getChildren().addAll(searchIcon, searchField);

        HBox userInfo = new HBox(15);
        userInfo.setAlignment(Pos.CENTER_RIGHT);

        ImageView notificationIcon = new ImageView(new Image("file:image/notification.png"));
        notificationIcon.setFitHeight(24);
        notificationIcon.setFitWidth(24);

        Label welcomeLabel = new Label("Welcome!");
        welcomeLabel.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        welcomeLabel.setStyle("-fx-text-fill: black;");

        StackPane avatarContainer = new StackPane();
        ImageView userAvatar = new ImageView(new Image("file:image/avatar.png"));
        userAvatar.setFitHeight(40);
        userAvatar.setFitWidth(40);
        avatarContainer.getChildren().add(userAvatar);
        avatarContainer.setStyle("-fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 0);");

        userInfo.getChildren().addAll(notificationIcon, welcomeLabel, avatarContainer);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(searchContainer, spacer, userInfo);
        return topBar;
    }

    private static VBox createTrackContent() {
        VBox trackContent = new VBox(20);
        trackContent.setPadding(new Insets(20));

        Label welcomeTitle = new Label("Track Your Expenses");
        welcomeTitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 26));
        welcomeTitle.setStyle("-fx-text-fill: black;");

        Label subtitle = new Label("Monitor and manage your spending");
        subtitle.setFont(Font.font("Candara", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-text-fill: #666666;");

        VBox.setMargin(subtitle, new Insets(-10, 0, 20, 0));

        HBox expenseCards = createExpenseCards();
        VBox expenseForm = createExpenseForm();
        VBox recentExpenses = createRecentExpenses();

        trackContent.getChildren().addAll(
                welcomeTitle,
                subtitle,
                expenseCards,
                expenseForm,
                recentExpenses
        );

        return trackContent;
    }

    private static HBox createExpenseCards() {
        HBox expenseCards = new HBox(20);
        expenseCards.setAlignment(Pos.CENTER);

        VBox[] cards = {
                createExpenseCard(
                        "Total Expenses",
                        "$2,580.75",
                        "This Month",
                        "file:image/chartHD.png",
                        "#E3F2FD"
                ),
                createExpenseCard(
                        "Budget Left",
                        "$1,419.25",
                        "From $4,000",
                        "file:image/HeartHD.png",
                        "#F3E5F5"
                ),
                createExpenseCard(
                        "Biggest Expense",
                        "Groceries",
                        "$450.50",
                        "file:image/grocery.png",
                        "#E8F5E9"
                )
        };

        expenseCards.getChildren().addAll(cards);
        return expenseCards;
    }

    private static VBox createExpenseCard(String title, String amount, String subtitle, String iconPath, String backgroundColor) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        );
        card.setPrefWidth(250);
        card.setPrefHeight(150);

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(30);
        icon.setFitWidth(30);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        titleLabel.setStyle("-fx-text-fill: black;");

        Label amountLabel = new Label(amount);
        amountLabel.setFont(Font.font("Candara", FontWeight.BOLD, 24));
        amountLabel.setStyle("-fx-text-fill: black;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(Font.font("Candara", FontWeight.NORMAL, 14));
        subtitleLabel.setStyle("-fx-text-fill: #666666;");

        card.getChildren().addAll(icon, titleLabel, amountLabel, subtitleLabel);

        return card;
    }

    private static VBox createExpenseForm() {
        VBox expenseForm = new VBox(15);
        expenseForm.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        );
        expenseForm.setPadding(new Insets(20));

        Label titleLabel = new Label("Add New Expense");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);

        Label amountLabel = new Label("Amount");
        amountLabel.setStyle("-fx-text-fill: black;");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 5;");

        Label categoryLabel = new Label("Category");
        categoryLabel.setStyle("-fx-text-fill: black;");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Groceries", "Transportation", "Entertainment", "Utilities", "Others");
        categoryComboBox.setPromptText("Select category");
        categoryComboBox.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 5;");

        Label dateLabel = new Label("Date");
        dateLabel.setStyle("-fx-text-fill: black;");
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 5;");

        Label notesLabel = new Label("Notes");
        notesLabel.setStyle("-fx-text-fill: black;");
        TextArea notesArea = new TextArea();
        notesArea.setPromptText("Add notes");
        notesArea.setPrefRowCount(3);
        notesArea.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 5;");

        Button submitButton = new Button("Add Expense");
        submitButton.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;"
        );
        submitButton.setPrefWidth(120);

        form.add(amountLabel, 0, 0);
        form.add(amountField, 1, 0);
        form.add(categoryLabel, 0, 1);
        form.add(categoryComboBox, 1, 1);
        form.add(dateLabel, 0, 2);
        form.add(datePicker, 1, 2);
        form.add(notesLabel, 0, 3);
        form.add(notesArea, 1, 3);
        form.add(submitButton, 1, 4);

        GridPane.setHgrow(amountField, Priority.ALWAYS);
        GridPane.setHgrow(categoryComboBox, Priority.ALWAYS);
        GridPane.setHgrow(datePicker, Priority.ALWAYS);
        GridPane.setHgrow(notesArea, Priority.ALWAYS);

        expenseForm.getChildren().addAll(titleLabel, form);

        return expenseForm;
    }

    private static VBox createRecentExpenses() {
        VBox recentExpenses = new VBox(15);
        recentExpenses.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        );
        recentExpenses.setPadding(new Insets(20));

        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setSpacing(20);

        Label titleLabel = new Label("Recent Expenses");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        // Create table
        TableView<ExpenseItem> expenseTable = new TableView<>();
        expenseTable.setStyle("-fx-background-color: white; -fx-border-color: #DCEDC8; -fx-border-width: 1;");

        // Create columns
        TableColumn<ExpenseItem, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(100);

        TableColumn<ExpenseItem, String> titleCol = new TableColumn<>("Description");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(200);

        TableColumn<ExpenseItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(150);

        TableColumn<ExpenseItem, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setPrefWidth(100);

        expenseTable.getColumns().addAll(dateCol, titleCol, categoryCol, amountCol);

        // Add sample data
        ObservableList<ExpenseItem> data = FXCollections.observableArrayList(
            new ExpenseItem("Today", "Grocery Shopping", "Groceries", "-$85.50"),
            new ExpenseItem("Yesterday", "Coffee Shop", "Food & Drinks", "-$4.50"),
            new ExpenseItem("2 days ago", "Electric Bill", "Utilities", "-$75.00"),
            new ExpenseItem("3 days ago", "Movie Tickets", "Entertainment", "-$30.00")
        );
        expenseTable.setItems(data);

        Button generateReportButton = new Button("Generate Report");
        generateReportButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 15;");
        generateReportButton.setOnAction(e -> showReportDialog());

        Button importButton = new Button("Import CSV");
        importButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 15;");
        importButton.setOnAction(e -> importCSVFile(expenseTable));

        Button classificationButton = new Button("Classification");
        classificationButton.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 8 15;");
        classificationButton.setOnAction(e -> toggleClassification(expenseTable));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        titleBar.getChildren().addAll(titleLabel, spacer, importButton, classificationButton, generateReportButton);

        recentExpenses.getChildren().addAll(titleBar, expenseTable);
        return recentExpenses;
    }

    private static void showReportDialog() {
        Stage reportStage = new Stage();
        reportStage.setTitle("Expense Analysis Report");

        VBox reportContent = new VBox(20);
        reportContent.setPadding(new Insets(30));
        reportContent.setStyle("-fx-background-color: white;");

        // Title
        Label titleLabel = new Label("Expense Analysis Report");
        titleLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: black;");

        // Subtitle
        Label subtitleLabel = new Label("Generated on " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        subtitleLabel.setFont(Font.font("Candara", FontWeight.NORMAL, 16));
        subtitleLabel.setStyle("-fx-text-fill: #666666;");

        // Summary Section
        VBox summaryBox = createReportSection("Summary", new String[]{
            "Total Expenses: $2,580.75",
            "Average Daily Expense: $85.50",
            "Highest Spending Category: Groceries",
            "Budget Utilization: 64.5%"
        });

        // Category Analysis
        VBox categoryBox = createReportSection("Category Analysis", new String[]{
            "Groceries: $850.50 (32.9%)",
            "Transportation: $450.00 (17.4%)",
            "Entertainment: $380.00 (14.7%)",
            "Utilities: $750.00 (29.1%)",
            "Others: $150.25 (5.9%)"
        });

        // Trends
        VBox trendsBox = createReportSection("Spending Trends", new String[]{
            "Week 1: $650.25",
            "Week 2: $720.50",
            "Week 3: $680.00",
            "Week 4: $530.00"
        });

        // Recommendations
        VBox recommendationsBox = createReportSection("Recommendations", new String[]{
            "Consider reducing entertainment expenses by 15%",
            "Look for better deals on grocery shopping",
            "Set up automatic bill payments to avoid late fees",
            "Review subscription services monthly"
        });

        reportContent.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            summaryBox,
            categoryBox,
            trendsBox,
            recommendationsBox
        );

        ScrollPane scrollPane = new ScrollPane(reportContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        Scene scene = new Scene(scrollPane, 800, 600);
        reportStage.setScene(scene);
        reportStage.show();
    }

    private static VBox createReportSection(String title, String[] items) {
        VBox section = new VBox(10);
        section.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 10; -fx-padding: 15;");

        Label sectionTitle = new Label(title);
        sectionTitle.setFont(Font.font("Candara", FontWeight.BOLD, 18));
        sectionTitle.setStyle("-fx-text-fill: black;");

        VBox content = new VBox(8);
        for (String item : items) {
            Label itemLabel = new Label(item);
            itemLabel.setFont(Font.font("Candara", 14));
            itemLabel.setStyle("-fx-text-fill: #333333;");
            content.getChildren().add(itemLabel);
        }

        section.getChildren().addAll(sectionTitle, content);
        return section;
    }

    private static void importCSVFile(TableView<ExpenseItem> table) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                ObservableList<ExpenseItem> data = FXCollections.observableArrayList();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                boolean isFirstLine = true;
                
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] values = line.split(",");
                    if (values.length >= 4) {
                        data.add(new ExpenseItem(
                            values[0].trim(),
                            values[1].trim(),
                            values[2].trim(),
                            values[3].trim()
                        ));
                    }
                }
                reader.close();
                table.setItems(data);
            } catch (IOException e) {
                showError("Error", "Failed to import CSV file: " + e.getMessage());
            }
        }
    }

    private static void toggleClassification(TableView<ExpenseItem> table) {
        ObservableList<ExpenseItem> currentItems = table.getItems();
        if (currentItems.isEmpty()) {
            showError("Error", "No data available for classification");
            return;
        }

        // Toggle between classified and unclassified data
        if (currentItems.get(0).getCategory().equals("Unclassified")) {
            // Show classified data
            ObservableList<ExpenseItem> classifiedData = FXCollections.observableArrayList(
                new ExpenseItem("Today", "Grocery Shopping", "Groceries", "-$85.50"),
                new ExpenseItem("Yesterday", "Coffee Shop", "Food & Drinks", "-$4.50"),
                new ExpenseItem("2 days ago", "Electric Bill", "Utilities", "-$75.00"),
                new ExpenseItem("3 days ago", "Movie Tickets", "Entertainment", "-$30.00")
            );
            table.setItems(classifiedData);
        } else {
            // Show unclassified data
            ObservableList<ExpenseItem> unclassifiedData = FXCollections.observableArrayList(
                new ExpenseItem("Today", "Walmart Purchase", "Unclassified", "-$120.50"),
                new ExpenseItem("Yesterday", "Amazon Order", "Unclassified", "-$45.99"),
                new ExpenseItem("2 days ago", "Gas Station", "Unclassified", "-$35.00"),
                new ExpenseItem("3 days ago", "Restaurant", "Unclassified", "-$65.00")
            );
            table.setItems(unclassifiedData);
        }
    }

    private static void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Add ExpenseItem class for table data
    public static class ExpenseItem {
        private final SimpleStringProperty date;
        private final SimpleStringProperty title;
        private final SimpleStringProperty category;
        private final SimpleStringProperty amount;

        public ExpenseItem(String date, String title, String category, String amount) {
            this.date = new SimpleStringProperty(date);
            this.title = new SimpleStringProperty(title);
            this.category = new SimpleStringProperty(category);
            this.amount = new SimpleStringProperty(amount);
        }

        public String getDate() { return date.get(); }
        public String getTitle() { return title.get(); }
        public String getCategory() { return category.get(); }
        public String getAmount() { return amount.get(); }
    }

    private static VBox createSidebar(Stage stage) {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #DEF2CD;");
        sidebar.setPrefWidth(200);

        Label financeTitle = new Label("FinanceAI");
        financeTitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 20));
        financeTitle.setStyle("-fx-text-fill: black;");
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

        trackButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        trackButton.setOnMouseEntered(null);
        trackButton.setOnMouseExited(null);

        homeButton.setOnAction(event -> {
            HomeUI homeUI = new HomeUI();
            Stage homeStage = new Stage();
            homeUI.start(homeStage);
            stage.close();
        });

        transactionButton.setOnAction(event -> {
            TransactionUI transactionUI = new TransactionUI();
            Stage transactionStage = new Stage();
            transactionUI.start(transactionStage);
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

        button.setStyle(defaultStyle);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(defaultStyle));

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}