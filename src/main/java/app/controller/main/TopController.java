package app.controller.main;

import app.MainLauncher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 21:16
 * description:
 */
public class TopController implements Initializable {
    
    public BorderPane mainTopRoot;//包裹标题文字和最小化、最大化、关闭/退出按钮的BorderPane
    
    public Label logoLabel;
    
    public TextField searchInput;
    
    public HBox topBar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    public void exit(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            System.exit(0);
        }
    }
    

    

    @FXML
    public void maxmize(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            Rectangle2D rectangle2d = Screen.getPrimary().getVisualBounds();
            MainLauncher.isMax = !MainLauncher.isMax;
            if (MainLauncher.isMax) {
                // 最大化
                MainController.stage.setX(rectangle2d.getMinX());
                MainController.stage.setY(rectangle2d.getMinY());
                MainController.stage.setWidth(rectangle2d.getWidth());
                MainController.stage.setHeight(rectangle2d.getHeight());
            } else {
                // 缩放回原来的大小
                MainController.stage.setX(MainLauncher.stageX);
                MainController.stage.setY(MainLauncher.stageX);
                MainController.stage.setWidth(MainLauncher.width);
                MainController.stage.setHeight(MainLauncher.height);
            }
        }
    }
    
    @FXML
    public void minmize(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            MainController.stage.setIconified(true);
        }
    }
    
    public void openSettingsPane(MouseEvent mouseEvent) {
        System.out.println("打开设置面板");
    }
}
