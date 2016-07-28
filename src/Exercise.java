import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import view.Type;

public class Exercise implements ContentElement{
    String name;
    String description;
    String imagePath;
    String videoPath;
    Type type;

    public Exercise(String name,
            String description,
            String imagePath,
            String videoPath,
            Type type){
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.videoPath = videoPath;
        this.type = type;
    }

    public ScrollPane getContent() throws Exception {
        if (!name.equals(null)&&!description.equals(null)&&!imagePath.equals(null)&&!videoPath.equals(null)&&!type.equals(null)){
                ScrollPane content  = new ScrollPane();
                content.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0)");
                content.setFitToHeight(true);
                content.setFitToWidth(true);
                VBox vBox = new VBox(10);
                vBox.setPadding(new Insets(10,10,10,10));
                Label title = new Label(name);
                title.setFont(new Font(36));
                StackPane pane = new StackPane();
                pane.setAlignment(Pos.CENTER);
                vBox.setAlignment(Pos.CENTER);
                vBox.maxWidthProperty().bind(content.widthProperty());
                Image image = new Image(getClass().getResource("/exercise/" +imagePath).toString());
                ImageView imageView = new ImageView(image);
                imageView.fitHeightProperty().bind(content.heightProperty().multiply(0.755).multiply(0.75));
                imageView.fitWidthProperty().bind(content.widthProperty().multiply(0.567).multiply(0.75));
                Media media = new Media(getClass().getResource("/exercise/" +videoPath).toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                MediaView mediaView= new MediaView(mediaPlayer);
                mediaView.fitWidthProperty().bind(imageView.fitWidthProperty());
                mediaView.fitHeightProperty().bind(imageView.fitWidthProperty());
                pane.getChildren().addAll(imageView);
                HBox hBox = new HBox(10);
                Button play = new Button("Play");
                play.setVisible(false);
                play.setOnAction(event -> {
                    if (play.getText().equals("Play")) {
                        play.setText("Pause");
                        mediaPlayer.play();
                    } else {
                        play.setText("Play");
                        mediaPlayer.pause();
                    }
                });
                ImageView imageView1 = new ImageView(image);
                imageView1.fitHeightProperty().bind(imageView.fitHeightProperty().multiply(0.25));
                imageView1.fitWidthProperty().bind(imageView.fitWidthProperty().multiply(0.25));
                Media media1 = new Media(getClass().getResource("/exercise/" +videoPath).toString());
                MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
                mediaPlayer1.setOnReady(() -> {
                    mediaPlayer1.seek(Duration.seconds(2));
                });
                StackPane videoImage = new StackPane();
                videoImage.setAlignment(Pos.CENTER);
                Image playbutton = new Image(getClass().getResource("/view/playbutton.png").toString());
                ImageView playButtonView = new ImageView(playbutton);
                playButtonView.setFitHeight(40);
                playButtonView.setFitWidth(40);
                MediaView mediaView1= new MediaView(mediaPlayer1);
                mediaView1.fitWidthProperty().bind(imageView1.fitWidthProperty());
                mediaView1.fitHeightProperty().bind(imageView1.fitWidthProperty());
                videoImage.getChildren().addAll(mediaView1,playButtonView);
                videoImage.setOnMouseClicked(event -> {
                    if (pane.getChildren().contains(imageView)){
                        pane.getChildren().remove(imageView);
                        pane.getChildren().add(mediaView);
                        play.setVisible(true);
                    }
                });
                imageView1.setOnMouseClicked(event -> {
                    if (pane.getChildren().contains(mediaView)){
                        mediaView.getMediaPlayer().stop();
                        pane.getChildren().remove(mediaView);
                        pane.getChildren().add(imageView);
                        play.setVisible(false);
                    }
                });
                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().addAll(play,imageView1,videoImage);
                Text text = new Text(description);
                text.setTextAlignment(TextAlignment.CENTER);
                text.setFont(Font.font(16));
                text.wrappingWidthProperty().bind(content.widthProperty().divide(1.2));
                vBox.getChildren().addAll(title,pane,hBox,text);
                content.setContent(vBox);
                content.setStyle("-fx-background: rgb(80,80,80)");
                return content;
        }else{
            throw new Exception();
        }

    }


    @Override
    public boolean isMultiPage() {
        return true;
    }
}
