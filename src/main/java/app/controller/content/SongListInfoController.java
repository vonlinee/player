package app.controller.content;

import app.model.LocalMusic;
import app.util.MusicPlayer;
import app.util.MusicUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-27 10:39
 * description:
 */
public class SongListInfoController implements Initializable {
    
    public static SongListInfoController controller;
    
    public ObservableList<LocalMusic> localMusicTableData;
    
    @FXML
    public BorderPane playListsDetailsRootPane;
    @FXML
    public ImageView playListImage;
    @FXML
    public Label playListTitle;
    @FXML
    public Label playListInfo;
    @FXML
    public HBox songListInfoBox;
    @FXML
    public VBox listDetail;
    @FXML
    public TableView<LocalMusic> localMusicTable;
    @FXML
    public TableColumn<LocalMusic, String> songId;
    @FXML
    public TableColumn<LocalMusic, String> songName;
    @FXML
    public TableColumn<LocalMusic, String> singerName;
    @FXML
    public TableColumn<LocalMusic, String> albumName;
    @FXML
    public TableColumn<LocalMusic, String> totalTime;
    @FXML
    public TableColumn<LocalMusic, String> totalSize;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        initNodeSize();
        initTableColumn();
        updateLocalTable();
    }
    
    /**
     * 初始化列表数据
     */
    public void initTableColumn(){
        songId.setCellValueFactory(new PropertyValueFactory<>("id"));
        songName.setCellValueFactory(new PropertyValueFactory<>("name"));
        singerName.setCellValueFactory(new PropertyValueFactory<>("author"));
        albumName.setCellValueFactory(new PropertyValueFactory<>("album"));
        totalSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        totalTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        bindLocalMusicClicked();
    }
    
    
    private void initNodeSize(){
        songListInfoBox.prefHeightProperty().bind(playListsDetailsRootPane.heightProperty().divide(3));
        playListImage.setFitWidth(songListInfoBox.getWidth()/2);
        listDetail.prefWidthProperty().bind(songListInfoBox.widthProperty().divide(2));
        localMusicTable.prefWidthProperty().bind(playListsDetailsRootPane.widthProperty());
        songId.prefWidthProperty().bind(localMusicTable.prefWidthProperty().divide(5));
        songName.prefWidthProperty().bind(localMusicTable.prefWidthProperty().divide(5));
        singerName.prefWidthProperty().bind(localMusicTable.prefWidthProperty().divide(5));
        albumName.prefWidthProperty().bind(localMusicTable.prefWidthProperty().divide(5));
        totalTime.prefWidthProperty().bind(localMusicTable.prefWidthProperty().divide(5));
        totalSize.prefWidthProperty().bind(localMusicTable.prefWidthProperty().divide(5));
        playListImage.setFitWidth(150);
        playListImage.setFitHeight(150);
    }
    
    
    /**
     * 双击播放歌单歌曲
     */
    private void bindLocalMusicClicked() {
        localMusicTable.setRowFactory(netEaseMusicTableView -> {
            TableRow<LocalMusic> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TableView<LocalMusic> tableView = row.getTableView();
                    LocalMusic localMusic = tableView.getItems().get(row.getIndex());
                    MusicPlayer.play(localMusic.getURI());
                }
            });
            return row;
        });
    }
    
    /**
     * 开始更新本地歌曲列表
     */
    public void updateLocalTable(){
        ObservableList<LocalMusic> localMusic = MusicUtils.getLocalMusic(MusicUtils.ROOT_PATH);
        localMusicTableData = localMusic;
        localMusicTable.setItems(localMusic);
    }
}
