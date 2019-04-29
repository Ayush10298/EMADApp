package com.example.emadapp;

import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.ArrayList;


public class TimeTableAdapter extends BaseAdapter {
    Context context;
    private ArrayList<String>  Roomno;
    private ArrayList<String> Teacher;
    private ArrayList<String> Time;
    private ArrayList<String> Subject;
    private ArrayList<String> Days;



    public TimeTableAdapter(Context c, ArrayList<String> Roomno,ArrayList<String> Teacher, ArrayList<String> Time, ArrayList<String> Subject, ArrayList<String> Days){
        this.context = c;
        this.Roomno = Roomno;
        this.Teacher = Teacher;
        this.Time = Time;
        this.Subject = Subject;
        this.Days = Days;
        //Log.d("Adapter Working","Data Loaded");
    }

    @Override
    public int getCount() {
        return Roomno.size();
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
            row = inflate.inflate(R.layout.time_table_list,parent,false);
        }
        TextView subject = (TextView) row.findViewById(R.id.subject_text);
        TextView faculty = (TextView)row.findViewById(R.id.faculty_text);
        TextView roomno = (TextView) row.findViewById(R.id.room_no_text);
        TextView time = (TextView)row.findViewById(R.id.time_text);
        TextView days = (TextView)row.findViewById(R.id.days);

        subject.setText(Subject.get(position));
        faculty.setText(Teacher.get(position));
        roomno.setText(Roomno.get(position));
        time.setText(Time.get(position));
        days.setText(Days.get(position));

        return row;

    }
}
