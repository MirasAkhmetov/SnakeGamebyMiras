import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Window extends Pane {

    static Button button1;
    static Button button2;

    public Window(){
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        Label wel = new Label("   SNAKE\n   GAME");
        Label label2 = new Label("Play and Enjoy");
        label2.setId("label2");
        grid.add(wel, 0,0);
        button1 = new Button("PLAY");
        button1.setId("button1");

        button1.setMinSize(85,35);
        grid.add(button1, 0, 1);
        button2 = new Button("More");
        button2.setId("button2");
        grid.add(button2, 0, 2);
        button2.setMinSize(85,35);

        Button button3 = new Button("Exit");
        button3.setId("button3");

        grid.add(button3, 0, 3);
        pane.add(grid, 0 ,0);
        pane.add(label2,0,1);
        button3.setMinSize(85,35);

        pane.setTranslateX(90);

        pane.setTranslateY(80);

        getChildren().addAll(pane);


    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
