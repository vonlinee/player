package app.util;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * @author von
 * created on 2020-09-27 13:56
 * description:
 */
public final class MusicPlayer{
    //用于初始化MediaPlayer
    private static final String INITIAL_MEDIA_PATH = "/tool/init.mp3";
    //当前播放音乐的来源,默认本地播放音乐
    private static boolean isLocalMusic = true;
    //默认从歌单第一首开始播放
    private static final StringProperty expectedSongProperty = new SimpleStringProperty("");
    //播放进度
    private static final SimpleDoubleProperty playProgressProperty = new SimpleDoubleProperty(0.0);
    //总时间
    private static final SimpleStringProperty totalTimeProperty = new SimpleStringProperty("");
    //当前时间
    private static final SimpleStringProperty currentTimeProperty = new SimpleStringProperty("");
    //音量
    private static final SimpleDoubleProperty volumeProperty = new SimpleDoubleProperty(0.2);
    //当前播放列表:用list存储
    private static ObservableList<String> currentPlayListProperty = FXCollections.observableList(new ArrayList<>());
    //播放模式:0-顺序播放  1-随机播放  2-循环播放
    private static final IntegerProperty playModelProperty = new SimpleIntegerProperty(0);
    //循环次数
    private static final IntegerProperty cycleCountProperty = new SimpleIntegerProperty(0);
    //状态
    private static final StringProperty statusProperty = new SimpleStringProperty("");
    //播放器
    private static MediaPlayer player;
    //当前播放的Media
    private static Media media;
    
    private int currentIndex = 0;
    
    private int count;
    
    //第一次初始化操作
    static {
        media = new Media(MusicPlayer.class.getResource(INITIAL_MEDIA_PATH).toExternalForm());
        player = new MediaPlayer(media);
        player.setVolume(0.2);
        expectedSongProperty.addListener((observable, oldValue, newValue) -> {
            initMediaPlayer(newValue); //由外界改变
        });
    }
    
    private static void checkPlayer(){
        if (player!=null){
            media = null;
            player.dispose();
            media = null;
            player = null;
        }
    }
    
    /**
     * @param url
     */
    private static void initMediaPlayer(String url){
        checkPlayer();
        if (!url.startsWith("file")){
            isLocalMusic = false;
        }
        MusicPlayer.media = new Media(url);
        player = new MediaPlayer(MusicPlayer.media);
        player.setAutoPlay(false);
        player.setVolume(volumeProperty.get());
        registerListener(player);
    }
    
    /**
     * 注册监听
     * @param player
     */
    private static void registerListener(MediaPlayer player){
        player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            currentTimeProperty.set(formatSeconds(newValue.toSeconds()));
            playProgressProperty.set(newValue.toSeconds() / player.getTotalDuration().toSeconds());
        });
        player.setOnEndOfMedia(() -> {
            player.stop();
            playNext();
        });
        player.statusProperty().addListener((observable, oldValue, newValue) -> {
            statusProperty.set(newValue.name());
            switch (newValue){
                case READY:
                    onReady();
                    break;
                case STALLED:
                    System.out.println("STALLED");
                    break;
                case PAUSED:
                    System.out.println("PAUSED");
                    break;
                case STOPPED:
                    System.out.println("STOPPED");
                    break;
                case PLAYING:
                    System.out.println("PLAYING");
                    break;
            }
        });
    }
    
    /**
     * 播放下一首
     */
    private static void playNext() {
        
    }
    
    /**
     * 第2种
     * 第一种：播放和初始化结合在一起
     * 第二种：播放和初始化分开，先初始化，再播放，两个操作是异步的
     */
    public static void init(Media media){
        checkPlayer();
        MusicPlayer.media = media;
        player = new MediaPlayer(media);
        player.setAutoPlay(false);
        player.setVolume(volumeProperty.get());
        registerListener(player);
    }
    
    
    /**
     * 准备就绪事件
     */
    public static void onReady(){
        //总时间
        totalTimeProperty.set(formatSeconds(player.getTotalDuration().toSeconds()));
        player.play();
    }
    
    /**
     * 获取当前时间
     * @return
     */
    public static SimpleStringProperty currentTime(){
        return currentTimeProperty;
    }
    
    /**
     * 获取当前音量
     * @return
     */
    public static SimpleDoubleProperty volume(){
        return volumeProperty;
    }
    
    /**
     * 获取当前总时间
     * @return
     */
    public static SimpleStringProperty totalTime(){
        return totalTimeProperty;
    }
    
    /**
     * 获取当前进度
     * @return
     */
    public static SimpleDoubleProperty currentPlayProgress(){
        return playProgressProperty;
    }
    
    /**
     * 当前播放的歌曲的地址
     * @return
     */
    public static StringProperty currentPlay(){
        return expectedSongProperty;
    }
    
    /**
     * 格式化秒数为分:秒 格式
     * @param seconds
     * @return
     */
    private static String formatSeconds(double seconds) {
        return String.format(" %02d:" + "%02d", (int) seconds / 60, (int) seconds % 60);
    }
    
    /**
     * 初始化播放列表
     * @param playList
     */
    public static void setPlayList(ObservableList<String> playList){
        if (playList!=null){
            currentPlayListProperty = null;
            currentPlayListProperty = playList;
        }
    }
    
    /**
     * 状态
     * @return
     */
    public static StringProperty statusProperty(){
        return statusProperty;
    }
    
    /**
     * 播放指定url的media，并将其所在的列表添加到播放器的播放列表
     * @param url       要播放的歌曲
     * @param playList  所在的播放列表
     */
    public static void play(String url,ObservableList<String> playList){
        expectedSongProperty.set(url);
        setPlayList(playList);
    }
    
    public static void play(String url){
        expectedSongProperty.set(url);
    }
    
    
    public static void play(){
        if (isPlaying()){
            return;
        }
        player.play();
    }
    
    /**
     * 播放/暂停切换
     */
    public static void playAndPause(){
        if (isPlaying()){
            player.pause();
        }
        if (isPaused()){
            player.play();
        }
    }
    
    public static boolean isPlaying(){
        return player.getStatus() == MediaPlayer.Status.PLAYING;
    }
    
    public static boolean isPaused(){
        return player.getStatus() == MediaPlayer.Status.PAUSED;
    }
    
    public static boolean isReady(){
        return player.getStatus() == MediaPlayer.Status.READY;
    }
    
    public static boolean isStopped(){
        return player.getStatus() == MediaPlayer.Status.STOPPED;
    }
    
    /**
     * 暂停
     */
    public static void pause(){
        if (isPlaying()){
            player.pause();
        }
    }
    
    public static void stop(){
        if (isPlaying() || isPaused()){
            player.stop();
        }
    }
    
}
