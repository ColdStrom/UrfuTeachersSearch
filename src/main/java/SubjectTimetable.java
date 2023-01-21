public class SubjectTimetable {
    private String subject;
    private String timeStart;
    private String timeEnd;
    private String group;
    private String room;

    public SubjectTimetable(String _subject, String _timeStart, String _timeEnd, String _group, String _room){
        this.subject = _subject;
        this.timeStart = _timeStart;
        this.timeEnd = _timeEnd;
        this.group = _group;
        this.room = _room;
    }

    public SubjectTimetable(){
        this.subject = "";
        this.timeStart = "";
        this.timeEnd = "";
        this.group = "";
        this.room = "";
    }

    public String makeDayTimetable(){
        String result = "";
        result += timeStart.substring(9,11) + ":" + timeStart.substring(11,13) + "-" + timeEnd.substring(9,11) + ":" + timeEnd.substring(11,13) + ": " + subject + " ауд. " + room;
        return result;
    }

    public String getGroup() {
        return group;
    }

    public String getSubject() {
        return subject;
    }

    public String getRoom() {
        return room;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

}
