package com.example.financeai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BudgetUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AI Finance Tracker - Budget");

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
        root.setStyle("-fx-background-color: #FFFFFF;");

        HBox topSection = createTopSection(stage);
        root.setTop(topSection);

        VBox leftSection = createLeftContent();
        root.setLeft(leftSection);
        leftSection.prefWidthProperty().bind(root.widthProperty().multiply(0.7));

        VBox rightSection = createRightSection();
        root.setRight(rightSection);

        return root;
    }

    private static HBox createTopSection(Stage stage) {
        HBox topBox = new HBox(20);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(10, 20, 10, 20));

        Label titleLabel = new Label("FinanceAI Tracker");
        titleLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 24));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setFont(Font.font("Candara", 14));

        Button settingsButton = new Button("Track");
        settingsButton.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        settingsButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15 8 15;");

        settingsButton.setOnAction(event -> {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.getScene().setRoot(TrackUI.createContent(currentStage));
        });

        Button homeButton = new Button("Home");
        homeButton.setFont(Font.font("Candara", FontWeight.BOLD, 14));
        homeButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15 8 15;");
        homeButton.setOnAction(event -> {
            HomeUI homeUI = new HomeUI();
            Stage homeStage = new Stage();
            homeUI.start(homeStage);
            stage.close();
        });

        topBox.getChildren().addAll(titleLabel, spacer, searchField, homeButton, settingsButton);
        return topBox;
    }

    private static VBox createLeftContent() {
        VBox leftContainer = new VBox(20);
        leftContainer.setPadding(new Insets(20));

        HBox row1 = new HBox(20);
        VBox.setVgrow(row1, Priority.ALWAYS);

        StackPane totalBalancePane = createHoverableBlock("Total Balance", "$12,345.67");
        HBox.setHgrow(totalBalancePane, Priority.ALWAYS);

        VBox categorizedTransactionsContent = new VBox(5);
        categorizedTransactionsContent.getChildren().addAll(
                new Label("Groceries: $500"),
                new Label("Utilities: $150"),
                new Label("Entertainment: $200"),
                new Label("Transport: $100"),
                new Label("Healthcare: $75")
        );
        StackPane categorizedTransactionsPane = createHoverableBlock("Categorized Transactions", categorizedTransactionsContent);
        HBox.setHgrow(categorizedTransactionsPane, Priority.ALWAYS);

        row1.getChildren().addAll(totalBalancePane, categorizedTransactionsPane);
        leftContainer.getChildren().add(row1);

        HBox row2 = new HBox(20);
        VBox.setVgrow(row2, Priority.ALWAYS);

        ImageView consumptionAnalysisImage = new ImageView();
        consumptionAnalysisImage.setFitWidth(120);
        consumptionAnalysisImage.setFitHeight(120);
        try {
            javafx.scene.image.Image consumptionImage = new javafx.scene.image.Image("file:image/ChartCircle.png");
            consumptionAnalysisImage.setImage(consumptionImage);
        } catch (Exception e) {
            System.err.println("Error loading consumption analysis image: " + e.getMessage());
        }

        VBox consumptionText = new VBox(5);
        consumptionText.getChildren().addAll(
                new Label("Monthly Spending Breakdown (Tabular Data):"),
                new Label("$500 on food, 25% of the total"),
                new Label("$800 rent, 40% of the total"),
                new Label("Keep an eye out for big spending items.")
        );
        consumptionText.setAlignment(Pos.CENTER_LEFT);
        consumptionText.setPadding(new Insets(0, 0, 0, 10));

        HBox consumptionContent = new HBox(15);
        consumptionContent.setAlignment(Pos.CENTER_LEFT);
        consumptionContent.getChildren().addAll(consumptionAnalysisImage, consumptionText);

        StackPane consumptionAnalysisTopPane = createHoverableBlock("Consumption Analysis", consumptionContent);
        HBox.setHgrow(consumptionAnalysisTopPane, Priority.ALWAYS);

        VBox budgetRecommendationsContent = new VBox(5);
        budgetRecommendationsContent.getChildren().addAll(
                new Label("Increase savings by 10%"),
                new Label("Reduce dining expenses by 15%"),
                new Label("Invest in low-risk stocks")
        );
        StackPane budgetRecommendationsPane = createHoverableBlock("Budget Recommendations", budgetRecommendationsContent);
        HBox.setHgrow(budgetRecommendationsPane, Priority.ALWAYS);

        row2.getChildren().addAll(consumptionAnalysisTopPane, budgetRecommendationsPane);
        leftContainer.getChildren().add(row2);

        ImageView savingsGoalProgressImage = new ImageView();
        savingsGoalProgressImage.setFitWidth(400);
        savingsGoalProgressImage.setFitHeight(150);
        try {
            javafx.scene.image.Image savingsImage = new javafx.scene.image.Image("file:image/Chart.png");
            savingsGoalProgressImage.setImage(savingsImage);
        } catch (Exception e) {
            System.err.println("Error loading savings goal progress image: " + e.getMessage());
            // TODO
        }

        VBox savingsText = new VBox(5);
        savingsText.getChildren().addAll(
                new Label("Progress towards Savings Goal (Tabular Data):"),
                new Label("Necessary goal: $1200 saved / $2000 goal (60%)"),
                new Label("Emergency Fund: $3500 Saved / $5000 Goal (70%)")
        );
        savingsText.setAlignment(Pos.CENTER_LEFT);
        savingsText.setPadding(new Insets(0, 0, 0, 10));

        HBox savingsContent = new HBox(15);
        savingsContent.setAlignment(Pos.CENTER_LEFT);
        savingsContent.getChildren().addAll(savingsGoalProgressImage, savingsText);

        StackPane savingsGoalProgressPane = createHoverableBlock("Savings Goal Progress", savingsContent);
        VBox.setVgrow(savingsGoalProgressPane, Priority.ALWAYS);
        leftContainer.getChildren().add(savingsGoalProgressPane);

        return leftContainer;
    }

    private static VBox createRightSection() {
        VBox rightBox = new VBox(20);
        rightBox.setPadding(new Insets(20));
        rightBox.setMinWidth(180);
        rightBox.setAlignment(Pos.TOP_CENTER);

        Button importButton = new Button("Import Transactions");
        importButton.setFont(Font.font("Candara", 14));
        importButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 15 10 15; -fx-pref-width: 160;");

        Button openSettingsButton = new Button("Open Settings");
        openSettingsButton.setFont(Font.font("Candara", 14));
        openSettingsButton.setStyle("-fx-background-color: #689F38; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 15 10 15; -fx-pref-width: 160;");

        openSettingsButton.setOnAction(event -> {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.getScene().setRoot(SettingsUI.createContent(currentStage));
        });

        rightBox.getChildren().addAll(importButton, openSettingsButton);
        return rightBox;
    }

    private static StackPane createHoverableBlock(String title, Object content) {
        StackPane block = new StackPane();
        block.setPadding(new Insets(15));
        block.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 5; -fx-border-color: #ddd;");

        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.TOP_LEFT);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        contentBox.getChildren().add(titleLabel);

        if (content instanceof String) {
            Label contentLabel = new Label((String) content);
            contentLabel.setFont(Font.font("Arial", 24));
            contentBox.getChildren().add(contentLabel);
        } else if (content instanceof javafx.scene.Node) {
            contentBox.getChildren().add((javafx.scene.Node) content);
        }

        StackPane.setAlignment(contentBox, Pos.TOP_LEFT);

        block.getChildren().add(contentBox);

        block.setOnMouseEntered(e -> block.setStyle("-fx-background-color: #e9e9e9; -fx-border-radius: 5; -fx-border-color: #bbb;"));
        block.setOnMouseExited(e -> block.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 5; -fx-border-color: #ddd;"));

        return block;
    }

    public static void main(String[] args) {
        launch(args);
    }
}