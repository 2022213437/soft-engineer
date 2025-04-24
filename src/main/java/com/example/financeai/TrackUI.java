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
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

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
                        "file:image/Icon chart.png",
                        "#E3F2FD"
                ),
                createExpenseCard(
                        "Budget Left",
                        "$1,419.25",
                        "From $4,000",
                        "file:image/Icon heart.png",
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

        Label titleLabel = new Label("Recent Expenses");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        VBox expenseList = new VBox(10);
        expenseList.getChildren().addAll(
                createExpenseItem("Grocery Shopping", "-$85.50", "Today", "Groceries", "file:image/grocery.png"),
                createExpenseItem("Coffee Shop", "-$4.50", "Yesterday", "Food & Drinks", "file:image/coffee.png"),
                createExpenseItem("Electric Bill", "-$75.00", "2 days ago", "Utilities", "file:image/utility.png"),
                createExpenseItem("Movie Tickets", "-$30.00", "3 days ago", "Entertainment", "file:image/entertainment.png")
        );

        recentExpenses.getChildren().addAll(titleLabel, expenseList);

        return recentExpenses;
    }

    private static HBox createExpenseItem(String title, String amount, String date, String category, String iconPath) {
        HBox item = new HBox();
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-radius: 10;");

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(30);
        icon.setFitWidth(30);

        VBox details = new VBox(5);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        titleLabel.setStyle("-fx-text-fill: black;");

        Label categoryLabel = new Label(category);
        categoryLabel.setFont(Font.font("Candara", FontWeight.NORMAL, 12));
        categoryLabel.setStyle("-fx-text-fill: #666666;");

        Label dateLabel = new Label(date);
        dateLabel.setFont(Font.font("Candara", FontWeight.NORMAL, 12));
        dateLabel.setStyle("-fx-text-fill: #666666;");

        details.getChildren().addAll(titleLabel, categoryLabel, dateLabel);

        Label amountLabel = new Label(amount);
        amountLabel.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        amountLabel.setStyle("-fx-text-fill: #E57373;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        item.getChildren().addAll(icon, new Region() {{
            setMinWidth(10);
        }}, details, spacer, amountLabel);

        item.setOnMouseEntered(e -> item.setStyle(
                "-fx-background-color: #F8F9FA;" +
                        "-fx-background-radius: 10;"
        ));

        item.setOnMouseExited(e -> item.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 10;"
        ));

        return item;
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