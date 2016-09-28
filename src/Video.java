import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Video {
    private StackPane stackPane;
    private MediaPlayer player;
    private MediaView view;
    private Slider sliderDuration;
    private Button playButton;
    private Polygon triangle;
    private HBox twix;
    private ImageView playButtonViewSmall;
    private Slider volumeSlider;
    private Button fullScreenButton;
    private double curX = 0;
    private double curY = 0;
    private String path;
    private Text maxTime;
    private boolean isPlaying;
    public Video(String videoPath){
        path = videoPath;
        stackPane = new StackPane();
        BorderPane borderPane = new BorderPane();
        //----------------------------Media-------------------------------------------------
//        Media file  = new Media("file:///C:/Users/Dmytro/Desktop/main/src/exercise/2/video.mp4");
        Media file = new Media(videoPath);
        //----------------------------Player------------------------------------------------
        player = new MediaPlayer(file);
        //---------------------------View-----------------------------------------------------
        view = new MediaView(player);
        view.setFitHeight(250);
//        //---------------------------Name box -------------------------------------------
        Image playButtonSmall = new Image(getClass().getResource("/view/playbutton.png").toString());
        playButtonViewSmall = new ImageView(playButtonSmall);
        playButtonViewSmall.setOnMouseClicked(event -> {
            player.play();
            playButtonViewSmall.setVisible(false);
            playButton.fire();
        });
        playButtonViewSmall.setFitHeight(80);
        playButtonViewSmall.setFitWidth(80);
        //---------------------------Control box -------------------------------------------
        HBox controlBox = new HBox(10);
        controlBox.setStyle("-fx-background-color: rgba(61, 59, 73, 0.8)");
        controlBox.setPadding(new Insets(10,10,10,10));
        //--------------------Triangle----------------------------------------------
        triangle = new Polygon();
        triangle.getPoints().addAll(0.0,0.0,
                0.0,30.0,
                20.0,15.0);
        triangle.setFill(Color.CYAN);
        //--------------------twix----------------------------------------------
        twix = new HBox(6);
        twix.getChildren().addAll(new Rectangle(7,30,Color.CYAN),new Rectangle(7,30,Color.CYAN));
        //--------------------CurrentTime----------------------------------------------
        Text currentTime = new Text("00:00");
        currentTime.setOnMouseEntered(event -> {
        });
        //--------------------Separator----------------------------------------------
        Text separator = new Text("|");
        //--------------------MaxTime----------------------------------------------
        maxTime = new Text("00:00");
        //--------------------ButtonPlay----------------------------------------------
        playButton = new Button("",triangle);
        playButton.setOnAction(event -> {
            if (player.getStatus() == MediaPlayer.Status.PLAYING){
                player.pause();
                playButtonViewSmall.setVisible(true);
                playButton.setGraphic(triangle);
                playButtonViewSmall.setVisible(true);
            }
            else {
                player.play();
                playButton.setGraphic(twix);
                playButtonViewSmall.setVisible(false);
            }
        });
        playButton.setStyle("-fx-background-color: transparent");
        //--------------------Slider----------------------------------------------
        sliderDuration = new Slider();
        player.setOnReady(() -> {
            sliderDuration.setMin(0);
            sliderDuration.setValue(0);
            sliderDuration.setMax(player.getTotalDuration().toSeconds());
            int hours = (int)player.getTotalDuration().toHours();
            int minutes = ((int)player.getTotalDuration().toMinutes())%60;
            int seconds = ((int)player.getTotalDuration().toSeconds())%60;
            if (hours>0)
                maxTime.setText(String.format("%d:%02d:%02d",hours,minutes,seconds));else
                maxTime.setText(String.format("%02d:%02d",minutes,seconds));
            sliderDuration.setMinWidth(20);

        });

        player.setOnEndOfMedia(() -> {
            player.stop();
            playButtonViewSmall.setVisible(true);
            playButton.setGraphic(triangle);
            sliderDuration.setValue(0);
        });
        player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!sliderDuration.isHover())
                sliderDuration.setValue(newValue.toSeconds());
            int hours = (int)newValue.toHours();
            int minutes = ((int)newValue.toMinutes())%60;
            int seconds = ((int)newValue.toSeconds())%60;
            if (hours>0)
                currentTime.setText(String.format("%d:%02d:%02d",hours,minutes,seconds));else
                currentTime.setText(String.format("%02d:%02d",minutes,seconds));
        });

        sliderDuration.setOnMouseClicked(event -> player.seek(Duration.seconds(sliderDuration.getValue())));
        sliderDuration.prefWidthProperty().bind(controlBox.widthProperty().multiply(0.7));
        //--------------------Volume----------------------------------------------
        volumeSlider = new Slider(0,1,1);//
        volumeSlider.setOrientation(Orientation.VERTICAL);
        volumeSlider.setPrefHeight(100);
        volumeSlider.setVisible(false);
//        volumeSlider.setShowTickLabels(true);
//        volumeSlider.setShowTickMarks(true);
        Image volumeImage = new Image(getClass().getResource("/view/2volume.png").toString());
        ImageView volumeImageView = new ImageView(volumeImage);
        Button volume = new Button("",volumeImageView);
        volume.setStyle("-fx-background-color: green");
        volume.setOnAction(event -> {
            if (player.getVolume()>0){
                player.setVolume(0);
                volume.setStyle("-fx-background-color: red");
                volumeSlider.setValue(0);
            }
            else {
                player.setVolume(1);
                volume.setStyle("-fx-background-color: green");
                volumeSlider.setValue(1);
            }
        });
        volume.setOnMouseEntered(event -> volumeSlider.setVisible(true));
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            player.setVolume((Double) newValue);
            if ((Double)newValue==0){
                volume.setStyle("-fx-background-color: red");
            }else{
                volume.setStyle("-fx-background-color: green");
            }
        });
        volumeImageView.setFitWidth(20);
        volumeImageView.setFitHeight(20);
        //--------------------FullScreen----------------------------------------------
        Image fullScreenImage = new Image(getClass().getResource("/view/fullScreen.png").toString());
        ImageView fullScreenImageView = new ImageView(fullScreenImage);
        fullScreenButton = new Button("",fullScreenImageView);
        fullScreenButton.setOnAction(event -> {
            isPlaying = player.getStatus() == MediaPlayer.Status.PLAYING;
                Player window = new Player();
                try {
                    window.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            player.pause();
            playButtonViewSmall.setVisible(true);
        });
        fullScreenImageView.setFitWidth(20);
        fullScreenImageView.setFitHeight(20);
        //------------------------------------------------------------------------------
        controlBox.getChildren().addAll(playButton,sliderDuration,currentTime,separator,maxTime,volume,fullScreenButton);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setOnMouseMoved(event -> {
            if (Math.abs(event.getX() - volumeSlider.getLayoutX())>100) volumeSlider.setVisible(false);
        });
        //---------------------------Border Pane ---------------------------------------------
//        borderPane.setTop(label);
        borderPane.setBottom(controlBox);
        Pane pane = new Pane();
        pane.setOnMouseMoved(event -> {
            if (event.getY()<volumeSlider.getLayoutY()||Math.abs(event.getX() - volumeSlider.getLayoutX())>100){
                volumeSlider.setVisible(false);
            }
        });
        volumeSlider.layoutXProperty().bind(volume.layoutXProperty().add(12));
        volumeSlider.layoutYProperty().bind(pane.layoutYProperty().add(110));
        pane.getChildren().addAll(volumeSlider);
        borderPane.setCenter(pane);
        //---------------------------Stack Pane ---------------------------------------------
        stackPane.setAlignment(Pos.CENTER);
        stackPane.maxWidthProperty().bind(view.fitWidthProperty());
        stackPane.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            controlBox.setDisable(false);
            controlBox.setOpacity(1);
            stackPane.setCursor(Cursor.DEFAULT);
            curX=event.getX();
            curY=event.getY();
        });
        AnimationTimer timer = new AnimationTimer() {
            long deltaNs = 3223372899L;
            double currX;
            double currY;
            long currNs;

            double prevX;
            double prevY;
            long prevNs;
            @Override
            public void handle(long now) {
                currX = curX;
                currY = curY;
                currNs = now;
                if( currNs - prevNs > deltaNs && controlBox.getOpacity()==1) {

                    if( prevX == currX && prevY == currY) {
                        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),controlBox);
                        fadeTransition.setFromValue(1);
                        fadeTransition.setToValue(0);
                        fadeTransition.play();
                        controlBox.setDisable(true);
                        volumeSlider.setVisible(false);
                        stackPane.setCursor(Cursor.NONE);
                    }

                    prevX = currX;
                    prevY = currY;
                    prevNs = currNs;
                }

            }
        };
        timer.start();
        stackPane.getChildren().addAll(view,borderPane,playButtonViewSmall);
        stackPane.setStyle("-fx-border-width: 3; -fx-border-color: gold; -fx-border-radius: 3");
        //-----------------------------------------------------------------------------------
    }  StackPane getStackPane() {
        return stackPane;
    }
        public void stop(){
            player.stop();
            playButton.setGraphic(triangle);
        }


    private class Player extends Application{
        @Override
        public void start(Stage primaryStage) throws Exception {
            Video video = new Video(path);
            video.stackPane.setStyle(null);
            video.view.fitHeightProperty().bind(video.stackPane.heightProperty());
            video.view.fitWidthProperty().bind(video.stackPane.widthProperty());
            stackPane.setDisable(true);
            video.player.setOnReady(() -> {
                video.volumeSlider.setValue(volumeSlider.getValue());
                video.player.setVolume(player.getVolume());
                video.maxTime.setText(maxTime.getText());
                video.sliderDuration.setValue(sliderDuration.getValue());
                video.player.play();
                video.player.seek(Duration.seconds(video.sliderDuration.getValue()));
                if (!isPlaying) {
                    video.player.pause();
                    video.playButtonViewSmall.setVisible(true);
                }else {
                    video.playButton.setGraphic(video.twix);
                    video.playButtonViewSmall.setVisible(false);
                }
            });
            video.fullScreenButton.setOnAction(event -> {
                isPlaying = video.player.getStatus() == MediaPlayer.Status.PLAYING;
                sliderDuration.setValue(video.sliderDuration.getValue());
                volumeSlider.setValue( video.volumeSlider.getValue());
                player.setVolume(video.player.getVolume());
                player.seek(Duration.seconds(sliderDuration.getValue()));
                if (!isPlaying) {
                    player.pause();
                    playButtonViewSmall.setVisible(true);
                    playButton.setGraphic(triangle);
                }else {
                    player.play();
                    playButton.setGraphic(twix);
                    playButtonViewSmall.setVisible(false);
                }
                 video.player.stop();
                getStackPane().setDisable(false);
                primaryStage.setFullScreen(false);
                primaryStage.hide();
            });
            primaryStage.setOnCloseRequest(event -> {
                video.fullScreenButton.fire();
            });
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(video.stackPane,800,600));
            primaryStage.setFullScreen(true);
            primaryStage.show();
        }
    }
}
