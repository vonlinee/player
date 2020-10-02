package app.controller.center;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author von
 * created on 2020-09-28 13:25
 * description:
 */
public class MainCenterController implements Initializable {
    
    @FXML
    public VBox mainCenterLeft;
    @FXML
    public StackPane mainCenterCenter;
    @FXML
    public BorderPane mainCenterRoot;
    
    public void initialize(URL location, ResourceBundle resources) {
        mainCenterLeft.prefHeightProperty().bind(mainCenterRoot.prefHeightProperty());
        mainCenterCenter.prefHeightProperty().bind(mainCenterRoot.prefHeightProperty());
    }
}
