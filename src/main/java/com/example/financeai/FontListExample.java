package com.example.financeai;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FontListExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 创建一个 VBox 用于存放字体示例
        VBox fontBox = new VBox(10); // 间距为 10 像素
        fontBox.setStyle("-fx-padding: 10;");

        // 获取所有可用字体家族并为每个字体创建一个 Label
        for (String fontFamily : Font.getFamilies()) {
            // 创建一个 Label，显示字体名称，并应用该字体
            Label fontLabel = new Label(fontFamily);
            try {
                fontLabel.setFont(Font.font(fontFamily, 16)); // 设置字体和大小
            } catch (Exception e) {
                // 如果字体加载失败，使用默认字体
                fontLabel.setFont(Font.font("System", 16));
            }
            fontBox.getChildren().add(fontLabel);
        }

        // 使用 ScrollPane 包裹 VBox 以支持滚动
        ScrollPane scrollPane = new ScrollPane(fontBox);
        scrollPane.setFitToWidth(true); // 让内容适应宽度
        scrollPane.setStyle("-fx-background-color: white;");

        // 设置场景
        Scene scene = new Scene(scrollPane, 400, 600);

        // 配置舞台
        primaryStage.setTitle("Available Fonts with Preview");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}