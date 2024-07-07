package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that starts our application.
 *
 * @author mjo, Joshua-Scott Schöttke
 */
public class ApplicationMain extends Application {

    /**
     * Creating the stage and showing it. This is where the initial size and the
     * title of the window are set.
     *
     * @param stage the stage to be shown
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(ApplicationMain.class.getResource("UserInterface"
                + ".fxml"));
        stage.setMinHeight(640);
        stage.setMinWidth(1275);
        Scene scene = new Scene(fxmlLoader);
        stage.setTitle("Welcome to Racetrack!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method
     *
     * @param args unused
     */
    public static void main(String... args) {
        launch(args);
    }
}
