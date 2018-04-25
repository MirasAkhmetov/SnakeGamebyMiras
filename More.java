import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;




public class More extends Pane {

    static RadioButton easy, medium, hard, expert;
    static Button play;
    public More(){
      play  = new Button("Play");
        GridPane grid = new GridPane();

        grid.setVgap(20);
        Label label = new Label("You can change Level! ;)");
        easy = new RadioButton("Easy");
        medium= new RadioButton("Medium");
        hard= new RadioButton("Hard");
        expert = new RadioButton("Expert");
        ToggleGroup btns=new ToggleGroup();
        easy.setToggleGroup(btns);
        medium.setToggleGroup(btns);
        hard.setToggleGroup(btns);
        expert.setToggleGroup(btns);


        grid.add(label,1,0);
        grid.add(easy,1,1);
        grid.add(medium,1,2);
        grid.add(hard, 1,3);
        grid.add(expert,1,4);
        grid.add(play,1,5);
        easy.setId("radiobutton");
        medium.setId("radiobutton");
        hard.setId("radiobutton");
        expert.setId("radiobutton");


        grid.setAlignment(Pos.CENTER);
        grid.setTranslateY(80);
        grid.setTranslateX(65);

        getChildren().add(grid);
    }
}
