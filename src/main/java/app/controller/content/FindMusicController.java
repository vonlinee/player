package app.controller.content;

import app.util.FxmlUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-30 19:38
 * description:
 */
public class FindMusicController implements Initializable {
    
    public BorderPane findMusicContent;
    
    public HBox btnGroup;
    
    public VBox findMusicCenterNode;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    //中间部分节点
    private VBox temp;
    
    /**
     * 准备轮播图图片
     * @param list
     */
    public void initImageList(List<Image> list){
        for (int i = 1; i <= 8; i++) {
            URL resource = getClass().getResource("/img/temp/+" + i + ".jpg");
            list.add(new Image(resource.toExternalForm()));
        }
    }
    
    /**
     * 打开歌单标签
     * @param mouseEvent
     */
    @FXML
    public void openAllSongList(MouseEvent mouseEvent) {
        if (mouseEvent.getButton()== MouseButton.PRIMARY){
            VBox songListContent = null;
            try {
                songListContent = FxmlUtils.load("/fxml/content/all-song-list.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            findMusicContent.setCenter(songListContent);
        }
    }
    
    /**
     * 打开个性推荐标签
     * @param mouseEvent
     */
    @FXML
    public void openRecomendList(MouseEvent mouseEvent) {
        if (mouseEvent.getButton()== MouseButton.PRIMARY){
            VBox songListContent = null;
            try {
                songListContent = FxmlUtils.load("/fxml/content/recomend.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            findMusicContent.setCenter(songListContent);
        }
    }
}
