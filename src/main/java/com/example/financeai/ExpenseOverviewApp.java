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

import com.example.financeai.FinanceTrackerUI;
import com.example.financeai.FinanceAIApp;

public class ExpenseOverviewApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expense Overview App");

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        double windowWidth = screenWidth * 0.8;
        double windowHeight = screenHeight * 0.8;

        BorderPane rootLayout = new BorderPane();

        VBox sidebar = createSidebar(primaryStage);
        rootLayout.setLeft(sidebar);

        VBox mainContent = createMainContent();
        rootLayout.setCenter(mainContent);

        Scene scene = new Scene(rootLayout, windowWidth, windowHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSidebar(Stage stage) {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #DEF2CD;");
        sidebar.setPrefWidth(200);

        Label financeTitle = new Label("FinanceAI");
        financeTitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 20));
        financeTitle.setTextFill(Color.BLACK);
        VBox.setMargin(financeTitle, new Insets(0, 0, 20, 0));

        Button homeButton = createSidebarButton("Home", "file:image/Icon home.png");
        Button trackButton = createSidebarButton("Track", "file:image/Icon search.png");
        Button transactionButton = createSidebarButton("Transaction", "file:image/Icon book open.png");
        Button newButton = createSidebarButton("New", "file:image/Icon plus square.png");
        Button budgetButton = createSidebarButton("Budget", "file:image/Icon heart.png");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button settingsButton = createSidebarButton("Settings", "file:image/Icon gear.png");
        Button logoutButton = createSidebarButton("Log out", "file:image/Icon right from bracket.png");

        transactionButton.setOnAction(event -> {
            stage.close();
            FinanceTrackerUI trackerApp = new FinanceTrackerUI();
            Stage trackerStage = new Stage();
            try {
                trackerApp.start(trackerStage);
            } catch (Exception e) {
                System.err.println("Error opening FinanceTrackerUI:");
                e.printStackTrace();
            }
        });

        logoutButton.setOnAction(event -> {
            stage.close();
            FinanceAIApp mainApp = new FinanceAIApp();
            Stage mainStage = new Stage();
            try {
                mainApp.start(mainStage);
            } catch (Exception e) {
                System.err.println("Error opening FinanceAIApp:");
                e.printStackTrace();
            }
        });

        settingsButton.setOnAction(event -> {
            stage.close();
            SettingsUI settingsApp = new SettingsUI();
            Stage settingsStage = new Stage();
            try {
                settingsApp.start(settingsStage);
            } catch (Exception e) {
                System.err.println("Error opening SettingsUI:");
                e.printStackTrace();
            }
        });

        budgetButton.setOnAction(event -> {
            stage.close();

            BudgetUI budgetApp = new BudgetUI();
            Stage budgetStage = new Stage();
            try {
                budgetApp.start(budgetStage);
            } catch (Exception e) {
                System.err.println("Error opening BudgetUI:");
                e.printStackTrace();
            }
        });


        sidebar.getChildren().addAll(
                financeTitle,
                homeButton,
                trackButton,
                transactionButton,
                newButton,
                budgetButton,
                spacer,
                settingsButton,
                logoutButton
        );

        for (javafx.scene.Node node : sidebar.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if ("Track".equals(button.getText())) {
                    button.setStyle("-fx-background-color: white; -fx-border-color: transparent;");

                    button.setOnMouseEntered(null);
                    button.setOnMouseExited(null);
                    break;
                }
            }
        }

        return sidebar;
    }

    private Button createSidebarButton(String text, String imagePath) {
        Button button = new Button();
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);

        Font buttonFont = Font.font("Arial", 16);

        Image iconImage = new Image(imagePath);
        ImageView icon = new ImageView(iconImage);
        icon.setFitHeight(18);
        icon.setPreserveRatio(true);

        HBox buttonContent = new HBox(5);
        buttonContent.setAlignment(Pos.CENTER_LEFT);

        Label buttonLabel = new Label(text);
        buttonLabel.setFont(buttonFont);
        buttonContent.getChildren().addAll(icon, buttonLabel);

        button.setGraphic(buttonContent);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // double buttonWidth = 180;
        double buttonHeight = 30;
        // button.setPrefWidth(buttonWidth);
        button.setPrefHeight(buttonHeight);

        button.setOnMouseEntered(e -> {
            if (!"Track".equals(text)) {
                button.setStyle("-fx-background-color: #C8E6C9; -fx-border-color: transparent;");
            }
        });
        button.setOnMouseExited(e -> {
            if (!"Track".equals(text)) {
                button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            } else {
                button.setStyle("-fx-background-color: white; -fx-border-color: transparent;");
            }
        });

        if ("Track".equals(text)) {
            button.setStyle("-fx-background-color: white; -fx-border-color: transparent;");
        }

        return button;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #FFFFFF;");

        Label titleLabel = new Label("Expense Overview");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        TableView<ExpenseEntry> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DCDCDC; -fx-border-width: 1;");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableView.setEditable(true);

        tableView.setStyle(tableView.getStyle() + "-fx-fixed-cell-size: 40;");

        TableColumn<ExpenseEntry, Boolean> selectCol = new TableColumn<>("");
        selectCol.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
        selectCol.setEditable(true);
        selectCol.setMaxWidth(40);
        selectCol.setMinWidth(40);
        selectCol.setSortable(false);


        TableColumn<ExpenseEntry, String> detailsCol = new TableColumn<>("Details");
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
        detailsCol.setMinWidth(200);
        detailsCol.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn());

        TableColumn<ExpenseEntry, String> merchantCol = new TableColumn<>("Merchant");
        merchantCol.setCellValueFactory(new PropertyValueFactory<>("merchant"));
        merchantCol.setMinWidth(150);
        merchantCol.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn());


        TableColumn<ExpenseEntry, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setMinWidth(100);
        amountCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        amountCol.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn());


        TableColumn<ExpenseEntry, String> reportCol = new TableColumn<>("Report");
        reportCol.setCellValueFactory(new PropertyValueFactory<>("report"));
        reportCol.setMinWidth(150);
        reportCol.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn());


        TableColumn<ExpenseEntry, String> statusCol = getExpenseEntryStringTableColumn();

        tableView.getColumns().addAll(selectCol, detailsCol, merchantCol, amountCol, reportCol, statusCol);

        ObservableList<ExpenseEntry> data = FXCollections.observableArrayList(
                new ExpenseEntry(false, "2025.11.9 Food Catering", "McFood", "€250.00", "November 2022", "Not Submitted"),
                new ExpenseEntry(false, "2025.11.11 Office Supplies", "Officio", "€150.00", "November 2022", "Not Submitted"),
                new ExpenseEntry(false, "2025.11.12 Business Lunch", "Restaurant", "€75.50", "November 2022", "Not Submitted"),
                new ExpenseEntry(true, "2025.11.13 Travel Expenses", "Airlines", "€450.25", "November 2022", "Submitted"),
                new ExpenseEntry(false, "2025.11.14 Client Dinner", "Bistro", "€120.00", "November 2022", "Not Submitted"),
                new ExpenseEntry(true, "2025.11.15 Accommodation", "Hotel ***", "€275.75", "November 2022", "Submitted"),
                new ExpenseEntry(false, "2025.11.16 News Subscription", "NewsTimes", "€30.00", "November 2022", "Not Submitted")
        );
        tableView.setItems(data);

        Button addExpenseButton = new Button("ADD EXPENSE");
        addExpenseButton.setStyle("-fx-background-color: #689842; -fx-text-fill: white; -fx-padding: 8px 12px; -fx-font-size: 12px;");
        addExpenseButton.setOnAction(event -> {
            ExpenseEntry newEntry = new ExpenseEntry(false, "", "", "", "", "Not Submitted");
            data.add(newEntry);
            tableView.scrollTo(newEntry);
            tableView.getSelectionModel().select(newEntry);
            tableView.edit(data.size() - 1, detailsCol);
        });

        Button deleteExpenseButton = new Button("DELETE");
        deleteExpenseButton.setStyle("-fx-background-color: #812F33; -fx-text-fill: white; -fx-padding: 8px 12px; -fx-font-size: 12px;");
        deleteExpenseButton.setOnAction(event -> {
            ObservableList<ExpenseEntry> selectedItems = tableView.getSelectionModel().getSelectedItems();
            if (selectedItems != null && !selectedItems.isEmpty()) {
                ObservableList<ExpenseEntry> itemsToRemove = FXCollections.observableArrayList(selectedItems);
                data.removeAll(itemsToRemove);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("请先选择要删除的条目。");
                alert.showAndWait();
            }
        });

        HBox titleAndButtonBox = new HBox(15);
        titleAndButtonBox.setAlignment(Pos.CENTER_LEFT);

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        HBox buttonBox = new HBox(10, addExpenseButton, deleteExpenseButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        titleAndButtonBox.getChildren().addAll(titleLabel, titleSpacer, buttonBox);

        mainContent.getChildren().addAll(titleAndButtonBox, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        return mainContent;
    }

    @NotNull
    private static TableColumn<ExpenseEntry, String> getExpenseEntryStringTableColumn() {
        TableColumn<ExpenseEntry, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setMinWidth(120);
        statusCol.setCellFactory(column -> new TableCell<ExpenseEntry, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    Label statusLabel = new Label(item);
                    statusLabel.setPadding(new Insets(3, 6, 3, 6));
                    statusLabel.setStyle("-fx-background-color: #F4F4F4; -fx-background-radius: 4; -fx-border-color: #F4F4F4; -fx-border-radius: 4; -fx-border-width: 1;");
                    if ("Submitted".equalsIgnoreCase(item)) {
                        statusLabel.setStyle("-fx-background-color: #DEF2CD; -fx-background-radius: 4; -fx-border-color: #DEF2CD; -fx-border-radius: 4; -fx-border-width: 1;");
                    }
                    setGraphic(statusLabel);
                    setText(null);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        return statusCol;
    }

    public static class ExpenseEntry {
        private final SimpleBooleanProperty selected;
        private final SimpleStringProperty details;
        private final SimpleStringProperty merchant;
        private final SimpleStringProperty amount;
        private final SimpleStringProperty report;
        private final SimpleStringProperty status;

        public ExpenseEntry(boolean selected, String details, String merchant, String amount, String report, String status) {
            this.selected = new SimpleBooleanProperty(selected);
            this.details = new SimpleStringProperty(details);
            this.merchant = new SimpleStringProperty(merchant);
            this.amount = new SimpleStringProperty(amount);
            this.report = new SimpleStringProperty(report);
            this.status = new SimpleStringProperty(status);
        }

        // --- Getters, Setters, and Property Methods ---
        public boolean isSelected() {
            return selected.get();
        }

        public SimpleBooleanProperty selectedProperty() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected.set(selected);
        }

        public String getDetails() {
            return details.get();
        }

        public SimpleStringProperty detailsProperty() {
            return details;
        }

        public void setDetails(String details) {
            this.details.set(details);
        }

        public String getMerchant() {
            return merchant.get();
        }

        public SimpleStringProperty merchantProperty() {
            return merchant;
        }

        public void setMerchant(String merchant) {
            this.merchant.set(merchant);
        }

        public String getAmount() {
            return amount.get();
        }

        public SimpleStringProperty amountProperty() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount.set(amount);
        }

        public String getReport() {
            return report.get();
        }

        public SimpleStringProperty reportProperty() {
            return report;
        }

        public void setReport(String report) {
            this.report.set(report);
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}