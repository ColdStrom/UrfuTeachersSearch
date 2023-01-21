// todo развести логику учителей и сервиса
//import javax.validation.valueextraction.ValueExtractorDeclarationException;
import net.fortuna.ical4j.data.ParserException;
import org.checkerframework.checker.units.qual.A;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class TeacherService {
    private HashMap<String, Teacher> teachers;
    private ArrayList<String> loadedSymbols;
    private ParsPageTeachers parsPageTeachers;
    private static HashMap<String, Integer> bordersMonths = new HashMap<>();
    {
        bordersMonths.put("01", 31);
        bordersMonths.put("02", 28);
        bordersMonths.put("03", 31);
        bordersMonths.put("04", 30);
        bordersMonths.put("05", 31);
        bordersMonths.put("06", 30);
        bordersMonths.put("07", 31);
        bordersMonths.put("08", 30);
        bordersMonths.put("09", 30);
        bordersMonths.put("10", 31);
        bordersMonths.put("11", 30);
        bordersMonths.put("12", 31);

    };

    TeacherService() {
        teachers = new HashMap<String, Teacher>();
        parsPageTeachers = new ParsPageTeachers();
        loadedSymbols = new ArrayList<String>();
    }

    public Teacher getTeacherByName(String name) {
        String[] firstSymbolsName = getFirstSymbols(name);
        for (String symbol : firstSymbolsName) {
            if (!Arrays.asList(loadedSymbols).contains(symbol)) {
                updateTeachers(symbol);
                loadedSymbols.add(symbol);
            }
        }
        Vector<String> permutationsName = permutations(name);
        for (var nameTeacher : permutationsName) {
            if (teachers.containsKey(nameTeacher)) {
                return teachers.get(nameTeacher);
            }
        }
        throw new RuntimeException("Я не знаю такого преподователя(");
    }

    private void updateTeachers(String symbol) {
        parsPageTeachers.parsPagesTeacher(symbol, teachers);
    }

    private static String[] getFirstSymbols(String _name) {
        String[] name = _name.split(" ");
        String[] firstSymbols = new String[name.length];
        for (int i = 0; i < name.length; i++) {
            firstSymbols[i] = "" + name[i].charAt(0);
        }
        return firstSymbols;
    }

    private static String join(String[] array, char separator) {
        String result = "";
        for (int i = 0; i < array.length - 1; i++) {
            result = result.concat(array[i] + separator);
        }
        result = result.concat(array[array.length - 1]);
        return result;
    }

    private static void swap(String[] array, int firstIndex, int secondIndex) {
        String tmp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = tmp;
    }

    private static Vector<String> permutations(String _elements) {
        String[] elements = _elements.split(" ");
        int size = elements.length;
        Vector<String> permutations = new Vector<>();
        int[] indexes = new int[size];
        for (int i = 0; i < size; i++) {
            indexes[i] = 0;
        }
        permutations.add(join(elements.clone(), ' '));
        int i = 0;
        while (i < size) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ? 0 : indexes[i], i);
                permutations.add(join(elements.clone(), ' '));
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
        }
        return permutations;
    }

    private static String fromUnicodeToString(String _unicodeSubsequence) {
        Set<String> hexItems = new HashSet<>();
        String unicodeSubsequence = _unicodeSubsequence.replace("\\", " ");
        Matcher m = Pattern.compile(" u[a-fA-f0-9]{4}").matcher(unicodeSubsequence);
        while (m.find()) {
            hexItems.add(m.group());
        }

        for (String unicodeHex : hexItems) {
            int hexVal = Integer.parseInt(unicodeHex.substring(2), 16);
            unicodeSubsequence = unicodeSubsequence.replace(unicodeHex, "" + ((char) hexVal));
        }
        return unicodeSubsequence;
    }

    private static Vector<String> parseData(String data) {
        Vector<String> teachersNamesID = new Vector<>();
        int indexValue = 0;
        int indexData = 0;
        while (true) {
            indexValue = data.indexOf("value", indexValue + 1);
            indexData = data.indexOf("data", indexData + 1);

            if (indexData == -1 || indexValue == -1)
                break;

            String unicodeNameTeacher = data.substring(indexValue + 9, indexData - 4);
            unicodeNameTeacher.replace("/", " ");

            StringBuilder temp = new StringBuilder();
            temp.append(fromUnicodeToString(unicodeNameTeacher));
            temp.append("   ");
            temp.append(data.substring(indexData + 6, (data.indexOf("}", indexData))));

            String _temp = new String();
            _temp = temp.toString();
            teachersNamesID.add(_temp);
        }
        return teachersNamesID;
    }

    public static String getTeacherId(String _nameTeacher) {
        String[] nameTeacher = _nameTeacher.split(" ");
        TimetableConnecting timetableConnecting = new TimetableConnecting();
        for (int i = 0; i <= nameTeacher.length - 1; i++) {
            String data = new String();
            data = timetableConnecting.getData(nameTeacher[i]);
            if (data.length() < 30)
                continue;
            Vector<String> namesTeachers = parseData(data);
            Vector<String> permutationsNamesTeachers = permutations(_nameTeacher);
            for (String name : namesTeachers) {
                for (String permutationNameTeacher : permutationsNamesTeachers) {
                    if (name.substring(0, name.indexOf("   ")).equals(permutationNameTeacher)) {
                        return name.substring(name.indexOf("   ") + 4, name.length());
                    }
                }
            }
        }
        return "Такого преподователя нет";
    }

    private static void sortArrayListSubjectTimetable(ArrayList<SubjectTimetable> subjectTimetables) {
        Collections.sort(subjectTimetables, new Comparator<SubjectTimetable>() {
            @Override
            public int compare(SubjectTimetable o1, SubjectTimetable o2) {

                if (Integer.parseInt(o1.getTimeStart().substring(0, 8)) < Integer.parseInt(o2.getTimeStart().
                        substring(0, 8))) {
                    return -1;
                } else if (Integer.parseInt(o1.getTimeStart().substring(0, 8)) > Integer.parseInt(o2.getTimeStart().
                        substring(0, 8))) {
                    return 1;
                } else if (Integer.parseInt(o1.getTimeStart().substring(9)) < Integer.parseInt(o2.getTimeStart().
                        substring(9))) {
                    return -1;
                } else if (Integer.parseInt(o1.getTimeStart().substring(9)) > Integer.parseInt(o2.getTimeStart().
                        substring(9))) {
                    return 1;
                }
                return 0;
            }
        });
    }

    private static String dateNormalization(String _date) {
        String year = _date.substring(0,4);
        String month = _date.substring(4,6);
        String day = _date.substring(6);
        if (month.equals("12") && day.equals("31")) {
            _date = (Integer.parseInt(year) + 1) + "01" + "01";
        }
        else if (Integer.parseInt(day) < bordersMonths.get(month)){
            _date = year + month + (Integer.parseInt(day) + 1);
        } else {
            _date = year + (Integer.parseInt(month) + 1) + "01";
        }
        return _date;
    }

    public static String getTeacherSchedule(String nameTeacher) throws ParserException, IOException {
        String ID = getTeacherId(nameTeacher);
        ParsTimetable parsTimetable = new ParsTimetable();
        LocalDate _date = LocalDate.now();
        String date = _date.toString().replace("-", "");
        ArrayList<SubjectTimetable> timetable = parsTimetable.parsingTimetable(ID, date);
        sortArrayListSubjectTimetable(timetable);
        String result = new String();
        int day = 0;
        for (int i = 0; i < 13; i++) {
            result += date.substring(6,8) + "." + date.substring(4,6) + "." + date.substring(0,3) + ":" + "\n";
            if (day == timetable.size()){
                for (int j = 0; j < i - day; j++){
                    date = dateNormalization(date);
                    result += "\t" + "В этот день у преподавателя нету пар" + "\n";
                    result += date.substring(6,8) + "." + date.substring(4,6) + "." + date.substring(0,3) + ":" + "\n";
                }
                result +=  "\t" + "В этот день у преподавателя нету пар" + "\n";
                break;
            }
            if (!date.equals(timetable.get(day).getTimeStart().substring(0, 8))) {
                result += "\t" + "В этот день у преподавателя нету пар" + "\n";
                date = dateNormalization(date);
            } else {
                while (date.equals(timetable.get(day).getTimeStart().substring(0, 8))){
                    result += "\t" + timetable.get(day).makeDayTimetable() + "\n";
                    day++;
                    if (day == timetable.size()){
                        break;
                    }
                }
                date = dateNormalization(date);
            }
        }
        return result;
    }
}
