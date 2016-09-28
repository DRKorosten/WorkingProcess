import DB.DB;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.LinkedHashMap;


public class MaxWeightPane extends ScrollPane{
    public MaxWeightPane(LinkedHashMap<String,Double> data,int user_id){
        setPadding(new Insets(10,10,10,10));
        setStyle("-fx-background-color: rgba(0, 100, 100, 0.0)");
        setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        GridPane gridPane = new GridPane();
        gridPane.setOpacity(1);
        gridPane.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0)");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        int row = 0;
        for (String key: data.keySet() ) {
            Label label = new Label(key);
            label.setTextFill(Color.CYAN);
            TextField textField = new TextField(data.get(key).toString());
            Button button = new Button("Save");
            button.setOnAction(event -> {
                int row1 = GridPane.getRowIndex(button);
               if (checkString(textField.getText())){
                   String sql = "UPDATE max_weight SET weight ="+textField.getText()+" WHERE user_id ="+user_id+" and exercise_id ="+(row1+1)+";";
                   DB.createOtherQueries(sql);
                   textField.setText(new Double(textField.getText()).toString());
                   textField.setStyle("-fx-background-color: white");
                   DB.close();
            }
            });
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.equals("")){
                    textField.setStyle("-fx-background-color: white");
                    button.setDisable(true);
                }else {
                    if (checkString(newValue)) {
                        textField.setStyle("-fx-background-color: greenyellow");
                        button.setDisable(false);
                    } else {
                        textField.setStyle("-fx-background-color: lightcoral");
                        button.setDisable(true);
                    }
                }
            });
            GridPane.setConstraints(label,0,row);
            GridPane.setConstraints(textField,1,row);
            GridPane.setConstraints(button,2,row++);
            gridPane.getChildren().addAll(label,textField,button);
        }
        setContent(gridPane);
        getStylesheets().add("/MaxWeightStyle.css");
        setFitToHeight(true);
        setFitToWidth(true);
    }
    private boolean checkString(String string) {
        try {
            Double.parseDouble(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
