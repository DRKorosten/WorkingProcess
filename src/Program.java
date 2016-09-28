import java.util.ArrayList;

public class Program {

  private  String name;
  private  ArrayList<Exercise> exercises;
  private  String imagePath;
  private  String description;

  public Program(String name, ArrayList<Exercise> exercises, String imagePath, String description) {
    this.name = name;
    this.exercises = exercises;
    this.imagePath = imagePath;
    this.description = description;
  }

}
