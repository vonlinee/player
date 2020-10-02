package app.util;

import app.model.LocalMusic;
import app.model.Music;
import app.model.NetEaseMusic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.client.params.AllClientPNames;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author von
 * created on 2020-09-25 00:07
 * description:
 */
public class MusicUtils {
    
    public static final String NETEASE_SONG_PREFIX = "http://music.163.com/song/media/outer/url?id=";
    
    public static final String MP3_FILE_SUFFIX = ".mp3";
    
    //存放 ffmpeg.exe 的文件夹路径
    private static final String FFMPEG_PATH = "src/main/resources/tool";
    
    //本地音乐根目录
    public static final String ROOT_PATH = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath().replaceAll("\\\\", "/") + "/test/";
    
    /**
     * 歌单歌曲名称和 ID 列表
     */
    public static ArrayList<Music> playListNameAndIds = new ArrayList<>();
    
    /**
     * 获得本地音乐文件夹中名称列表
     * 由于 ffmpeg 中文件不能包含空格，因此将所有文件去空格重命名后操作
     * @param path 本地音乐文件夹路径
     */
    public static ObservableList<LocalMusic> getLocalMusic(String path) {
        ObservableList<LocalMusic> localMusicList = FXCollections.observableArrayList();
        File[] list = new File(path).listFiles();
        // 有效歌曲计数
        int id = 0;
        for (File f : Objects.requireNonNull(list)) {
            String allName = f.getName();
            String newAllName = replaceLegalization(allName);
            String name = allName.substring(0, allName.lastIndexOf("."));
            String newName = replaceLegalization(name);
            String suffix = getSuffix(allName);
            String musicPath = f.getPath();
            String newPath = convertLastString(musicPath, name, newName);
            renameFile(musicPath, name);
            if ("wav".equals(suffix) || "mp3".equals(suffix) || "flac".equals(suffix)) {
                if (!"mp3".equals(suffix)) {
                    File tmp = new File(convertLastString(newPath, suffix, "mp3"));
                    if (!tmp.exists()) {
                        convertToMp3(newPath);
                    }
                }
                LocalMusic lm = getLocalMusicInfo(convertLastString(newPath, suffix, "mp3"), id + 1);
                lm.setURI(f.toURI().toString());
                localMusicList.add(lm);
                id++;
            }
        }
        return localMusicList;
    }
    
    /**
     * 解析本地歌曲的时长，专辑，大小
     * @param path 歌曲路径
     * @param id   当前个数
     * @return 音乐实体
     */
    private static LocalMusic getLocalMusicInfo(String path, int id) {
        MP3File file = null;
        try {
            file = new MP3File(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MP3AudioHeader header = Objects.requireNonNull(file).getMP3AudioHeader();
        AbstractID3v2Tag tag = file.getID3v2Tag();
        String index = String.format("%03d", id);
        String size = getMb(new File(path).length());
        String time = convertToMinAndSec(header.getTrackLength());
        if (tag.frameMap.get("TIT2") != null) {
            String name = tag.getFirst(FieldKey.TITLE);
            String author = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            LocalMusic localMusic = new LocalMusic(index, name, author, album, size, time);
            return localMusic;
        } else {
            return new LocalMusic(index, "未知歌名", "未知歌手", "未知专辑", size, time);
        }
    }
    
    /**
     * 将秒数转换为分秒格式
     * @param total 总秒数
     * @return 分秒格式
     */
    public static String convertToMinAndSec(double total) {
        return String.format(" %02d :" + " %02d", (int) total / 60, (int) total % 60);
    }
    
    /**
     * 转换歌曲大小为 mb 单位
     * @param size b 单位
     * @return mb 单位
     */
    public static String getMb(double size) {
        return String.format("%.2fMB", size / 1024 / 1024);
    }
    
    /**
     * 替换所有空格
     * @param origin 原始字串
     * @return 替换后的结果
     */
    private static String replaceLegalization(String origin) {
        return origin.replaceAll(" ", "").replaceAll("\\u00A0", "");
    }
    
    /**
     * 获得路径后缀
     * @param path 路径
     * @return 后缀
     */
    private static String getSuffix(String path) {
        return path.substring(path.lastIndexOf(".")+1);
    }
    
    /**
     * 替换最后一个符合条件的字符串
     * @param string  原始字串
     * @param match   被匹配的字串
     * @param replace 替换字串
     * @return 替换后的字串
     */
    private static String convertLastString(String string, String match, String replace) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int lastIndexOf = stringBuilder.lastIndexOf(match);
        if (-1 == lastIndexOf) {
            return string;
        }
        return stringBuilder.replace(lastIndexOf, lastIndexOf + match.length(), replace).toString();
    }
    
    /**
     * 重命名为去掉空格的文件
     * @param path 原路径
     * @param name 原文件名
     */
    private static void renameFile(String path, String name) {
        String newName = replaceLegalization(name);
        String newPath = convertLastString(path, name, newName);
        new File(path).renameTo(new File(newPath));
    }
    
    /**
     * 音频转换为 mp3 格式
     * @param path 转换的文件路径
     */
    private static void convertToMp3(String path) {
        String suffix = getSuffix(path);
        String to = convertLastString(path, suffix, "mp3");
        Runtime run = null;
        long start = System.currentTimeMillis();
        try {
            run = Runtime.getRuntime();
            Process p = run.exec(new File(FFMPEG_PATH).getAbsolutePath() + "/ffmpeg -y -i " + path + " -ar 44100 -ac 2 -acodec mp3 -ab 128k " + to);
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(run).freeMemory();
        }
        boolean b = deleteMusic(path);
    }
    
    /**
     * 删除本地音乐
     * @param url 音乐地址
     */
    private static boolean deleteMusic(String url) {
        return new File(url).delete();
    }
    
    /**
     * 从本地加载
     * @param path  文件路径
     * @return
     */
    public static Media loadMediaFromLocal(String path){
        File file = new File(path);
        return new Media(file.toURI().toString());
    }
    
    /**
     * 从网络加载
     * @param url  
     * @return
     */
    public static Media loadMediaFromNet(String url){
        return new Media(url);
    }
    
    
    /**
     * 下一首网易云歌曲
     * @return 歌曲信息
     */
    public static Media initNetEaseMusic(Music music) {
        String real = getRealURLPath("http://music.163.com/song/media/outer/url?id=" + music.getAuthor() + ".mp3");
        if("https://music.163.com/404".equals(real)){
            System.out.println("此单曲为付费单曲无法播放");
        }
        return new Media(real);
    }
    
    /**
     * 获取 302 跳转后的真实地址
     * @param url 原始地址
     * @return 真实地址
     */
    public static String getRealURLPath(String url) {
        try {
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(url);
            HttpClientParams params = client.getParams();
            params.setParameter(AllClientPNames.HANDLE_REDIRECTS, false);
            client.executeMethod(method);
            return method.getURI().getURI();
        } catch (Exception e) {
            e.printStackTrace();
            return url;
        }
    }
    
    /**
     * 获得网易云歌单中歌曲的信息
     */
    public static ObservableList<NetEaseMusic> getNetEaseSongInfo(String listUrl) {
        playListNameAndIds = ConnectUtil.getNetEaseListSong(listUrl);
        ObservableList<NetEaseMusic> data = FXCollections.observableArrayList();
        for (int i = 0; i < playListNameAndIds.size(); i++) {
            Music music = playListNameAndIds.get(i);
            data.add(new NetEaseMusic(String.format("%03d", i + 1), music.getName(), music.getAuthor(), "album", "time"));
        }
        return data;
    }
    
}
