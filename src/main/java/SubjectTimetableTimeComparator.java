import java.util.Comparator;

public class SubjectTimetableTimeComparator implements Comparator<SubjectTimetable> {
    @Override
    public int compare(SubjectTimetable o1, SubjectTimetable o2) {
        if (Integer.parseInt(o1.getTimeStart().substring(0,8)) < Integer.parseInt(o2.getTimeStart().
                                                                                                substring(0,8))){
            return -1;
        } else if (Integer.parseInt(o1.getTimeStart().substring(0, 8)) > Integer.parseInt(o2.getTimeStart().
                                                                                                substring(0, 8))){
            return 1;
        }
        return 0;
    }
}
