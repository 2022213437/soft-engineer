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
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #F8F9FA;");

        HBox topBar = createTopBar();
        VBox homeContent = createHomeContent();

        mainContent.getChildren().addAll(topBar, homeContent);
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

    private static VBox createHomeContent() {
        VBox homeContent = new VBox(20);
        homeContent.setPadding(new Insets(20));

        Label welcomeTitle = new Label("Welcome to FinanceAI");
        welcomeTitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 26));
        welcomeTitle.setStyle("-fx-text-fill: black;");

        Label subtitle = new Label("Your personal finance assistant");
        subtitle.setFont(Font.font("Candara", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-text-fill: #666666;");

        VBox.setMargin(subtitle, new Insets(-10, 0, 20, 0));

        HBox featureCards = createFeatureCards();
        VBox quickActions = createQuickActions();
        VBox recentActivity = createRecentActivity();

        homeContent.getChildren().addAll(
                welcomeTitle,
                subtitle,
                featureCards,
                quickActions,
                recentActivity
        );

        return homeContent;
    }

    private static HBox createFeatureCards() {
        HBox featureCards = new HBox(20);
        featureCards.setAlignment(Pos.CENTER);

        VBox[] cards = {
                createFeatureCard(
                        "Track Expenses",
                        "Monitor your daily spending",
                        "file:image/Icon search.png",
                        "#E3F2FD"
                ),
                createFeatureCard(
                        "View Dashboard",
                        "Analyze your financial data",
                        "file:image/dashboard.png",
                        "#F3E5F5"
                ),
                createFeatureCard(
                        "Manage Budget",
                        "Set and track your budgets",
                        "file:image/Icon heart.png",
                        "#E8F5E9"
                )
        };

        featureCards.getChildren().addAll(cards);
        return featureCards;
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
        card.setPrefWidth(250);
        card.setPrefHeight(200);

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(40);
        icon.setFitWidth(40);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: black;");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Candara", FontWeight.NORMAL, 14));
        descLabel.setStyle("-fx-text-fill: #666666;");
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        card.getChildren().addAll(icon, titleLabel, descLabel);

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
                createActionButton("Add Transaction", "file:image/Icon plus.png"),
                createActionButton("View Reports", "file:image/Icon chart.png"),
                createActionButton("Set Budget", "file:image/Icon heart.png"),
                createActionButton("View Goals", "file:image/Icon target.png")
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

    public static void main(String[] args) {
        launch(args);
    }
}