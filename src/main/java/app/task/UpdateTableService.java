package app.task;

import app.controller.content.SongListInfoController;
import app.model.LocalMusic;
import app.util.MusicUtils;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * 更新歌曲列表
 */
public class UpdateTableService extends Service<ObservableList<LocalMusic>> {
    
    static boolean isFinished = true;
    
    private static final String LOAD_ROOT_PATH = "E:\\Download\\mp3";
    
    private String tableId;//表格控件的Id
    
    public UpdateTableService() {
        super();
    }
    
    @Override
    protected void succeeded() {
        System.out.println(getValue());
        SongListInfoController.controller.localMusicTableData = getValue();
    }
    
    @Override
    protected Task<ObservableList<LocalMusic>> createTask() {
        return new Task<ObservableList<LocalMusic>>() {
            @Override
            protected ObservableList<LocalMusic> call() throws Exception {
                ObservableList<LocalMusic> datas = null;
                if (isFinished) {
                    isFinished = false;
                    datas = MusicUtils.getLocalMusic(LOAD_ROOT_PATH);
                    isFinished = true;
                }
                return datas;
            }
        };
    }

}