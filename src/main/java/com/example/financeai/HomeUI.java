package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;


public class HomeUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Home");

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

    private static BorderPane createContent(Stage stage) {
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
        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #F8F9FA;");

        HBox topBar = createTopBar();
        mainContent.getChildren().add(topBar);

        // Create main features section
        HBox featuresBox = new HBox(30);
        featuresBox.setAlignment(Pos.CENTER);

        // Track Expenses Card
        VBox trackCard = createFeatureCard(
            "Track Expenses",
            "Monitor your daily expenses and track your spending patterns",
            "file:image/Icon search.png",
            "#E3F2FD"
        );
        trackCard.setOnMouseClicked(e -> {
            TrackUI trackUI = new TrackUI();
            Stage trackStage = new Stage();
            trackUI.start(trackStage);
            ((Stage) trackCard.getScene().getWindow()).close();
        });

        // View Dashboard Card
        VBox dashboardCard = createFeatureCard(
            "View Dashboard",
            "Get insights into your financial health with interactive charts",
            "file:image/dashboard.png",
            "#F3E5F5"
        );
        dashboardCard.setOnMouseClicked(e -> {
            DashboardUI dashboardUI = new DashboardUI();
            Stage dashboardStage = new Stage();
            dashboardUI.start(dashboardStage);
            ((Stage) dashboardCard.getScene().getWindow()).close();
        });

        // Manage Budget Card
        VBox budgetCard = createFeatureCard(
            "Manage Budget",
            "Set and manage your budget goals with smart recommendations",
            "file:image/Icon heart.png",
            "#E8F5E9"
        );
        budgetCard.setOnMouseClicked(e -> {
            BudgetUI budgetUI = new BudgetUI();
            Stage budgetStage = new Stage();
            budgetUI.start(budgetStage);
            ((Stage) budgetCard.getScene().getWindow()).close();
        });

        featuresBox.getChildren().addAll(trackCard, dashboardCard, budgetCard);
        mainContent.getChildren().add(featuresBox);

        // Add Track Statistics Section
        VBox trackStats = createTrackStatistics();
        mainContent.getChildren().add(trackStats);

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

    private static VBox createFeatureCard(String title, String description, String iconPath, String backgroundColor) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: " + backgroundColor + ";" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        );
        card.setPrefWidth(300);
        card.setPrefHeight(200);

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(40);
        icon.setFitWidth(40);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Candara", 14));
        descLabel.setStyle("-fx-text-fill: #666666;");
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);

        card.getChildren().addAll(icon, titleLabel, descLabel);

        // Add hover effect
        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: " + backgroundColor + ";" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 6);"
        ));

        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: " + backgroundColor + ";" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        ));

        return card;
    }

    private static VBox createQuickActions() {
        VBox quickActions = new VBox(15);
        quickActions.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        );
        quickActions.setPadding(new Insets(20));

        Label titleLabel = new Label("Quick Actions");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER_LEFT);

        Button[] buttons = {
                createActionButton("Add Transaction", "file:image/PlusHD.png"),
                createActionButton("View Reports", "file:image/ChartHD2.png"),
                createActionButton("Set Budget", "file:image/HeartHD.png"),
                createActionButton("View Goals", "file:image/TargetHD.png")
        };

        actionButtons.getChildren().addAll(buttons);
        quickActions.getChildren().addAll(titleLabel, actionButtons);

        return quickActions;
    }

    private static Button createActionButton(String text, String iconPath) {
        Button button = new Button();
        button.setStyle(
                "-fx-background-color: #F8F9FA;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 15;"
        );

        HBox content = new HBox(10);
        content.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(20);
        icon.setFitWidth(20);

        Label label = new Label(text);
        label.setFont(Font.font("Candara", FontWeight.NORMAL, 14));
        label.setStyle("-fx-text-fill: black;");

        content.getChildren().addAll(icon, label);
        button.setGraphic(content);

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #EEEEEE;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 15;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #F8F9FA;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 15;"
        ));

        return button;
    }

    private static VBox createRecentActivity() {
        VBox recentActivity = new VBox(15);
        recentActivity.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        );
        recentActivity.setPadding(new Insets(20));

        Label titleLabel = new Label("Recent Activity");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        VBox activityList = new VBox(10);
        activityList.getChildren().addAll(
                createActivityItem("Grocery Shopping", "-$85.50", "Today", "file:image/grocery.png"),
                createActivityItem("Salary Deposit", "+$3,500.00", "Yesterday", "file:image/salary.png"),
                createActivityItem("Coffee Shop", "-$4.50", "Yesterday", "file:image/coffee.png"),
                createActivityItem("Electric Bill", "-$75.00", "2 days ago", "file:image/utility.png")
        );

        recentActivity.getChildren().addAll(titleLabel, activityList);

        return recentActivity;
    }

    private static HBox createActivityItem(String title, String amount, String date, String iconPath) {
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

        Label dateLabel = new Label(date);
        dateLabel.setFont(Font.font("Candara", FontWeight.NORMAL, 12));
        dateLabel.setStyle("-fx-text-fill: #666666;");

        details.getChildren().addAll(titleLabel, dateLabel);

        Label amountLabel = new Label(amount);
        amountLabel.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        amountLabel.setStyle("-fx-text-fill: " + (amount.startsWith("+") ? "#4CAF50" : "#E57373") + ";");

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

        homeButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        homeButton.setOnMouseEntered(null);
        homeButton.setOnMouseExited(null);

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

    private static VBox createTrackStatistics() {
        VBox statsBox = new VBox(20);
        statsBox.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);"
        );
        statsBox.setPadding(new Insets(20));

        Label titleLabel = new Label("Track Statistics");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: black;");

        // Create statistics grid
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(15);
        statsGrid.setPadding(new Insets(10));

        // Add statistics items
        addStatItem(statsGrid, 0, "Total Transactions", "156", "Total number of transactions this month");
        addStatItem(statsGrid, 1, "Average Transaction", "$85.50", "Average amount per transaction");
        addStatItem(statsGrid, 2, "Highest Category", "Groceries", "Category with highest spending");
        addStatItem(statsGrid, 3, "Monthly Trend", "+12.5%", "Compared to last month");

        // Create category distribution chart
        VBox chartBox = new VBox(10);
        chartBox.setPadding(new Insets(20));
        chartBox.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 10;");

        Label chartTitle = new Label("Category Distribution");
        chartTitle.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        chartTitle.setStyle("-fx-text-fill: black;");

        // Create a simple bar chart representation using rectangles
        HBox chartBars = new HBox(10);
        chartBars.setAlignment(Pos.BOTTOM_CENTER);
        chartBars.setPrefHeight(150);

        // Add sample bars
        String[] categories = {"Groceries", "Transport", "Entertainment", "Utilities", "Others"};
        double[] percentages = {35, 25, 15, 20, 5};
        String[] colors = {"#4CAF50", "#2196F3", "#9C27B0", "#FF9800", "#607D8B"};

        for (int i = 0; i < categories.length; i++) {
            VBox barContainer = new VBox(5);
            barContainer.setAlignment(Pos.CENTER);

            Rectangle bar = new Rectangle(40, percentages[i] * 1.5);
            bar.setFill(Color.web(colors[i]));
            bar.setArcWidth(5);
            bar.setArcHeight(5);

            Label categoryLabel = new Label(categories[i]);
            categoryLabel.setFont(Font.font("Candara", 12));
            categoryLabel.setStyle("-fx-text-fill: #666666;");

            Label percentageLabel = new Label(percentages[i] + "%");
            percentageLabel.setFont(Font.font("Candara", FontWeight.BOLD, 12));
            percentageLabel.setStyle("-fx-text-fill: black;");

            barContainer.getChildren().addAll(bar, percentageLabel, categoryLabel);
            chartBars.getChildren().add(barContainer);
        }

        chartBox.getChildren().addAll(chartTitle, chartBars);

        statsBox.getChildren().addAll(titleLabel, statsGrid, chartBox);
        return statsBox;
    }

    private static void addStatItem(GridPane grid, int row, String label, String value, String tooltip) {
        VBox item = new VBox(5);
        
        Label labelNode = new Label(label);
        labelNode.setFont(Font.font("Candara", 14));
        labelNode.setStyle("-fx-text-fill: #666666;");

        Label valueNode = new Label(value);
        valueNode.setFont(Font.font("Candara", FontWeight.BOLD, 18));
        valueNode.setStyle("-fx-text-fill: black;");

        item.getChildren().addAll(labelNode, valueNode);
        
        Tooltip tooltipNode = new Tooltip(tooltip);
        item.setOnMouseEntered(e -> Tooltip.install(item, tooltipNode));
        item.setOnMouseExited(e -> Tooltip.uninstall(item, tooltipNode));

        grid.add(item, 0, row);
    }

    public static void main(String[] args) {
        launch(args);
    }
}