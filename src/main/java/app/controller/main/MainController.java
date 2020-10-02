package app.controller.main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 12:50
 * description:
 */
public class MainController implements Initializable {
    
    public static Stage stage;
    
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public BorderPane top;
    @FXML
    public BorderPane bottom;
    @FXML
    public BorderPane center;
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        center.prefHeightProperty().bind(mainBorderPane.prefHeightProperty());
        
        addDragMoveStage(top);
    }
    
    
    
    // 定义偏移量，用于处理窗口移动
    private double xOffset = 0;
    private double yOffset = 0;
    
    /**
     * 添加拖动该控件，移动整个窗口事件
     * @param target
     */
    private void addDragMoveStage(BorderPane target){
        target.setOnMousePressed(new EventHandler<MouseEvent>() {
            /*
             * 鼠标按下时，记下鼠标按下位置相对于 root左上角(0,0) 的 x, y坐标, 也就是x偏移量 = x - 0, y偏移量 = y - 0
             */
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
                // root.startFullDrag();
            }
        });
        target.setOnMouseDragged(new EventHandler<MouseEvent>() {
            /*
             * 根据偏移量设置primaryStage新的位置
             */
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    
    
}
