package app.task;

import app.controller.content.AllSongListController;
import app.model.NetEasePlayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import app.util.ConnectUtil;

import java.util.ArrayList;

/**
 * @author von
 * created on 2020-09-28 15:52
 * description:
 * 随机获取某一分类的所有歌单的信息
 */
public class GetSongSheetInfoTask extends Task<ArrayList<NetEasePlayList>> {
    
    private boolean isFirst = true;
    
    /**
     * 分类
     */
    private String category;
    
    public GetSongSheetInfoTask(String category) {
        this.category = category;
    }
    
    @Override
    protected void succeeded() {
        super.succeeded();
        //更新UI
        if (isFirst){
            AllSongListController.allSongListData = getValue();
            isFirst = false;
        }
    }
    
    //普通线程
    @Override
    protected ArrayList<NetEasePlayList> call() throws Exception {
        return ConnectUtil.getNetEasePlayList(category);
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

}
