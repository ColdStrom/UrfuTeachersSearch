import java.util.HashMap;
import java.util.Objects;

public class BotLogic {
    /*

        // UI
        // Application
        // Domain

            todo: Logic оч связана с UI
     */
    private HashMap<String, Teacher> teachers;
    BotLogic(){
        Teachers _teachers = new Teachers();
        _teachers.parsingPageTeacher();
        teachers = _teachers.getTeachers();
    }

    public String answer(String message){
        AnswerForRequestTeacher answer = new AnswerForRequestTeacher();
        switch (parseUserMessage(message)) {
            case HELP:
                return StandardAnswer.answerForHelp;
            case START:
                return StandardAnswer.answerForStart;
            case JOKE:
                return StandardAnswer.answerForJoke;
            case TEACHER:
                return answer.makeAnswer(message, teachers);
        }
        return StandardAnswer.defaultAnswer;
    }


//  todo*: не хардкодить здесь команды, генироровать хепл только по тому, что точно умеем



    private UserRequest.UserRequests parseUserMessage(String message) {
        if (Objects.equals(message, StandardUserRequest.help)) {
            return UserRequest.UserRequests.HELP;
        } else if (Objects.equals(message, StandardUserRequest.start)) {
            return UserRequest.UserRequests.START;
        } else if (Objects.equals(message, StandardUserRequest.joke)) {
            return UserRequest.UserRequests.JOKE;
        }
        return UserRequest.UserRequests.TEACHER;
    }
}
