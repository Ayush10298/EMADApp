package com.example.emadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {
    Context context;
    private ArrayList<String> Events;
    private ArrayList<String> Venue;
    private ArrayList<String> Start;
    private ArrayList<String> End;

    public EventAdapter(Context context, ArrayList<String> events, ArrayList<String> venue, ArrayList<String> start, ArrayList<String> end) {
        this.context = context;
        Events = events;
        Venue = venue;
        Start = start;
        End = end;
    }

    @Override
    public int getCount() {
        return Events.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row==null){
            LayoutInflater inflate = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflate.inflate(R.layout.retrieve_list,parent,false);
        }
        TextView start = (TextView) row.findViewById(R.id.start);
        TextView end = (TextView)row.findViewById(R.id.end);
        TextView venue = (TextView) row.findViewById(R.id.venue);
        TextView event = (TextView)row.findViewById(R.id.event);


        start.setText(Start.get(position));
        end.setText(End.get(position));
        venue.setText(Venue.get(position));
        event.setText(Events.get(position));

        return row;
    }
}
