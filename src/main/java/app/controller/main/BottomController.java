package app.controller.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import app.util.MusicPlayer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 20:59
 * description:
 */
public class BottomController implements Initializable {
    
    public static BottomController controller;
    
    @FXML
    public BorderPane mainBottomRoot;
    @FXML
    public Label currentTime;
    @FXML
    public Label totalTime;
    @FXML
    public SVGPath sound;
    
    public SVGPath previous;
    
    public SVGPath playOrPauseSvg;
    
    public SVGPath next;
    
    public HBox playControllBox;
    
    public HBox playModelBox;
    
    public HBox playStatus;
    
    public AnchorPane progressPane;
    
    public Rectangle progressRectangle;
    
    public Slider progressSlider;
    
    public AnchorPane soundPane;
    
    public Rectangle soundRectangle;
    
    public Slider soundSlider;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        double v = mainBottomRoot.getWidth() - playControllBox.getPrefWidth() - playModelBox.getPrefWidth();
        playStatus.prefWidthProperty().bind(mainBottomRoot.prefWidthProperty().subtract(v));
        double width = currentTime.getPrefWidth() + totalTime.getPrefWidth();
        // 播放进度滑动条
        progressPane.prefWidthProperty().bind(playStatus.prefWidthProperty().subtract(width));
        progressSlider.prefWidthProperty().bind(progressPane.widthProperty());
        progressRectangle.heightProperty().bind(progressSlider.heightProperty().subtract(12));
        progressRectangle.widthProperty().bind(progressSlider.widthProperty());
        //声音滑动面板
        soundPane.prefWidthProperty().bind(playModelBox.prefWidthProperty().divide(3));
        soundSlider.prefWidthProperty().bind(soundPane.widthProperty());
        soundRectangle.heightProperty().bind(soundSlider.heightProperty().subtract(12));
        soundRectangle.widthProperty().bind(soundSlider.widthProperty());
        soundRectangle.setStyle(formatStyle("#5dc78a",20.0));
        initliderListener(progressSlider,progressRectangle,"#5dc78a");
        initliderListener(soundSlider,soundRectangle,"#5dc78a");
        
        initBind();
    }
    
    public void previous(MouseEvent mouseEvent) {
        MusicPlayer.pause();
    }
    
    public void togglePlay(MouseEvent mouseEvent) {
        String url = "file:/E:/Download/mp3/金孝琳-안녕(再见).mp3";
        MusicPlayer.play(url);
        // MusicPlayer.playAndPause();
    }
    
    public void next(ActionEvent actionEvent) {
        
    }
    
    public void toggleSound(MouseEvent mouseEvent) {
        
    }
    
    public void toggleLyricPane(ActionEvent actionEvent) {
        
    }
    
    /**
     * 初始化数据绑定
     */
    private void initBind(){
        currentTime.textProperty().bind(MusicPlayer.currentTime());
        totalTime.textProperty().bind(MusicPlayer.totalTime());
        progressSlider.valueProperty().bind(MusicPlayer.currentPlayProgress().multiply(100));
        soundSlider.valueProperty().bindBidirectional(MusicPlayer.volume());
    }
    
    
    /**
     * 添加监听器
     * @param slider
     * @param progress
     * @param color
     */
    private void initliderListener(Slider slider, Rectangle progress, String color){
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double max = slider.getMax();//默认100
                double v = (double) newValue / max * 100;
                //设置线性渐变
                String format = formatStyle(color, v);
                progress.setStyle(format);
            }
        });
    }
    
    private static final String LINEAR_GRADIENT_PREFFIX = "-fx-fill: linear-gradient(to right, ";
    
    private static final String STYLE_FORMAT_SUFFIX = " %f%%, #c3c3c3 %f%%);";
    
    /**
     * 设置百分比值之前部分的颜色
     * @param colorCode 颜色代码：track部分
     * @param percent   百分比
     * @return          线性渐变样式字符串
     */
    private String formatStyle(String colorCode,double percent){
        return String.format(LINEAR_GRADIENT_PREFFIX + colorCode + STYLE_FORMAT_SUFFIX , percent , percent);
    }
    
    /**
     * 关闭根控件，缩小为0
     * @param rootPane 根控件
     */
    private void closeRootPane(AnchorPane rootPane) {
        rootPane.setScaleX(0);
        rootPane.setScaleY(0);
    }
    
    /**
     * 打开根控件
     * @param rootPane 根控件
     */
    private void openRootPane(AnchorPane rootPane) {
        rootPane.setScaleX(1);
        rootPane.setScaleY(1);
    }

}
