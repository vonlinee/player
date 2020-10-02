package app.controller.center;

import app.util.FxmlUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 13:31
 * description:
 */
public class MainCenterLeftController implements Initializable {
    
    public static MainCenterLeftController controller;
    
    @FXML
    public HBox currentSongInfo;
    @FXML
    public ScrollPane leftSiderBar;
    @FXML
    public VBox mainCenterLeftRoot;
    @FXML
    public VBox tabs;
    @FXML
    public Button recomendTab;
    @FXML
    public Button findMusicTab;
    @FXML
    public Button myMusicTab;
    @FXML
    public ImageView songImg;
    
    public Label singerName;
    
    public Label songName;
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        leftSiderBar.prefHeightProperty().bind(mainCenterLeftRoot.heightProperty().subtract(currentSongInfo.getPrefHeight()));
        leftSiderBar.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);//控制横向不可滚动
        recomendTab.prefWidthProperty().bind(mainCenterLeftRoot.prefWidthProperty());
        findMusicTab.prefWidthProperty().bind(mainCenterLeftRoot.prefWidthProperty());
        myMusicTab.prefWidthProperty().bind(mainCenterLeftRoot.prefWidthProperty());
    }
    
    /**
     * 切换MainCenterCenter的ScrollPane的子节点
     * 发现音乐
     * @param mouseEvent
     */
    @FXML
    public void openFindMusicPane(MouseEvent mouseEvent) {
        //加载find-music.fxml:这里也可选择使用代码生成控件
        BorderPane findMusicContent = null;
        try {
            findMusicContent = FXMLLoader.load(getClass().getResource("/fxml/content/find-music.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainCenterCenController.setContent(findMusicContent);
    }
    
    /**
     * 我的音乐
     * @param mouseEvent
     */
    @FXML
    public void openMyMusicPane(MouseEvent mouseEvent) {
        //加载find-music.fxml:这里也可选择使用代码生成控件
        BorderPane localMusicContent = null;
        try {
            localMusicContent = FXMLLoader.load(getClass().getResource("/fxml/center/song-list-info.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainCenterCenController.setContent(localMusicContent);
    }
    
    
    @FXML
    public void showSize(MouseEvent mouseEvent) {
        
    }
    
    
    private void findMusicTab(){
        //加载find-music.fxml:这里也可选择使用代码生成控件
        BorderPane findMusicContent = null;
        try {
            findMusicContent = FxmlUtils.load("/fxml/content/all-song-list.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainCenterCenController.setContent(findMusicContent);
    }
    
}
