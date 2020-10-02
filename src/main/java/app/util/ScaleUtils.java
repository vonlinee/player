package app.util;

import javafx.scene.Node;

/**
 * @author von
 * created on 2020-10-01 18:29
 * description:
 */
public class ScaleUtils {
    
    /**
     * 默认以中心点进行缩放
     * @param node
     * @param scaleX
     * @param scaleY
     */
    public static void scaleTo(Node node, Double scaleX, Double scaleY){
        node.setScaleX(scaleX);
        node.setScaleY(scaleY);
    }
    
    /**
     * 按指定坐标进行缩放
     * @param node
     * @param x
     * @param y
     * @param scaleX
     * @param scaleY
     */
    public static void scaleTo(Node node,double x,double y, Double scaleX, Double scaleY){
        node.setScaleX(scaleX);
        node.setScaleY(scaleY);
    }
    
    
    /**
     * 缩放为0至隐藏，且失去父控件托管
     * @param node
     */
    public static void scaleToHide(Node node){
        node.setManaged(false);
        node.setScaleX(0.0);
        node.setScaleY(0.0);
    }
    
    /**
     * 缩放为1至原大小，且获得父控件托管
     * @param node
     */
    public static void scaleToShow(Node node){
        node.setManaged(true);
        node.setScaleX(1.0);
        node.setScaleY(1.0);
    }
}
