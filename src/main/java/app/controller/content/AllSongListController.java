package app.controller.content;

import app.controller.center.MainCenterCenController;
import app.model.NetEasePlayList;
import app.task.GetSongListTask;
import app.task.GetSongSheetInfoTask;
import app.util.FxmlUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 15:10
 * description:
 */
public class AllSongListController implements Initializable {
    
    private static final HashMap<Integer,String> songSheetUrls = new HashMap<>(60);
    
    public static AllSongListController controller;
    
    public static ArrayList<NetEasePlayList> allSongListData;
    
    @FXML
    public VBox contentRoot;
    @FXML
    public HBox row1;
    @FXML
    public VBox recomendSongList;
    @FXML
    public HBox row2;
    @FXML
    public HBox row3;
    @FXML
    public HBox row4;
    @FXML
    public HBox row5;
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        recomendSongList.getChildren().forEach(node -> ((HBox)node).getChildren().forEach(node1 -> {
            VBox vBox = (VBox) node1;
            vBox.prefWidthProperty().bind(contentRoot.prefWidthProperty().divide(5));
            vBox.prefHeightProperty().bind(vBox.prefWidthProperty());
        }));
        if (allSongListData!=null){
            updateSongSheet(allSongListData);
        }
    }
    
    /**
     * 打开歌单详情页面
     * @param mouseEvent
     */
    public void openSongListDetail(MouseEvent mouseEvent) {
        VBox source = (VBox) mouseEvent.getSource();
        String url = songSheetUrls.get(source.hashCode());
        //这里需要获取点击的歌单的地址，加载列表
        BorderPane songListContent = null;
        try {
            songListContent = FxmlUtils.load("/fxml/content/song-list.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainCenterCenController.setContent(songListContent);
        //获取歌单列表
        GetSongListTask task = new GetSongListTask(url);
        new Thread(task).start();
    }
    
    /**
     * 更新歌单封面
     * @param data
     */
    public static void updateSongSheet(ArrayList<NetEasePlayList> data){
        // 每行歌单个数
        int rowCounts = 5;
        // 遍历所有 hBox ，由于每个 hBox 拥有 rowCounts 个歌单，因此设置步长为 rowCounts
        for (int i = 0; i < data.size(); i += rowCounts) {
            // 第一行不是 hBox ，因此 get 中需要 +1
            HBox hBox = (HBox) AllSongListController.controller.recomendSongList.getChildren().get(i / rowCounts);
            // 遍历 hBox 中的所有 vBox
            for (int j = 0; j < rowCounts; j++) {
                // i 只是 hBox 的下标，要遍历其中的 vBox 还需要 +j
                int index = i + j;
                // 避免歌单没有填满一行的越界异常
                if (index == data.size()) {
                    return;
                }
                String title = data.get(index).getPlayListTitle();
                String picUrl = data.get(index).getPlayListPicUrl();
                String listUrl = data.get(index).getPlayListUrl();
                String info = data.get(index).getPlayListInfo();
                SongListController.datas.put("title",title);
                SongListController.datas.put("picUrl",picUrl);
                SongListController.datas.put("info",info);
                // 打开歌单详情页
                VBox vBox = (VBox) hBox.getChildren().get(j);
                songSheetUrls.put(vBox.hashCode(),listUrl);
                ImageView imageView = (ImageView) vBox.getChildren().get(0);
                Label label = (Label) vBox.getChildren().get(1);
                imageView.setImage(new Image(picUrl, imageView.getFitWidth(), imageView.getFitHeight(), true, false));
            }
        }
    }
    
}
