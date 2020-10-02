package app.controller.center;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 13:51
 * description:
 */
public class MainCenterCenController implements Initializable {
    
    public static MainCenterCenController controller;
    
    @FXML
    public StackPane mainCenterCenterRoot;
    @FXML
    public ScrollPane contentRoot;
    @FXML
    public BorderPane content;
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        contentRoot.prefHeightProperty().bind(mainCenterCenterRoot.prefHeightProperty());
        contentRoot.prefWidthProperty().bind(mainCenterCenterRoot.prefWidthProperty());
    }
    
    /**
     * 更换中间展示的内容
     * @param newContent 要展示的节点
     */
    public static void setContent(BorderPane newContent){
        controller.content = null;
        controller.contentRoot.setContent(newContent);
        newContent.prefWidthProperty().bind(controller.contentRoot.widthProperty());
        newContent.prefHeightProperty().bind(controller.contentRoot.heightProperty());
    }
    
}
