import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.text.Text;


public class Snake extends Application {
    //Adding Directions
   public enum Direction{
        UP, DOWN, LEFT, RIGHT
    }

    //Block_Size-snake's body's size
    public static final int BLOCK_SIZE=15;//preaparing sizes for elements
   //App_W and App_H scene's size
    public static final int APP_W= 40*BLOCK_SIZE;
    public static final int APP_H=30*BLOCK_SIZE;
    public static HBox hbox;
    public static Pane root;

    private Direction direction = Direction.RIGHT;//creates Direction type which determines in which direction snake moves
    private boolean moved = false;
    private boolean running=false;

    Label score=new Label("0");
    Label textScore=new Label("SCORE: ");//Text to show scrore



    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;

    private Parent createContent(){//creates and runs all elements of game
         root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        Text text = new Text(" ");

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        ImageView food  = new ImageView("sample/apple.png");//food for snake
        food.setFitHeight(2*BLOCK_SIZE);//giving size for food
        food.setFitWidth(2*BLOCK_SIZE);
        food.setTranslateX((int)(Math.random()*APP_W)/BLOCK_SIZE*BLOCK_SIZE);
        food.setTranslateY((int)(Math.random()*APP_H)/BLOCK_SIZE*BLOCK_SIZE);
        Timeline animation=new Timeline(new KeyFrame(Duration.millis(200),event -> {//Animation when eats food
            food.setTranslateX((int)(Math.random()*(APP_W-BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE);
            food.setTranslateY((int)(Math.random()*(APP_H-BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE);
        }));
        animation.setCycleCount(1);
        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {//Control of game
            if(!running)
                return;
            boolean toRemove = snake.size()>1;//determines case which adds tail of snake
            Node tail = toRemove ? snake.remove(snake.size()-1):snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (direction){//Determines which button is pressed and specifies direction for snake
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY()- BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY()+ BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX()- BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX()+ BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;

            }

            moved= true;//snake moves



            if (toRemove) {//Adds tail if case when snake size<1 true
                snake.add(0, tail);
            }


            for (Node rect: snake){//Cases when looses and restarts game
                if (rect != tail && tail.getTranslateX()== rect.getTranslateX()
                        && tail.getTranslateY()==rect.getTranslateY() ){
                    restartGame();

                    break;
                }
            }
            // if coordinates of snake out of window
            if(tail.getTranslateX()<0 || tail.getTranslateX() >= APP_W || tail.getTranslateY()<0 || tail.getTranslateY()>=APP_H){
                restartGame();
            }

            if (food.getBoundsInParent().intersects(tail.getBoundsInParent())){//statement when snake eats food

                animation.play();

                score.setText(Integer.parseInt(score.getText())+1+"");//to count score

                Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                snake.add(rect);

            }


        });

        timeline.getKeyFrames().add(frame);//Adds events to keyframe
        timeline.setCycleCount(Timeline.INDEFINITE);

        score.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:white;");//Css design
        textScore.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:white;");

        hbox=new HBox(textScore,score);//Adding elements to layouts

        root.getChildren().addAll(food, snakeBody, text,hbox);
        return root;
    }
    public void restartGame(){//Method for restarting game(reruns method startGame)
        stopGame();
        if (running==false){
           Label l = new Label("Game Over");//creating label 'Game Over'
           l.setStyle("-fx-font-size:40px;-fx-font-weight:bold;-fx-text-fill:red;"); //labels design
            l.setLayoutX(220);//
            l.setLayoutY(200);
            Button replay = new Button("REPLAY");
            replay.setLayoutX(250);
            replay.setLayoutY(250);


            root.getChildren().addAll(l,replay );
            //when replay button pressed game starts again
            replay.setOnAction(event -> {
                snake.clear();
                root.getChildren().removeAll(l, replay);
                startGame();

            });
        }
    }

    public  void startGame(){//Method startGame  runs game
        direction= Direction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        snake.add(head);
        snake.add(new Rectangle(BLOCK_SIZE,BLOCK_SIZE));
        timeline.play();
        running=true;
        score.setText("0");
    }

    private  void stopGame(){//stop Game
        running=false;
        timeline.stop();

    }



    public void start(Stage window) throws Exception{
        Window windowPane=new Window();


        Scene windowScene=new Scene(windowPane,300,400);//Creating needed scenes and windows


        windowScene.getStylesheets().add(0, "sample/snake.css");//Adds css file to main menu scene

        Scene scene= new Scene(createContent());//game scene
        scene.setOnKeyPressed(event -> {
            if(moved){
                switch (event.getCode()){//Direction Control buttons
                    case W: //W is Up
                        if (direction !=Direction.DOWN)
                            direction=Direction.UP;
                        break;
                    case S: //S is Down
                        if (direction !=Direction.UP)
                            direction=Direction.DOWN;
                        break;
                    case A://A is Left
                        if (direction !=Direction.RIGHT)
                            direction=Direction.LEFT;
                        break;
                    case D://D is Right
                        if (direction !=Direction.LEFT)
                            direction=Direction.RIGHT;
                        break;
                }
            }

            moved=false;
        });




        window.setTitle("Tutorial");
        window.setScene(windowScene);

        More morePane=new More();
        Scene morePaneScene=new Scene(morePane,300,400);//scene for more menu
        morePaneScene.getStylesheets().add(0, "sample/more.css");

        Window.button1.setOnAction(event -> {//changes scene if play button clicked

            window.setScene(scene);
            startGame();

        });

        Window.button2.setOnAction(event -> {//changes scene if more button is clicked

            window.setScene(morePaneScene);

        });
        More.easy.setOnAction(event ->{//controls(changes) level of game(increases or decreases speed of sneak)
            timeline.setDelay(Duration.seconds(0.5));

        });
        More.medium.setOnAction(event -> {//controls(changes) level of game(increases or decreases speed of sneak)
            timeline.setDelay(Duration.seconds(0.3));
        });
        More.hard.setOnAction(event -> {//controls(changes) level of game(increases or decreases speed of sneak)
            timeline.setDelay(Duration.seconds(0.1));

        });
        More.expert.setOnAction(event -> {//controls(changes) level of game(increases or decreases speed of sneak)
            timeline.setDelay(Duration.millis(0.05));
        });
      More.play.setOnAction(event -> {//Starts game when play button is More scene pressed
          window.setScene(scene);
          startGame();
      });


        scene.getStylesheets().add(0,"sample/food.css");// Add css file to game scene
        window.show();
        startGame();


    }
    public static void main(String[] args){
        launch(args);
    }
}
