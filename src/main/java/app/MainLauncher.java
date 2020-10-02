package app;

import app.controller.main.MainController;
import app.task.GetSongSheetInfoTask;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author von
 * created on 2020-09-25 00:12
 * description:
 */
public class MainLauncher extends Application {
    
    public static boolean isMax = false;
    
    public static double width = 0.00;
    
    public static double height = 0.00;
    
    public static double stageX = 0.00;
    
    public static double stageY = 0.00;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        MainController.stage = primaryStage;
        StackPane root = FXMLLoader.load(getClass().getResource("/fxml/main/main-root.fxml"));
        Scene scene = new Scene(root);
        
        //设置背景:根节点设为透明
        BackgroundFill bgFill = new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), new Insets(0));
        Background bg = new Background(bgFill);
        root.setBackground(bg);
    
        Image image = new Image("/img/netease.png");
        primaryStage.getIcons().add(image);
        addStageSizeListener(primaryStage);
        //设置场景图为全透明
        scene.setFill(Paint.valueOf("#fafafa00"));//最后2位数字是透明度，00代表全透明
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("音乐播放器");
        primaryStage.show();
        
        new Thread(new GetSongSheetInfoTask("全部")).start();
    }
    
    
    private void addStageSizeListener(Stage stage){
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && !isMax) {
                    width = newValue.doubleValue();
                }
            }
        });
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && !isMax) {
                    height = newValue.doubleValue();
                }
            }
        });
        stage.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && !isMax) {
                    stageX = newValue.doubleValue();
                }
            }
        });
        stage.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && !isMax) {
                    stageY = newValue.doubleValue();
                }
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
