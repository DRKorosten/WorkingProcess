import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
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
                imageView.setFitHeight(250);
                imageView.setFitWidth(450);
                StackPane imagePane = new StackPane();
                imagePane.setStyle("-fx-border-width: 3; -fx-border-color: gold; -fx-border-radius: 3");
                imagePane.setMaxWidth(450);
                imagePane.getChildren().addAll(imageView);
                Video videoHolder = new Video(getClass().getResource("/exercise/" +videoPath).toString());
                StackPane video = videoHolder.getStackPane();
                pane.getChildren().addAll(imagePane);
                HBox hBox = new HBox(10);
                StackPane imagePreview = new StackPane();
                imagePreview.setStyle("-fx-background-color: chartreuse;-fx-background-radius: 2; -fx-padding: 2");
                ImageView imageViewPreview = new ImageView(image);
                imageViewPreview.setFitHeight(90);
                imageViewPreview.setFitWidth(160);
                Media mediaPreview = new Media(getClass().getResource("/exercise/" +videoPath).toString());
                MediaPlayer mediaPlayerPreview = new MediaPlayer(mediaPreview);
                mediaPlayerPreview.setOnReady(() -> {
                    mediaPlayerPreview.seek(Duration.seconds(20));
                });
                StackPane videoPreview = new StackPane();
                videoPreview.setAlignment(Pos.CENTER);
                Text textVideo = new Text("Video");
                textVideo.setFont(new Font(30));
                textVideo.setStyle("-fx-fill:#f99000;-fx-stroke: black");
                Image playButtonSmall = new Image(getClass().getResource("/view/playbutton.png").toString());
                ImageView playButtonViewSmall = new ImageView(playButtonSmall);
                playButtonViewSmall.setFitHeight(40);
                playButtonViewSmall.setFitWidth(40);
                MediaView mediaViewPreview= new MediaView(mediaPlayerPreview);
                mediaViewPreview.setFitHeight(90);
                videoPreview.getChildren().addAll(mediaViewPreview,playButtonViewSmall,textVideo);
                videoPreview.setAlignment(textVideo,Pos.TOP_CENTER);
                BoxBlur blurEffect = new BoxBlur(5,5,1);
                mediaViewPreview.setEffect(blurEffect);
                videoPreview.setOnMouseClicked(event -> {
                    if (pane.getChildren().contains(imagePane)){
                        pane.getChildren().remove(imagePane);
                        imageViewPreview.setEffect(blurEffect);
                        mediaViewPreview.setEffect(null);
                        videoPreview.setStyle("-fx-border-width: 2;-fx-border-color: chartreuse");
                        imagePreview.setStyle(null);
                        pane.getChildren().add(video);
                    }
                });
                imagePreview.setOnMouseClicked(event -> {
                    if (pane.getChildren().contains(video)){
                        videoHolder.stop();
                        imageViewPreview.setEffect(null);
                        mediaViewPreview.setEffect(blurEffect);
                        imagePreview.setStyle("-fx-background-color: chartreuse;-fx-background-radius: 2; -fx-padding: 2");
                        videoPreview.setStyle("-fx-border-width: 0");
                        pane.getChildren().remove(video);
                        pane.getChildren().add(imagePane);
                    }
                });
                hBox.setAlignment(Pos.CENTER);
                Text textPhoto = new Text("Photo");
                textPhoto.setFont(new Font(30));
                textPhoto.setStyle("-fx-fill:#f99000;-fx-stroke: black");
                imagePreview.setAlignment(textPhoto,Pos.TOP_CENTER);
                imagePreview.getChildren().addAll(imageViewPreview,textPhoto);
                hBox.getChildren().addAll(imagePreview,videoPreview);
                StackPane textPane = new StackPane();
                textPane.setAlignment(Pos.CENTER);
                Text text = new Text(description);
                text.setStyle("-fx-fill: black");
                text.setId("description");
                text.setTextAlignment(TextAlignment.CENTER);
                text.wrappingWidthProperty().bind(content.widthProperty().divide(1.1));
                textPane.getChildren().addAll(text);
                textPane.setStyle("-fx-background-color: rgba(255, 255, 255,0.6);-fx-background-radius: 50;");
                vBox.getChildren().addAll(title,pane,hBox,textPane);
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
