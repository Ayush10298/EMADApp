package com.example.emadapp;

public class EventsValue {
    private String name;
    private String start;
    private String end;
    private String venue;

    public EventsValue() {
    }

    public EventsValue(String name, String start, String end, String venue) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
