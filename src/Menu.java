import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class Menu implements ContentElement{
    int counter;
    int rows;
    ArrayList<ContentElement> menuElements;
    public Menu(int number)
    {
    counter = number;
}
    public ScrollPane createMenu(){
        ScrollPane content = new ScrollPane();
        content.setFitToHeight(true);
        content.setFitToWidth(true);
        content.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        content.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        GridPane menu = new GridPane();
        menu.setPadding(new Insets(20,20,20,20));
        menu.maxWidthProperty().bind(content.widthProperty());
        menu.maxHeightProperty().bind(content.heightProperty());
        menu.setHgap(10);
        menu.setVgap(10);
        int side = (int) Math.sqrt(counter);
        if (side*side<counter){
            side++;
        }
        int count = counter;
        rows= 0;
        while (count>0){
            count-=side;
            rows++;
        }
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (i * side + j <counter) {
                    VBox vbox = new VBox(5);
                    vbox.setAlignment(Pos.CENTER);
                    Image image = new Image(getClass().getResource("/view/ExitBackground.jpg").toString());
                    ImageView imageView = new ImageView(image);
                    imageView.fitHeightProperty().bind(content.heightProperty().subtract(50+menu.getVgap()*rows*rows).divide(rows ));
                    imageView.fitWidthProperty().bind(content.widthProperty().subtract(50+menu.getVgap()*side).divide(side));
                    Text text = new Text("Menu" + "" + (i * side + j + 1));
                    vbox.getChildren().addAll(imageView, text);
                    GridPane.setConstraints(vbox, j+1, i+1);
                    GridPane.setHalignment(vbox,HPos.CENTER);
                    GridPane.setValignment(vbox,VPos.CENTER);
                    menu.getChildren().add(vbox);
                }
            }
        }
        content.setContent(menu);
        return content;
    }

    @Override
    public boolean isMultiPage() {
        return false;
    }
}
