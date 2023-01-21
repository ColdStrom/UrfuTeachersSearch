import java.util.Comparator;

public class SubjectTimetableDateComparator implements Comparator<SubjectTimetable> {
    @Override
    public int compare(SubjectTimetable o1, SubjectTimetable o2) {
        if (Integer.parseInt(o1.getTimeStart().substring(9)) < Integer.parseInt(o2.getTimeStart().
                substring(9))){
            return 1;
        } else if (Integer.parseInt(o1.getTimeStart().substring(9)) > Integer.parseInt(o2.getTimeStart().
                substring(9))){
            return -1;
        }
        return 0;
    }
}
