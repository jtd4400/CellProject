package cell_project_dir;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class CellApplication extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Cell Simulation");
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        gp.setHgap(0);
        gp.setVgap(0);
        gp.setMaxWidth(150);
        gp.setMaxHeight(150);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Label l = new Label();
                l.setBackground(Color.RED);
                l.setBounds(15,15,15,15);
            }
        }
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
