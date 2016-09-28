import DB.DB;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import view.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public  class MainController {

    public BorderPane content;
    @FXML
    private Button buttonLeft;
    @FXML
    private Button buttonRight;
    @FXML
    private Label labelUser;
    private ArrayList<ScrollPane> pages;
    private int current;
    private int user_id =1 ;
    private LinkedHashMap<String,Double> maxWeigthData;
    private Menu menu;
    private ContentElement contentElement;

    private MaxWeightPane createMaxWeightPane(){
        String query = new String("Select e.name_ru ,m.weight from 'exercise' e, 'max_weight' m where e.id in\n" +
                "       (Select distinct exercise_id from 'schedule' where program_id  in (Select program_id from 'user_program' where user_id ="+user_id+"))  and  m.exercise_id in\n" +
                "        (Select distinct exercise_id from 'schedule' where program_id in(Select program_id from 'user_program' where user_id = "+user_id+" )) and \n" +
                "        e.id = m.exercise_id;");
        maxWeigthData = new LinkedHashMap<>();
        try {
            ResultSet resultSet= DB.createSelectQuery(query);
            while (resultSet.next()){
              maxWeigthData.put(resultSet.getString("name_ru"),resultSet.getDouble("weight"));
            }
            DB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MaxWeightPane maxWeightPane = new MaxWeightPane(maxWeigthData,1);

        return maxWeightPane;
    }

    private int getCountOfEx(){
        String query = new String("select count(id) from max_weight");
        try {
            ResultSet resultSet= DB.createSelectQuery(query);
            resultSet.next();
            System.out.println(resultSet.getInt("count(id)"));
            DB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 112;
    }
    private int getCountOfPrograms(){
        String query = new String("select count(*) from user_program Where user_id ="+user_id);
        int counter = 0;
        try {
            ResultSet resultSet= DB.createSelectQuery(query);
            resultSet.next();
            counter=resultSet.getInt("count(*)");
            DB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    void start(){
        labelUser.setText(getUserName());
        pages = new ArrayList<>();
//        int counter_programs = getCountOfPrograms();
//        int order = 1;
//        int k;
//        while (order<counter_programs){
//            if (counter_programs-order<3) k = counter_programs-order; else k=3;
//            for (int i = 0; i < k; i++) {
//            getProgramExercises(order);
//            }
//        }

        ArrayList<Exercise> exercises = getProgramExercises(1);
        for (Exercise contentElement:exercises ) {
            try {
                pages.add(contentElement.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        Menu menu = new Menu(5);
//        pages.add(menu.createMenu());
        current = 0;

        content.setCenter(pages.get(current));
        if (current==0||current==pages.size()-1) {
            buttonLeft.setDisable(true);
        }
        if (this.pages.size()==1){
            buttonRight.setVisible(false);
            buttonLeft.setVisible(false);
        }
    }
    private ArrayList<Exercise> getProgramExercises(int id ){
        ArrayList<Exercise> exercises = new ArrayList<>();
        String query = new String("Select id,name_ru,description_ru,image,video from exercise where id in (Select exercise_id from schedule where program_id = "+id+" order by order_num,day) order by id");
        try {
            ResultSet resultSet= DB.createSelectQuery(query);
            while (resultSet.next()){
               Exercise exercise = new Exercise(resultSet.getString("name_ru"),resultSet.getString("description_ru"),resultSet.getString("id")+"/img.jpg",resultSet.getString("id")+"/video.mp4",Type.BASE);
               exercises.add(exercise);
            }
            DB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;

    }

    private String getUserName() {
        String query = new String("select username from user Where id ="+user_id);
        try {
            ResultSet resultSet= DB.createSelectQuery(query);
            resultSet.next();
            query=resultSet.getString("username");
            DB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @FXML
    private void previusPage() {
        if (current>0){
            content.setCenter(pages.get(--current));
            buttonRight.setDisable(false);
        }
        if (current==0){
            buttonLeft.setDisable(true);
        }
    }
    @FXML
    private void nextPage() {
        if (current<pages.size()-1){
            content.setCenter(pages.get(++current));
            buttonLeft.setDisable(false);
        }
        if (current==pages.size()-1){
            buttonRight.setDisable(true);
        }
    }

    public void logOut(ActionEvent event) {
//        ((Node)(event.getSource())).getScene().getWindow().hide();
//        LogInWindow logInWindow = new LogInWindow();
//        try {
//            logInWindow.start(new Stage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
//
    public void editProfile() {
//        EditDataWindow editDataWindow = new EditDataWindow(user ,this);
//        try {
//            editDataWindow.start(new Stage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


@FXML
    private void maximize(Event  event) {
//      String name = ((Node)(event.getSource())).getId();
//      File path = new File("C:/Users/Dmytro/Desktop/SimpleAplication/src/sample/mainProgram/view/Info/"+name);
//    try {
//        InfoPane infoPane = new InfoPane(path);
//        currentScene.setCenter(infoPane);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
}
}

