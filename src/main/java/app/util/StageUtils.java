package app.util;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 * @author von
 * created on 2020-10-01 09:21
 * description:
 */
public class StageUtils {
    
    /**
     * 缩放至原比例显示
     * @param pane
     */
    public static void show(Pane pane){
        Scale scale = new Scale(1.0, 1.0, pane.getWidth() / 2, pane.getHeight() / 2);
        pane.getTransforms().add(scale);
    }
    
    /**
     * 缩放至0隐藏
     * @param pane
     */
    public static void hide(Pane pane){
        Scale scale = new Scale(0.0, 0.0, pane.getWidth() / 2, pane.getHeight() / 2);
        pane.getTransforms().add(scale);
    }
}
