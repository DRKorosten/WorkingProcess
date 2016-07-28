import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
    private MainController mainController;
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Font.loadFont(getClass().getResource("/sample/registration/view/registration.ttf").toExternalForm(),15);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        mainController.start();
        primaryStage.setTitle("Main");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1050);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
