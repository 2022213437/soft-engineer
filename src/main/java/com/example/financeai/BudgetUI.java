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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BudgetUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budget");

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
        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #F8F9FA;");

        HBox topBar = createTopBar();
        mainContent.getChildren().add(topBar);

        HBox contentWrapper = new HBox(30);
        contentWrapper.setPadding(new Insets(20, 0, 0, 0));

        VBox leftContent = createLeftContent();
        HBox.setHgrow(leftContent, Priority.ALWAYS);

        VBox rightContent = createRightSection();
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

        Label titleLabel = new Label("Budget Analysis");
        titleLabel.setFont(Font.font("Candara", FontWeight.BOLD, 26));
        titleLabel.setStyle("-fx-text-fill: black;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(titleLabel, spacer);
        return topBar;
    }

    private static VBox createLeftContent() {
        VBox leftContainer = new VBox(30);
        leftContainer.setPadding(new Insets(20));
        leftContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Purchase Information Section
        VBox purchaseSection = new VBox(15);
        Label purchaseTitle = new Label("Purchase Information");
        purchaseTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        purchaseTitle.setStyle("-fx-text-fill: black");
        
        TextField itemNameField = createStyledTextField("Item Name", 300);
        TextField itemAmountField = createStyledTextField("Amount ($)", 300);
        TextField storagePeriodField = createStyledTextField("Storage Period (months)", 300);
        TextField purchaseDateField = createStyledTextField("Purchase Date (YYYY-MM-DD)", 300);
        TextField categoryField = createStyledTextField("Category (e.g., Electronics, Food)", 300);
        TextField priorityField = createStyledTextField("Priority (High/Medium/Low)", 300);
        
        purchaseSection.getChildren().addAll(purchaseTitle, itemNameField, itemAmountField, 
            storagePeriodField, purchaseDateField, categoryField, priorityField);

        // Personal Information Section
        VBox personalSection = new VBox(15);
        Label personalTitle = new Label("Personal Information");
        personalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        personalTitle.setStyle("-fx-text-fill: black");
        
        TextField nameField = createStyledTextField("Full Name", 300);
        TextField ageField = createStyledTextField("Age", 300);
        TextField occupationField = createStyledTextField("Occupation", 300);
        TextField incomeField = createStyledTextField("Monthly Income ($)", 300);
        TextField savingsField = createStyledTextField("Current Savings ($)", 300);
        TextField monthlyExpensesField = createStyledTextField("Monthly Fixed Expenses ($)", 300);
        TextField financialGoalsField = createStyledTextField("Financial Goals", 300);

        personalSection.getChildren().addAll(personalTitle, nameField, ageField, occupationField,
            incomeField, savingsField, monthlyExpensesField, financialGoalsField);

        // Submit Button
        Button submitButton = new Button("Generate Budget Analysis");
        submitButton.setFont(Font.font("Candara", FontWeight.BOLD, 16));
        submitButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-padding: 15 20 15 20; -fx-background-radius: 5;");
        submitButton.setMaxWidth(300);

        leftContainer.getChildren().addAll(purchaseSection, personalSection, submitButton);
        return leftContainer;
    }

    private static TextField createStyledTextField(String promptText, double width) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFont(Font.font("Candara", 14));
        textField.setPrefWidth(width);
        textField.setStyle("-fx-background-color: white; -fx-border-color: #ddd; " +
            "-fx-border-radius: 3; -fx-padding: 8;");
        return textField;
    }

    private static VBox createRightSection() {
        VBox rightBox = new VBox(20);
        rightBox.setPadding(new Insets(20));
        rightBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label resultTitle = new Label("Analysis Results");
        resultTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextArea resultArea = new TextArea();
        resultArea.setPromptText("Budget analysis results will appear here...");
        resultArea.setFont(Font.font("Candara", 14));
        resultArea.setStyle("-fx-text-fill: black;");
        resultArea.setWrapText(true);
        resultArea.setEditable(false);
        resultArea.setStyle("-fx-background-color: white; -fx-border-color: #ddd; " +
            "-fx-border-radius: 3; -fx-padding: 10;");
        VBox.setVgrow(resultArea, Priority.ALWAYS);

        rightBox.getChildren().addAll(resultTitle, resultArea);
        return rightBox;
    }

    private static VBox createSidebar(Stage stage) {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #DEF2CD;");
        sidebar.setPrefWidth(200);

        Label financeTitle = new Label("FinanceAI");
        financeTitle.setFont(Font.font("Candara", FontWeight.BOLD, 20));
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

        budgetButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        budgetButton.setOnMouseEntered(null);
        budgetButton.setOnMouseExited(null);

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

        dashboardButton.setOnAction(event -> {
            DashboardUI dashboardUI = new DashboardUI();
            Stage dashboardStage = new Stage();
            dashboardUI.start(dashboardStage);
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