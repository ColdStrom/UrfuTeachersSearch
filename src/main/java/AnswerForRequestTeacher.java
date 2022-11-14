import java.util.HashMap;
import java.util.Objects;

public class AnswerForRequestTeacher {
    public static String makeAnswer(String _teacher, HashMap<String, Teacher> teachers){
        Teacher teacher = teachers.get(_teacher);
        String answer = teacher.getName();
        if (!Objects.equals(teacher.getEmail(), "empty") || !Objects.equals(teacher.getEmail(), "")){
            answer += "\n\t" + "Почта: " + teacher.getEmail();
        }
        if (!Objects.equals(teacher.getNumberPhone(), "empty") || Objects.equals(teacher.getNumberPhone(), "")){
            answer += "\n\t" + "Телефон кафедры: " + teacher.getNumberPhone();
        }
        if (!Objects.equals(teacher.getAddress(), "empty") || !Objects.equals(teacher.getAddress(), "")){
            answer += "\n\t" + "Адрес и аудитория: " + teacher.getAddress();
        }
        answer += "\n\t" + "Персональная страница преподавателя: " + teacher.getPersonalLink();
        return answer;
    }
}
