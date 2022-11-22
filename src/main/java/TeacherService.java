import javax.validation.valueextraction.ValueExtractorDeclarationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class TeacherService {
    private HashMap<String, Teacher> teachers;
    private ArrayList<String> loadedSymbols;

    ParsPageTeachers parsPageTeachers;

    TeacherService(){
        teachers = new HashMap<String, Teacher>();
        parsPageTeachers = new ParsPageTeachers();
        loadedSymbols = new ArrayList<String>();
    }

    private void updateTeachers(String symbol){
        parsPageTeachers.parsPagesTeacher(symbol, teachers);
    }

    public Teacher getTeacherByName(String name) {
        String[] firstSymbolsName = getFirstSymbols(name);
        for (String symbol : firstSymbolsName){
            if (!Arrays.asList(loadedSymbols).contains(symbol)){
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

    private static String[] getFirstSymbols(String _name){
        String[] name = _name.split(" ");
        String[] firstSymbols = new String[name.length];
        for (int i = 0; i < name.length; i++){
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

    public static Vector<String> permutations(String _elements) {
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
}
