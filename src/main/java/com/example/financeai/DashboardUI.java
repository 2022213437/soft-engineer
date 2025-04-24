package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DashboardUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dashboard");

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
        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(15));
        mainContent.setStyle("-fx-background-color: #F8F9FA;");

        HBox topBar = createTopBar();

        HBox contentWrapper = new HBox(20);
        contentWrapper.setPadding(new Insets(5, 0, 0, 0));

        VBox leftContent = new VBox(15);
        leftContent.setPrefWidth(700);

        HBox headerSection = new HBox(20);
        headerSection.setAlignment(Pos.CENTER_LEFT);

        Label dashboardLabel = new Label("Dashboard");
        dashboardLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 26));
        dashboardLabel.setStyle("-fx-text-fill: black;");

        VBox balanceCard = createBalanceCard();
        headerSection.getChildren().addAll(dashboardLabel, balanceCard);

        VBox monthlySummary = createMonthlySummary();

        VBox chartContainer = createChartContainer();

        leftContent.getChildren().addAll(headerSection, monthlySummary, chartContainer);

        VBox recentTransactions = createRecentTransactions();

        contentWrapper.getChildren().addAll(leftContent, recentTransactions);

        mainContent.getChildren().addAll(topBar, contentWrapper);
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
        searchField.setPromptText("Enter keywords to search transactions");
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

    private static VBox createBalanceCard() {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #81C784);" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");
        card.setPrefWidth(250);

        Label titleLabel = new Label("Current balance");
        titleLabel.setFont(Font.font("Candara", 16));
        titleLabel.setStyle("-fx-text-fill: white;");

        Label balanceLabel = new Label("$9,876.33");
        balanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        balanceLabel.setStyle("-fx-text-fill: white;");

        card.getChildren().addAll(titleLabel, balanceLabel);
        return card;
    }

    private static VBox createMonthlySummary() {
        VBox summary = new VBox(15);
        Label titleLabel = new Label("This Month's summary");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: black;");

        HBox cards = new HBox(15);
        cards.getChildren().addAll(
                createSummaryCard("Transaction", "$25,920", "file:image/transaction.png", "linear-gradient(to right, #64B5F6, #90CAF9)"),
                createSummaryCard("Total income", "$20,850", "file:image/income.png", "linear-gradient(to right, #81C784, #A5D6A7)"),
                createSummaryCard("Total expenses", "$10,100", "file:image/expense.png", "linear-gradient(to right, #E57373, #EF9A9A)")
        );

        summary.getChildren().addAll(titleLabel, cards);
        return summary;
    }

    private static VBox createSummaryCard(String title, String amount, String iconPath, String gradient) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: " + gradient + ";" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        card.setPadding(new Insets(15));
        card.setPrefWidth(220);
        card.setAlignment(Pos.CENTER_LEFT);

        HBox iconContainer = new HBox();
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(25);
        icon.setFitWidth(25);
        iconContainer.getChildren().add(icon);
        iconContainer.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 10; -fx-padding: 6;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 14));
        titleLabel.setStyle("-fx-text-fill: white;");

        Label amountLabel = new Label(amount);
        amountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        amountLabel.setStyle("-fx-text-fill: white;");

        card.getChildren().addAll(iconContainer, titleLabel, amountLabel);
        return card;
    }

    private static VBox createChartContainer() {
        VBox container = new VBox(15);
        container.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Weekly Spending Trends");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        HBox chartsContainer = new HBox(15);
        chartsContainer.setAlignment(Pos.CENTER);

        VBox chart2Box = createChartWithLabel(
                "file:image/Chart2.png",
                "Consumption Sector Chart"
        );

        VBox chart3Box = createChartWithLabel(
                "file:image/Chart3.png",
                "Consumption Bar Chart"
        );

        VBox chart4Box = createChartWithLabel(
                "file:image/Chart4.png",
                "Consumption Line Chart"
        );

        chartsContainer.getChildren().addAll(chart2Box, chart3Box, chart4Box);

        container.getChildren().addAll(titleLabel, chartsContainer);
        return container;
    }

    private static VBox createChartWithLabel(String imagePath, String labelText) {
        VBox chartBox = new VBox(10);
        chartBox.setAlignment(Pos.CENTER);

        ImageView chartImage = new ImageView(new Image(imagePath));
        chartImage.setFitWidth(200);
        chartImage.setFitHeight(180);
        chartImage.setPreserveRatio(true);

        Label chartLabel = new Label(labelText);
        chartLabel.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        chartLabel.setStyle("-fx-text-fill: black;");

        chartBox.getChildren().addAll(chartImage, chartLabel);

        return chartBox;
    }

    private static VBox createRecentTransactions() {
        VBox transactions = new VBox(15);
        transactions.setPrefWidth(350);
        transactions.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        transactions.setPadding(new Insets(20));

        Label titleLabel = new Label("Recent Transactions");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        HBox tabBar = new HBox(25);
        tabBar.setPadding(new Insets(0, 0, 15, 0));
        Label allTab = new Label("All");
        Label incomeTab = new Label("Income");
        Label expenseTab = new Label("Expense");

        String tabStyle = "-fx-font-size: 13px; -fx-cursor: hand; -fx-text-fill: black;";
        allTab.setStyle(tabStyle + "-fx-font-weight: bold; -fx-border-color: #4CAF50; -fx-border-width: 0 0 2 0;");
        incomeTab.setStyle(tabStyle);
        expenseTab.setStyle(tabStyle);

        tabBar.getChildren().addAll(allTab, incomeTab, expenseTab);

        Label pendingLabel = new Label("Pending Transactions");
        pendingLabel.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        pendingLabel.setStyle("-fx-text-fill: black;");
        pendingLabel.setPadding(new Insets(5, 0, 5, 0));

        VBox transactionsList = new VBox(10);
        transactionsList.getChildren().addAll(
                createTransactionItem("Financial Overview", "Dining expenses", "-$189.36", "file:image/dining.png"),
                createTransactionItem("Coffee Expense", "Dining expenses", "-$189.36", "file:image/coffee.png"),
                createTransactionItem("Grocery Expenses", "Food delivery costs", "$350.00", "file:image/grocery.png"),
                createTransactionItem("Tech Gadgets", "Electronics expenses", "-$189.36", "file:image/tech.png"),
                createTransactionItem("Pizza Expenses", "Italian cuisine costs", "-$189.36", "file:image/food.png")
        );

        transactions.getChildren().addAll(titleLabel, tabBar, pendingLabel, transactionsList);
        return transactions;
    }

    private static HBox createTransactionItem(String title, String description, String amount, String iconPath) {
        HBox item = new HBox();
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(8));
        item.setSpacing(12);
        item.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 10;");

        StackPane iconContainer = new StackPane();
        iconContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        iconContainer.setPrefSize(40, 40);

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        iconContainer.getChildren().add(icon);

        VBox textContent = new VBox(2);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        titleLabel.setStyle("-fx-text-fill: black;");
        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", 11));
        descLabel.setStyle("-fx-text-fill: #666666;");
        textContent.getChildren().addAll(titleLabel, descLabel);

        Label amountLabel = new Label(amount);
        amountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        if (amount.startsWith("-")) {
            amountLabel.setStyle("-fx-text-fill: #E57373;");
        } else {
            amountLabel.setStyle("-fx-text-fill: #81C784;");
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        item.getChildren().addAll(iconContainer, textContent, spacer, amountLabel);

        item.setOnMouseEntered(e -> item.setStyle("-fx-background-color: #F0F0F0; -fx-background-radius: 10; -fx-cursor: hand;"));
        item.setOnMouseExited(e -> item.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 10;"));

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

        transactionButton.setOnAction(event -> {
            TransactionUI transactionUI = new TransactionUI();
            Stage transactionStage = new Stage();
            transactionUI.start(transactionStage);
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

        dashboardButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        dashboardButton.setOnMouseEntered(null);
        dashboardButton.setOnMouseExited(null);

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
