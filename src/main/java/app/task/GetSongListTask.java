package app.task;

import app.controller.content.SongListController;
import app.model.NetEaseMusic;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import app.util.MusicUtils;

/**
 * @author von
 * created on 2020-09-28 15:35
 * description:
 * 获得歌单的歌曲列表
 */
public class GetSongListTask extends Task<ObservableList<NetEaseMusic>> {
    
    private final String url;
    
    public GetSongListTask(String url) {
        this.url = url;
    }
    
    @Override
    protected void succeeded() {
        super.succeeded();
        ObservableList<NetEaseMusic> value = getValue();
        SongListController.updatePlayListSongs(value);
    }
    
    @Override
    protected ObservableList<NetEaseMusic> call() throws Exception {
        return MusicUtils.getNetEaseSongInfo(url);
    }
}
