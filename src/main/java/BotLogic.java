import java.util.HashMap;
import java.util.Objects;

public class BotLogic {
    /*

        // UI
        // Application
        // Domain

            todo: Logic оч связана с UI
     */
    TeacherService teacherService;
    BotLogic(){
        teacherService = new TeacherService();
    }

    public String answer(String message){
        AnswerForRequestTeacher answerForTeacher = new AnswerForRequestTeacher();
        switch (parseUserMessage(message)) {
            case HELP:
                return StandardAnswer.answerForHelp;
            case START:
                return StandardAnswer.answerForStart;
            case JOKE:
                return StandardAnswer.answerForJoke;
            case TEACHER:
                try{
                    Teacher teacher = teacherService.getTeacherByName(message);
                    try {
                        String timeTable = teacherService.getTeacherSchedule(message);
                        return answerForTeacher.makeAnswer(teacher) + "\n\n" + "РВСПИСАНИЕ ПАР:" + "\n" + timeTable;
                    }catch (net.fortuna.ical4j.data.ParserException e){
                        e.printStackTrace();
                        e.printStackTrace();
                    }
                    catch (java.io.IOException e){
                        e.printStackTrace();
                    }
                } catch (RuntimeException error){
                    return "Я не знаю такого преподователя";
                }
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
