import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import app.util.MusicPlayer;

import java.io.File;

public class Test1 extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("音乐播放器");
        BorderPane bp = new BorderPane();
        HBox hBox = new HBox();
    
        Button playBtn = new Button("play");
        Button pauseBtn = new Button("pause");
        Button nextBtn = new Button("next");
        Button previousBtn = new Button("previous");
        Button stopBtn = new Button("stop");
        Button resetBtn = new Button("reset");
        Button disposeBtn = new Button("dispose");
        
        hBox.getChildren().addAll(previousBtn,playBtn,nextBtn,pauseBtn,stopBtn,resetBtn,disposeBtn);
    
        File file = new File("E:\\Download\\mp3");
    
        Media media = new Media("file:/E:/Download/mp3/金孝琳-안녕(再见).mp3");

        MusicPlayer.init(media);
        
        
        playBtn.setOnAction(event -> {
            MusicPlayer.play();
        });
        pauseBtn.setOnAction(event -> {
            MusicPlayer.pause();
        });
        stopBtn.setOnAction(event -> {
            MusicPlayer.stop();
        });

        
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        
        HBox currentTime = new HBox();
        Label text = new Label("currentTime:");
        Label currentTimeLabel = new Label("");
        currentTime.getChildren().addAll(text,currentTimeLabel);
    
        HBox currentStatus = new HBox();
        Label textStatus = new Label("currentStatus:");
        Label currentStatusLabel = new Label("");
        currentStatus.getChildren().addAll(textStatus,currentStatusLabel);
        
        
        currentStatusLabel.textProperty().bind(MusicPlayer.statusProperty());
        currentTimeLabel.textProperty().bind(MusicPlayer.currentTime());
        

        
        vBox.getChildren().addAll(currentTime,currentStatus);
    
    
        bp.setTop(hBox);
        bp.setCenter(vBox);
        
        
        
        primaryStage.setScene(new Scene(bp,800,600));
        primaryStage.show();
        
        
    }
}
