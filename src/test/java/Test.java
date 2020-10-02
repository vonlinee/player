import app.task.GetSongSheetInfoTask;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * @author von
 * created on 2020-09-28 15:38
 * description:
 */
public class Test extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    
        BorderPane root = new BorderPane();
    
    
        HBox hBox = new HBox();
    
        Button b1 = new Button("获得歌单");
        
        
        hBox.getChildren().addAll(b1);
    
        
        String url = "https://music.163.com/playlist?id=5215822980";
        
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override //JavaFX Application Thread
            public void handle(ActionEvent event) {
                GetSongSheetInfoTask task = new GetSongSheetInfoTask("全部");
                new Thread(task).start();
            }
        });
        
        
        
        
        
        
        
        
        root.setTop(hBox);
        Scene scene = new Scene(root,600,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("音乐播放器");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
