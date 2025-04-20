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
import javafx.stage.Stage;

public class SettingsUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AI Finance Tracker - Settings");

        BorderPane root = new BorderPane();

        root.setTop(createHeader());

        root.setLeft(createSidebar());

        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1100, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane createHeader() {
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

        dashboardButton.setFont(Font.font("Candara", 14));
        transactionButton.setFont(Font.font("Candara", 14));
        settingsButton.setFont(Font.font("Candara", 14));

        String defaultButtonStyle = "-fx-background-color: #E0E0E0; -fx-text-fill: black; -fx-font-weight: bold;";
        String activeButtonStyle = "-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold;";
        dashboardButton.setStyle(defaultButtonStyle);
        transactionButton.setStyle(defaultButtonStyle);
        settingsButton.setStyle(activeButtonStyle);
        navButtons.getChildren().addAll(dashboardButton, transactionButton, settingsButton);

        dashboardButton.setOnAction(event -> {
            ExpenseOverviewApp expenseOverviewApp = new ExpenseOverviewApp();
            Stage newStage = new Stage();
            try {
                expenseOverviewApp.start(newStage);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO
            }
        });

        transactionButton.setOnAction(event -> {
            FinanceTrackerUI financeTrackerUI = new FinanceTrackerUI();
            Stage newStage = new Stage();
            try {
                financeTrackerUI.start(newStage);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO
            }
        });

        headerPane.setLeft(titleBox);
        headerPane.setRight(navButtons);
        return headerPane;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(25));
        sidebar.setStyle("-fx-background-color: #DCEDC8;");
        sidebar.setPrefWidth(220);

        Label budgetSettingsLabel = createSidebarLabel("Budget Settings", true);
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

    private Label createSidebarLabel(String text, boolean isActive) {
        Label label = new Label(text);
        label.setFont(Font.font("Candara", isActive ? FontWeight.BOLD : FontWeight.NORMAL, 14));
        label.setTextFill(isActive ? Color.BLACK : Color.DARKSLATEGRAY);
        return label;
    }

    private BorderPane createMainContent() {
        BorderPane mainAreaLayout = new BorderPane();
        mainAreaLayout.setPadding(new Insets(25));
        mainAreaLayout.setStyle("-fx-background-color: white;");

        VBox sectionsContainer = new VBox(40);
        sectionsContainer.setPadding(new Insets(10, 0, 20, 0));

        VBox budgetBox = createBudgetSettingsBox();
        VBox savingsBox = createSavingsGoalBox();
        VBox localizationBox = createLocalizationBox();

        sectionsContainer.getChildren().addAll(budgetBox, savingsBox, localizationBox);

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

    private VBox createBudgetSettingsBox() {
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

        ToggleButton notificationToggle = new ToggleButton("Enable Budget Notifications");
        notificationToggle.setFont(Font.font("HP Simplified", 14));
        notificationToggle.setSelected(true);
        box.getChildren().addAll(title, monthlyBudgetLabel, monthlyBudgetField, notificationSettingsLabel, notificationToggle);
        return box;
    }

    private VBox createSavingsGoalBox() {
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
        box.getChildren().addAll(title, setGoalLabel, setGoalField, progressLabel, progressSlider);
        return box;
    }

    private VBox createLocalizationBox() {
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

    private HBox createSaveButtonArea() {
        HBox buttonArea = new HBox();
        buttonArea.setAlignment(Pos.CENTER_RIGHT);
        buttonArea.setPadding(new Insets(20, 0, 0, 0));
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15 8 15;");
        buttonArea.getChildren().add(saveButton);
        return buttonArea;
    }

    public static void main(String[] args) {
        launch(args);
    }
}