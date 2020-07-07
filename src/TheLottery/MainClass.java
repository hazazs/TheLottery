package TheLottery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainClass extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TheLotteryView.fxml"));
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/TheLottery.png")));
        stage.setTitle("TheLottery 1.6.0 by hazazs");
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}