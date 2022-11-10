// страничка с преподами https://urfu.ru/ru/about/personal-pages/

import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Objects;

//Тургенева, Мира, Куйбышева, Софьи Ковалевской, Чапаева, Коминтерна,

public class ParsPageTeachers {

    public static void main(String[] args) {
        HashMap<String, Teacher> teachers = new HashMap<>();
        HashMap<String, String> contactsTeachers = new HashMap<>();
        try {
            var document = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/").get();
            var nameTeacher = document.select("div.text");
            for (var elements : nameTeacher){
                //System.out.println(elements.text());
                Teacher teacher = new Teacher();
                String name = elements.child(0).text();
                String link = elements.child(0).child(0).attr("href").toString();
                teacher.setName(name);
                teacher.setPersonalLink(link);
                teacher.setAddress("empty");
                parsTextTeachers(elements.text(), teacher);
                teachers.put(name, teacher);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        for (String key : teachers.keySet()){
            teachers.get(key).printTeacher();
        }
    }

    private static void parsTextTeachers(String text, Teacher teacher){
        int indexNumberPhone = text.indexOf("Телефон");
        int indexEmail = text.indexOf("Электронная почта");
        if (indexNumberPhone != -1 && indexEmail == -1 ){
            teacher.setNumberPhone(text.substring(indexNumberPhone));
        }
        if (indexEmail != -1 && indexNumberPhone == -1){
            teacher.setEmail(text.substring(indexEmail));
        }
        if (indexNumberPhone != -1 && indexEmail != -1){
            teacher.setNumberPhone(text.substring(indexNumberPhone + 9, indexEmail));
            teacher.setEmail(text.substring(indexEmail + 19));
        }
        return;
    }

    private static void parsAddress(String text, Teacher teacher){
        String[] streets = {"Тургенева", "Мира", "Куйбышева", "Софьи Ковалевской", "Чапаева", "Коминтерна"};
    }

}


//public class ParsPageTeachers {
//    public static void main(String[] args) {
//        try {
//            var document = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/").get();
//            var content = document.select("p.name");
//            //var tegContent = content.tagName("a");
//            for (var elements : content){
//                System.out.println(elements + ": " + elements.attr("href"));
//                var teacher = elements.attr("href");
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//}



//    public static void main(String[] args) {
//        HashMap<String, String> linksTeachers = new HashMap<>();
//        HashMap<String, String> contactsTeachers = new HashMap<>();
//        try {
//            var document = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/").get();
//            var nameTeacher = document.select("div.text > p.name");
//            var contacts = document.select("div.text");
//            for (var elements : nameTeacher){
//                var link = elements.child(0);
//                linksTeachers.put(elements.text().toString(), link.attr("href").toString());
//            }
//            for (var elements : contacts){
//                var contact = elements.child(5);
//                contactsTeachers.put(elements.text().toString(), " " + contact.child(5).text() + elements.child(6).text());
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        for (String key : linksTeachers.keySet()) {
//            System.out.println(key + ": " + linksTeachers.get(key) + "контакты: " + contactsTeachers.get(key));
//        }
//    }



//    public static void main(String[] args) {
//        HashMap<String, Teacher> teachers = new HashMap<>();
//        try {
//            var document = Jsoup.connect("https://urfu.ru/ru/about/personal-pages/").get();
//            var nameTeacher = document.select("div.text");
//            var contacts = document.select("div.text");
//            for (var elements : nameTeacher) {
//                Teacher teacher = new Teacher();
//                String name = elements.child(0).text();
//                String link = elements.child(0).child(0).attr("href").toString();
//                String address = elements.child(5).text();
//                String numberPhone =  elements.child(6).text();
//                var rt = elements.childNodeSize();
//                if (elements.childNodeSize() > 15){
//                    String email = elements.child(7).child(0).attr("href").toString();
//                    teacher.setAll(name, link, address, numberPhone, email);
//                } else {
//                    teacher.setAll(name, link, address, numberPhone, "empty");
//                }
//
//                teachers.put(name, teacher);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (String key : teachers.keySet()) {
//            teachers.get(key).printTeacher();
//        }
//    }