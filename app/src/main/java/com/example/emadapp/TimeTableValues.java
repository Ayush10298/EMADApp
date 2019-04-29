package com.example.emadapp;

public class TimeTableValues {
    private String  Roomno;
    private String Teacher;
    private String Time;
    private String Subject;

    public TimeTableValues() {
    }

    public TimeTableValues(String roomno, String teacher, String time, String subject) {
        Roomno = roomno;
        Teacher = teacher;
        Time = time;
        Subject = subject;
    }

    public String getRoomno() {
        return Roomno;
    }

    public void setRoomno(String roomno) {
        Roomno = roomno;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}

