package app.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author 86181
 */
public class NetEaseMusic {
    //编号
    private SimpleStringProperty id;
    //标题
    private SimpleStringProperty title;
    //歌手
    private SimpleStringProperty author;
    //专辑名
    private SimpleStringProperty album;
    //时长
    private SimpleStringProperty time;
    //资源地址
    private SimpleStringProperty url;
    
    public NetEaseMusic(String id, String title, String author, String album, String time) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.album = new SimpleStringProperty(album);
        this.time = new SimpleStringProperty(time);
    }
    
    public String getId() {
        return id.get();
    }
    
    public SimpleStringProperty idProperty() {
        return id;
    }
    
    public String getUrl() {
        return url.get();
    }
    
    public SimpleStringProperty urlProperty() {
        return url;
    }
    
    public void setId(String id) {
        this.id.set(id);
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public SimpleStringProperty titleProperty() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public String getAuthor() {
        return author.get();
    }
    
    public SimpleStringProperty authorProperty() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author.set(author);
    }
    
    public String getAlbum() {
        return album.get();
    }
    
    public SimpleStringProperty albumProperty() {
        return album;
    }
    
    public void setAlbum(String album) {
        this.album.set(album);
    }
    
    public String getTime() {
        return time.get();
    }
    
    public SimpleStringProperty timeProperty() {
        return time;
    }
    
    public void setTime(String time) {
        this.time.set(time);
    }
}
