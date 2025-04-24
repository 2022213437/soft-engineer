package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Settings");

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
        root.setTop(createHeader(stage));
        root.setLeft(createSidebar());
        root.setCenter(createMainContent());
        return root;
    }

    private static BorderPane createHeader(Stage stage) {
        BorderPane headerPane = new BorderPane();
        headerPane.setPadding(new Insets(15, 25, 15, 25));
        headerPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));

        Label titleLabel = new Label("FinanceAI Settings");
        titleLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 18));
        HBox titleBox = new HBox(5, titleLabel);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        HBox navButtons = new HBox(10);
        navButtons.setAlignment(Pos.CENTER_RIGHT);
        Button dashboardButton = new Button("Dashboard");
        Button transactionButton = new Button("Transaction Classification");
        Button settingsButton = new Button("Settings");
        Button homeButton = new Button("Home");

        dashboardButton.setFont(Font.font("Candara", 14));
        transactionButton.setFont(Font.font("Candara", 14));
        settingsButton.setFont(Font.font("Candara", 14));
        homeButton.setFont(Font.font("Candara", 14));

        String defaultButtonStyle = "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #E0E0E0; -fx-border-radius: 3;";
        String activeButtonStyle = "-fx-background-color: #F5F5F5; -fx-text-fill: black; -fx-border-color: #E0E0E0; -fx-border-radius: 3; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);";
        dashboardButton.setStyle(defaultButtonStyle);
        transactionButton.setStyle(defaultButtonStyle);
        settingsButton.setStyle(activeButtonStyle);
        homeButton.setStyle(defaultButtonStyle);
        navButtons.getChildren().addAll(dashboardButton, transactionButton, settingsButton, homeButton);

        dashboardButton.setOnAction(event -> {
            DashboardUI dashboardUI = new DashboardUI();
            Stage dashboardStage = new Stage();
            dashboardUI.start(dashboardStage);
            stage.close();
        });

        transactionButton.setOnAction(event -> {
            stage.getScene().setRoot(TransactionUI.createContent(stage));
        });

        homeButton.setOnAction(event -> {
            HomeUI homeUI = new HomeUI();
            Stage homeStage = new Stage();
            homeUI.start(homeStage);
            stage.close();
        });

        headerPane.setLeft(titleBox);
        headerPane.setRight(navButtons);
        return headerPane;
    }

    private static VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(25));
        sidebar.setStyle("-fx-background-color: #DCEDC8;");
        sidebar.setPrefWidth(220);

        Label budgetSettingsLabel = createSidebarLabel("Budget Settings", false);
        Label savingsGoalLabel = createSidebarLabel("Savings Goal", false);
        Label localizationLabel = createSidebarLabel("Localization", false);
        Label categoriesLabel = createSidebarLabel("Categories", false);
        Label aiPreferencesLabel = createSidebarLabel("AI Preferences", false);
        Label profileSettingsLabel = createSidebarLabel("Profile Settings", false);

        sidebar.getChildren().addAll(
                budgetSettingsLabel, savingsGoalLabel, localizationLabel,
                categoriesLabel, aiPreferencesLabel, profileSettingsLabel
        );
        return sidebar;
    }

    private static Label createSidebarLabel(String text, boolean isActive) {
        Label label = new Label(text);
        label.setFont(Font.font("Candara", isActive ? FontWeight.BOLD : FontWeight.NORMAL, 14));
        label.setTextFill(isActive ? Color.BLACK : Color.DARKSLATEGRAY);
        return label;
    }

    private static BorderPane createMainContent() {
        BorderPane mainAreaLayout = new BorderPane();
        mainAreaLayout.setPadding(new Insets(25));
        mainAreaLayout.setStyle("-fx-background-color: white;");

        VBox sectionsContainer = new VBox(40);
        sectionsContainer.setPadding(new Insets(10, 0, 20, 0));

        VBox budgetBox = createBudgetSettingsBox();
        VBox savingsBox = createSavingsGoalBox();
        VBox localizationBox = createLocalizationBox();
        VBox categoriesBox = createCategoriesAIBox();
        VBox preferencesBox = createPreferencesBox();
        VBox profileBox = createProfileSettingsBox();

        sectionsContainer.getChildren().addAll(budgetBox, savingsBox, localizationBox,
                categoriesBox, preferencesBox, profileBox);

        ScrollPane scrollPane = new ScrollPane(sectionsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        HBox saveButtonArea = createSaveButtonArea();

        mainAreaLayout.setCenter(scrollPane);
        mainAreaLayout.setBottom(saveButtonArea);
        return mainAreaLayout;
    }

    private static VBox createBudgetSettingsBox() {
        VBox box = new VBox(10);
        Label title = new Label("Budget Settings");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: black;");

        Label monthlyBudgetLabel = new Label("Monthly Budget");
        monthlyBudgetLabel.setFont(Font.font("Candara", 14));
        monthlyBudgetLabel.setStyle("-fx-text-fill: black;");
        TextField monthlyBudgetField = new TextField();
        monthlyBudgetField.setPromptText("eg: 138");

        Label notificationSettingsLabel = new Label("Notification Settings");
        notificationSettingsLabel.setFont(Font.font("Candara", 14));
        notificationSettingsLabel.setStyle("-fx-text-fill: black;");

        CheckBox notificationToggle = new CheckBox();
        Label notificationLabel = new Label("Enable Budget Notifications");
        notificationLabel.setFont(Font.font("HP Simplified", 14));
        notificationLabel.setStyle("-fx-text-fill: black;");
        Label notificationDesc = new Label("Receive alerts when approaching budget limits");
        notificationDesc.setFont(Font.font("HP Simplified", 12));
        notificationDesc.setStyle("-fx-text-fill: #555555;");
        VBox notificationTextBox = new VBox(0, notificationLabel, notificationDesc);
        HBox notificationBox = new HBox(5, notificationToggle, notificationTextBox);
        notificationBox.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().addAll(title, monthlyBudgetLabel, monthlyBudgetField,
                notificationSettingsLabel, notificationBox);
        return box;
    }

    private static VBox createSavingsGoalBox() {
        VBox box = new VBox(10);
        Label title = new Label("Savings Goal Setting");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: black;");

        Label setGoalLabel = new Label("Set Savings Goal");
        setGoalLabel.setFont(Font.font("Candara", 14));
        setGoalLabel.setStyle("-fx-text-fill: black;");
        TextField setGoalField = new TextField();
        setGoalField.setPromptText("eg: 138");

        Label progressLabel = new Label("Progress");
        progressLabel.setFont(Font.font("HP Simplified", 14));
        progressLabel.setStyle("-fx-text-fill: black;");
        Slider progressSlider = new Slider(0, 100, 60);
        progressSlider.setShowTickMarks(false);
        progressSlider.setShowTickLabels(false);
        Label progressValue = new Label("60%");
        progressValue.setStyle("-fx-text-fill: #555555;");
        progressValue.setFont(Font.font("HP Simplified", 12));

        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            progressValue.setText(String.format("%.0f%%", newValue.doubleValue()));
        });

        HBox progressBox = new HBox(10, progressSlider, progressValue);
        progressBox.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().addAll(title, setGoalLabel, setGoalField, progressLabel, progressBox);
        return box;
    }

    private static VBox createLocalizationBox() {
        VBox box = new VBox(10);
        Label title = new Label("Localization Preferences");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: black;");

        Label preferredLanguageLabel = new Label("Preferred Language");
        preferredLanguageLabel.setFont(Font.font("Candara", 14));
        preferredLanguageLabel.setStyle("-fx-text-fill: black;");
        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "Chinese", "Spanish", "French", "German");
        languageComboBox.setValue("English");
        languageComboBox.setMaxWidth(Double.MAX_VALUE);

        CheckBox systemLocaleCheckBox = new CheckBox();
        Label systemLocaleLabel = new Label("Use System Locale");
        systemLocaleLabel.setFont(Font.font("HP Simplified", 14));
        systemLocaleLabel.setStyle("-fx-text-fill: black;");
        HBox systemLocaleBox = new HBox(5, systemLocaleCheckBox, systemLocaleLabel);
        systemLocaleBox.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().addAll(title, preferredLanguageLabel, languageComboBox, systemLocaleBox);
        return box;
    }

    private static HBox createSaveButtonArea() {
        HBox buttonArea = new HBox();
        buttonArea.setAlignment(Pos.CENTER_RIGHT);
        buttonArea.setPadding(new Insets(20, 0, 0, 0));
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15 8 15;");
        buttonArea.getChildren().add(saveButton);
        return buttonArea;
    }

    private static VBox createCategoriesAIBox() {
        VBox box = new VBox(10);
        Label title = new Label("Categories AI Settings");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: black;");

        Label categoriesLabel = new Label("Manage Categories");
        categoriesLabel.setFont(Font.font("Candara", 14));
        categoriesLabel.setStyle("-fx-text-fill: black;");

        HBox addCategoryBox = new HBox(10);
        TextField newCategoryField = new TextField();
        newCategoryField.setPromptText("Enter new category name");
        Button addButton = new Button("Add Category");
        addButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        addButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white;");
        addCategoryBox.getChildren().addAll(newCategoryField, addButton);

        ListView<String> categoriesList = new ListView<>();
        categoriesList.setPrefHeight(150);
        categoriesList.getItems().addAll("Food", "Transportation", "Entertainment", "Shopping");

        Label aiSettingsLabel = new Label("AI Learning Settings");
        aiSettingsLabel.setFont(Font.font("Candara", 14));
        aiSettingsLabel.setStyle("-fx-text-fill: black;");

        CheckBox autoCategorizeCheck = new CheckBox("Enable Auto-Categorization");
        autoCategorizeCheck.setFont(Font.font("HP Simplified", 14));
        autoCategorizeCheck.setStyle("-fx-text-fill: black;");
        Slider learningRateSlider = new Slider(0, 100, 50);
        learningRateSlider.setShowTickMarks(true);
        learningRateSlider.setShowTickLabels(true);
        Label learningRateLabel = new Label("AI Learning Rate");
        learningRateLabel.setStyle("-fx-text-fill: black;");
        Label learningRateValue = new Label("50%");
        learningRateValue.setStyle("-fx-text-fill: #555555;");
        learningRateValue.setFont(Font.font("HP Simplified", 12));

        learningRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            learningRateValue.setText(String.format("%.0f%%", newValue.doubleValue()));
        });

        HBox learningRateBox = new HBox(10, learningRateSlider, learningRateValue);
        learningRateBox.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().addAll(title, categoriesLabel, addCategoryBox, categoriesList,
                aiSettingsLabel, autoCategorizeCheck, learningRateLabel, learningRateBox);
        return box;
    }

    private static VBox createPreferencesBox() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(0, 0, 0, 5));
        Label title = new Label("Preferences");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: black;");

        Label themeLabel = new Label("Theme Settings");
        themeLabel.setFont(Font.font("Candara", 14));
        themeLabel.setStyle("-fx-text-fill: black;");
        ComboBox<String> themeComboBox = new ComboBox<>();
        themeComboBox.getItems().addAll("Light", "Dark", "System Default");
        themeComboBox.setValue("Light");
        themeComboBox.setMaxWidth(Double.MAX_VALUE);

        Label fontLabel = new Label("Font Settings");
        fontLabel.setFont(Font.font("Candara", 14));
        fontLabel.setStyle("-fx-text-fill: black;");
        ComboBox<String> fontComboBox = new ComboBox<>();
        fontComboBox.getItems().addAll("Small", "Medium", "Large");
        fontComboBox.setValue("Medium");
        fontComboBox.setMaxWidth(Double.MAX_VALUE);

        Label notificationLabel = new Label("Notification Settings");
        notificationLabel.setFont(Font.font("Candara", 14));
        notificationLabel.setStyle("-fx-text-fill: black;");

        CheckBox emailNotifications = new CheckBox();
        Label emailLabel = new Label("Email Notifications");
        emailLabel.setFont(Font.font("HP Simplified", 14));
        emailLabel.setStyle("-fx-text-fill: black;");
        Label emailDesc = new Label("Receive notifications via email");
        emailDesc.setFont(Font.font("HP Simplified", 12));
        emailDesc.setStyle("-fx-text-fill: #555555;");
        VBox emailTextBox = new VBox(0, emailLabel, emailDesc);
        HBox emailBox = new HBox(5, emailNotifications, emailTextBox);
        emailBox.setAlignment(Pos.CENTER_LEFT);

        CheckBox pushNotifications = new CheckBox();
        Label pushLabel = new Label("Push Notifications");
        pushLabel.setFont(Font.font("HP Simplified", 14));
        pushLabel.setStyle("-fx-text-fill: black;");
        Label pushDesc = new Label("Receive push notifications on your device");
        pushDesc.setFont(Font.font("HP Simplified", 12));
        pushDesc.setStyle("-fx-text-fill: #555555;");
        VBox pushTextBox = new VBox(0, pushLabel, pushDesc);
        HBox pushBox = new HBox(5, pushNotifications, pushTextBox);
        pushBox.setAlignment(Pos.CENTER_LEFT);

        CheckBox soundNotifications = new CheckBox();
        Label soundLabel = new Label("Sound Notifications");
        soundLabel.setFont(Font.font("HP Simplified", 14));
        soundLabel.setStyle("-fx-text-fill: black;");
        Label soundDesc = new Label("Play sound for important notifications");
        soundDesc.setFont(Font.font("HP Simplified", 12));
        soundDesc.setStyle("-fx-text-fill: #555555;");
        VBox soundTextBox = new VBox(0, soundLabel, soundDesc);
        HBox soundBox = new HBox(5, soundNotifications, soundTextBox);
        soundBox.setAlignment(Pos.CENTER_LEFT);

        Label syncLabel = new Label("Data Sync Settings");
        syncLabel.setFont(Font.font("Candara", 14));
        syncLabel.setStyle("-fx-text-fill: black;");

        CheckBox autoSync = new CheckBox();
        Label autoSyncLabel = new Label("Auto Sync");
        autoSyncLabel.setFont(Font.font("HP Simplified", 14));
        autoSyncLabel.setStyle("-fx-text-fill: black;");
        Label autoSyncDesc = new Label("Automatically sync data across devices");
        autoSyncDesc.setFont(Font.font("HP Simplified", 12));
        autoSyncDesc.setStyle("-fx-text-fill: #555555;");
        VBox autoSyncTextBox = new VBox(0, autoSyncLabel, autoSyncDesc);
        HBox autoSyncBox = new HBox(5, autoSync, autoSyncTextBox);
        autoSyncBox.setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> syncInterval = new ComboBox<>();
        syncInterval.getItems().addAll("Every 15 minutes", "Every hour", "Every 6 hours", "Daily");
        syncInterval.setValue("Every hour");
        syncInterval.setMaxWidth(Double.MAX_VALUE);

        box.getChildren().addAll(title,
                themeLabel, themeComboBox,
                fontLabel, fontComboBox,
                notificationLabel, emailBox, pushBox, soundBox,
                syncLabel, autoSyncBox, syncInterval);
        return box;
    }

    private static VBox createProfileSettingsBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(0, 0, 0, 5));
        Label title = new Label("Profile Settings");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: black;");

        Label personalInfoLabel = new Label("Personal Information");
        personalInfoLabel.setFont(Font.font("Candara", 14));
        personalInfoLabel.setStyle("-fx-text-fill: black;");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        Label securityLabel = new Label("Security Settings");
        securityLabel.setFont(Font.font("Candara", 14));
        securityLabel.setStyle("-fx-text-fill: black;");

        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Current Password");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm New Password");

        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        changePasswordButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white;");

        Label accountLabel = new Label("Account Settings");
        accountLabel.setFont(Font.font("Candara", 14));
        accountLabel.setStyle("-fx-text-fill: black;");

        CheckBox twoFactorAuth = new CheckBox();
        Label twoFactorLabel = new Label("Enable Two-Factor Authentication");
        twoFactorLabel.setFont(Font.font("HP Simplified", 14));
        twoFactorLabel.setStyle("-fx-text-fill: black;");
        Label twoFactorDesc = new Label("Add an extra layer of security to your account");
        twoFactorDesc.setFont(Font.font("HP Simplified", 12));
        twoFactorDesc.setStyle("-fx-text-fill: #555555;");
        VBox twoFactorTextBox = new VBox(0, twoFactorLabel, twoFactorDesc);
        HBox twoFactorBox = new HBox(5, twoFactorAuth, twoFactorTextBox);
        twoFactorBox.setAlignment(Pos.CENTER_LEFT);

        CheckBox emailVerification = new CheckBox();
        Label verificationLabel = new Label("Email Verification Required");
        verificationLabel.setFont(Font.font("HP Simplified", 14));
        verificationLabel.setStyle("-fx-text-fill: black;");
        Label emailVerificationDesc = new Label("Require email verification for sensitive operations");
        emailVerificationDesc.setFont(Font.font("HP Simplified", 12));
        emailVerificationDesc.setStyle("-fx-text-fill: #555555;");
        VBox verificationTextBox = new VBox(0, verificationLabel, emailVerificationDesc);
        HBox emailVerificationBox = new HBox(5, emailVerification, verificationTextBox);
        emailVerificationBox.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().addAll(title,
                personalInfoLabel, nameField, emailField, phoneField,
                securityLabel, currentPasswordField, newPasswordField, confirmPasswordField,
                changePasswordButton,
                accountLabel, twoFactorBox, emailVerificationBox);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}