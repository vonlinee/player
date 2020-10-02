package app.controller.center;

import app.model.NetEasePlayList;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-10-01 20:09
 * description:
 */
public class RecomendListController implements Initializable {
    
    public FlowPane recomendListPane;
    
    public StackPane display;
    
    public SubScene imageSubScene;
    
    public HBox imageControll;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recomendListPane.prefWidthProperty().bind(display.prefWidthProperty());
    }
    
    /**
     * 初始化推荐歌单列表
     */
    private void initRecomendListPane() throws IOException {
        List<NetEasePlayList> list = new ArrayList<>();
    
        Document document = Jsoup.connect("https://music.163.com").get();
        Elements ul = document.select(".m-cvrlst f-cb");
        for (Element element : ul) {
            System.out.println(element);
        }

    }
    
    public static void main(String[] args) {
        
    }
    
}
