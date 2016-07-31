import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
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
                StackPane video = new StackPane();
                video.setAlignment(Pos.CENTER);
                Media media = new Media(getClass().getResource("/exercise/" +videoPath).toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                MediaView mediaView= new MediaView(mediaPlayer);
                mediaView.fitWidthProperty().bind(imageView.fitWidthProperty());
                mediaView.fitHeightProperty().bind(imageView.fitWidthProperty());
                Image playButton = new Image(getClass().getResource("/view/playbutton.png").toString());
                ImageView playButtonView = new ImageView(playButton);
                playButtonView.setOpacity(0.5);
                playButtonView.setFitHeight(100);
                playButtonView.setFitWidth(100);
                video.getChildren().addAll(mediaView,playButtonView);
                video.setOnMouseClicked(event -> {
                if (playButtonView.getOpacity()==0.5) {
                    playButtonView.setOpacity(0);
                    mediaPlayer.play();
                } else {
                    playButtonView.setOpacity(0.5);
                    mediaPlayer.pause();
                }
            });
                pane.getChildren().addAll(imageView);
                HBox hBox = new HBox(10);
                ImageView imageViewPreview = new ImageView(image);
                imageViewPreview.fitHeightProperty().bind(imageView.fitHeightProperty().multiply(0.25));
                imageViewPreview.fitWidthProperty().bind(imageView.fitWidthProperty().multiply(0.25));
                Media mediaPreview = new Media(getClass().getResource("/exercise/" +videoPath).toString());
                MediaPlayer mediaPlayerPreview = new MediaPlayer(mediaPreview);
                mediaPlayerPreview.setOnReady(() -> {
                    mediaPlayerPreview.seek(Duration.seconds(2));
                });
                StackPane videoPreview = new StackPane();
                videoPreview.setAlignment(Pos.CENTER);
                Image playButtonSmall = new Image(getClass().getResource("/view/playbutton.png").toString());
                ImageView playButtonViewSmall = new ImageView(playButtonSmall);
                playButtonViewSmall.setFitHeight(40);
                playButtonViewSmall.setFitWidth(40);
                MediaView mediaViewPreview= new MediaView(mediaPlayerPreview);
                mediaViewPreview.fitWidthProperty().bind(imageViewPreview.fitWidthProperty());
                mediaViewPreview.fitHeightProperty().bind(imageViewPreview.fitWidthProperty());
                videoPreview.getChildren().addAll(mediaViewPreview,playButtonViewSmall);
                BoxBlur blurEffect = new BoxBlur(5,5,1);
                videoPreview.setEffect(blurEffect);
                videoPreview.setOnMouseClicked(event -> {
                    if (pane.getChildren().contains(imageView)){
                        pane.getChildren().remove(imageView);
                        imageViewPreview.setEffect(blurEffect);
                        videoPreview.setEffect(null);
                        videoPreview.setStyle("-fx-border-width: 2;-fx-border-color: chartreuse");
                        imageViewPreview.setStyle("-fx-padding: 0;");
                        pane.getChildren().add(video);
                    }
                });
                imageViewPreview.setOnMouseClicked(event -> {
                    if (pane.getChildren().contains(video)){
                        mediaView.getMediaPlayer().stop();
                        imageViewPreview.setEffect(null);
                        videoPreview.setEffect(blurEffect);
                        imageViewPreview.setStyle("-fx-padding: 10;\n" +
                                "-fx-background-color: chartreuse;");
                        videoPreview.setStyle("-fx-border-width: 0");
                        pane.getChildren().remove(video);
                        pane.getChildren().add(imageView);
                    }
                });
                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().addAll(imageViewPreview,videoPreview);
                Text text = new Text(description);
                text.setId("description");
                text.setTextAlignment(TextAlignment.CENTER);
                text.wrappingWidthProperty().bind(content.widthProperty().divide(1.2));
                vBox.getChildren().addAll(title,pane,hBox,text);
                content.setContent(vBox);
                content.getStylesheets().add("/view/exercise.css");
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
