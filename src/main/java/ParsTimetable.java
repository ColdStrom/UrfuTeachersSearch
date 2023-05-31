import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.MapTimeZoneCache;
import javax.cache.CacheException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;

public class ParsTimetable {
    public static ArrayList<SubjectTimetable> parsingTimetable(String _ID, String _date) throws IOException, ParserException, CacheException {
        String fileName = "C:\\Users\\osipc\\IdeaProjects\\UrfuTeachersSearch\\calendar.ics";
        try {
            downloadFile(_ID, _date, fileName);
        } catch (java.lang.Exception e){
            e.printStackTrace();
        }
        ArrayList<SubjectTimetable> timetable = new ArrayList<>();
        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
        FileInputStream fin = new FileInputStream("calendar.ics");
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        ComponentList listEvent = calendar.getComponents(Component.VEVENT);
        for (var elem : listEvent) {
            VEvent event = (VEvent) elem;
            SubjectTimetable subject = new SubjectTimetable(
                    event.getSummary().getValue(),
                    event.getStartDate().getValue(),
                    event.getEndDate().getValue(),
                    event.getDescription().getValue(),
                    event.getLocation().getValue()
            );
            timetable.add(subject);
        }
        fin.close();
        deleteFile(fileName);
        return timetable;
    }

    private static void downloadFile(String ID, String date, String fileName) throws Exception {
        URL url = new URL("https://urfu.ru/api/schedule/teacher/calendar/" + ID + "/" + date + "/");
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName));
            in.close();
        }
    }

    private static void deleteFile(String pathFile){
        try {
            Files.delete(Paths.get(pathFile));
        }  catch (IOException e) {
            System.err.println(e);
        }
    }
}