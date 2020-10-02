package app.util;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author von
 * created on 2020-09-17 20:55
 * description:
 */
public class FxmlUtils {
    
    /**
     * 封装加载fxml文件方法
     * @param url
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T load(String url) throws IOException {
        URL resource = FxmlUtils.class.getResource(url);
        return FXMLLoader.load(resource);
    }
    
    /**
     * 获取FxmlLoader
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static FXMLLoader getFxmlLoader(String url) throws MalformedURLException {
        URL resource = FxmlUtils.class.getResource(url);
        return new FXMLLoader(resource);
    }
    
    /**
     * 获取Fxml资源文件对应的Controller
     * @param url
     * @return  initialize会自动在构造函数之后调用
     */
    public static Object getController(String url) throws IOException {
        //FXMLLoader必须使用参数初始化，否则getController会失败
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(url));
        loader.load();
        //这个方法必须在load方法之后调用
        return loader.getController();
    }
    
}
