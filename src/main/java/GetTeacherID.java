import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTeacherID {
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
        for (int i = 0; i < nameTeacher.length - 1; i++) {
            String data = new String();
            data = timetableConnecting.getData(nameTeacher[i]);
            if (data.length() <= 19)
                continue;
            Vector<String> namesTeachers = parseData(data);
            for (String name : namesTeachers) {
                if (name.substring(0, name.indexOf("   ")).equals(_nameTeacher)) {
                    return name.substring(name.indexOf("   ") + 4, name.length());
                }
            }
        }
        return "Такого преподователя нет";
    }
}
