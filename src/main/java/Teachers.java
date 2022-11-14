import java.util.HashMap;

public class Teachers {
    private HashMap<String, Teacher> teachers;

    public void parsingPageTeacher(){
        ParsPageTeachers parsPageTeachers = new ParsPageTeachers();
        teachers = parsPageTeachers.parsPagesTeacher();
    }

    public static void updateTeachers(){}

    public HashMap<String, Teacher> getTeachers() {
        return teachers;
    }
}
