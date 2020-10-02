package app.controller.content;

import app.controller.center.MainCenterLeftController;
import app.model.NetEaseMusic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import app.util.MusicPlayer;
import app.util.MusicUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 14:45
 * description:
 */
public class SongListController implements Initializable {
    
    public static final HashMap<String,String> datas = new HashMap<>();
    
    public static SongListController controller;
    
    public HBox songListDetail;
    
    public BorderPane contentRoot;
    
    public TableView<NetEaseMusic> netSongsList;
    
    public ImageView playListImage;
    
    public VBox introduction;
    
    public TableColumn<NetEaseMusic, String> netEaseId;
    
    public TableColumn<NetEaseMusic, String> netEaseTitle;
    
    public TableColumn<NetEaseMusic, String> netEaseAuthor;
    
    public TableColumn<NetEaseMusic, String> netEaseAlbum;
    
    public TableColumn<NetEaseMusic, String> netEaseTime;
    
    public Label playListInfo;
    
    public Label playListTitle;
    
    
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        //双击播放
        bindNetEaseMusicClicked();
    }
    
    /**
     * 更新网易云歌单音乐 table view
     * @param data 数据组
     */
    public static void updatePlayListSongs(ObservableList<NetEaseMusic> data) {
        controller.playListTitle.setText(datas.get("title"));
        controller.playListInfo.setText(datas.get("info"));
        controller.playListImage.setImage(new Image(datas.get("picUrl")));
        Platform.runLater(() -> {
            controller.netEaseId.setCellValueFactory(new PropertyValueFactory<>("id"));
            controller.netEaseTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            controller.netEaseAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            controller.netEaseAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
            controller.netEaseTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            controller.netSongsList.setItems(data);
        });
    }
    
    /**
     * 双击播放歌单歌曲
     */
    private void bindNetEaseMusicClicked() {
        netSongsList.setRowFactory(netEaseMusicTableView -> {
            TableRow<NetEaseMusic> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int index = row.getIndex();
                    NetEaseMusic music = row.getTableView().getItems().get(index);
                    String real = MusicUtils.getRealURLPath(MusicUtils.NETEASE_SONG_PREFIX + music.getAuthor() + MusicUtils.MP3_FILE_SUFFIX);
                    MusicPlayer.play(real);
                    MusicPlayer.setPlayList(getPlayList(datas));
                    //更新当前播放的歌曲信息
                    
                }
            });
            return row;
        });
    }
    
    private ObservableList<String> getPlayList(HashMap<String,String> datas){
        ObservableList<String> observableList = FXCollections.observableList(new ArrayList<>(20));
        for (String key:datas.keySet()){
            observableList.add(datas.get(key));
        }
        return observableList;
    }
    
    private void updeateCurrentPlay(){

    }
    
}
