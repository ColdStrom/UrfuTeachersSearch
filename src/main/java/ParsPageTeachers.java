// TODO: додлеать парсер для адреса, работает неправильно
// TODO: у некоторых преподов почему то всплывает "Электронная почта: <почта>" или "Телефон: <телефон>"
//       а нам нахуй не нужон этот префикс
//https://urfu.ru/ru/about/personal-pages/personal/index/?tx_urfupersonal_personalpages%5Balpha%5D=Б

import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Vector;


// TeacherService
// Teacher[] Get(char symbol)
// Teachar Get(..)


// UrfuApi



public class ParsPageTeachers {

    public void parsPagesTeacher(String firstSymbolName, HashMap<String, Teacher> teachers){ // todo убрать статик ✅
        try {
            var document = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/personal/index/?tx_urfupersonal_personalpages%5Balpha%5D=" + firstSymbolName).get();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parsTextTeachers(String text, Teacher teacher) {
        // todo: не париться и сохранять просто как AdditionalInfo
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