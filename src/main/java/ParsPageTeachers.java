// TODO: додлеать парсер для адреса, работает неправильно
// TODO: у некоторых преподов почему то всплывает "Электронная почта: <почта>" или "Телефон: <телефон>"
//       а нам нахуй не нужон этот префикс

import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Objects;


public class ParsPageTeachers {

    public static HashMap<String, Teacher> parsPagesTeacher(){
        HashMap<String, Teacher> teachers = new HashMap<>();
        try {
            var document = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/").get();
            String[] linksTeachers = new String[31];

            var links = document.select("div.alpha-navigation");
            for (int i = 0; i < 31; i++) {
                for (var link : links) {
                    linksTeachers[i] = "https://urfu.ru" + link.child(i).attr("href").toString();
                }
            }
            for (int i = 1; i < 31; i++) {
                var nameTeacher = document.select("div.text");
                for (var element : nameTeacher) {
                    Teacher teacher = new Teacher();
                    String name = element.child(0).text();
                    String PersonalLink = "https://urfu.ru" + element.child(0).child(0).attr("href").toString();
                    teacher.setName(name);
                    teacher.setPersonalLink(PersonalLink);
                    parsTextTeachers(element.text(), teacher);
                    teachers.put(name, teacher);
                }
                document = Jsoup.connect(linksTeachers[i]).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }

    private static void parsTextTeachers(String text, Teacher teacher) {
        int indexNumberPhone = text.indexOf("Телефон");
        int indexEmail = text.indexOf("Электронная почта");
        int indexAddress = -1;
        String[] streets = {"Тургенева", "Мира", "Куйбышева", "Софьи Ковалевской", "Чапаева", "Коминтерна"};
        boolean haveAddress = false;

        for (int i = 0; i < streets.length; i++) {
            if (text.contains(streets[i])) {
                haveAddress = true;
                indexAddress = text.indexOf(streets[i]);
                break;
            }
        }
        if (!haveAddress) { teacher.setAddress("empty"); }
        if (indexNumberPhone != -1 && indexEmail == -1) {
            teacher.setNumberPhone(text.substring(indexNumberPhone));
            teacher.setEmail("empty");
            if (haveAddress) {
                teacher.setAddress(text.substring(indexAddress, indexNumberPhone - 2));
            }
        }
        if (indexEmail != -1 && indexNumberPhone == -1) {
            teacher.setEmail(text.substring(indexEmail));
            teacher.setNumberPhone("empty");
            if (haveAddress) {
                teacher.setAddress(text.substring(indexAddress, indexEmail - 1));
            }
        }
        if (indexNumberPhone != -1 && indexEmail != -1) {
            teacher.setNumberPhone(text.substring(indexNumberPhone + 9, indexEmail));
            teacher.setEmail(text.substring(indexEmail + 19));
            if (haveAddress) {
                teacher.setAddress(text.substring(indexAddress, indexNumberPhone - 2));
            }
        }
        if (indexEmail == -1 && indexNumberPhone == -1) {
            teacher.setEmail("empty");
            teacher.setNumberPhone("empty");
            if (haveAddress) {
                teacher.setAddress(text.substring(indexAddress));
            }
        }
    }
}