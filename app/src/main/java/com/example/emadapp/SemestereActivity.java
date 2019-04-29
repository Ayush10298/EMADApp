package com.example.emadapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class SemestereActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference reference;
    TimeTableValues timeTableValues;
    ArrayList<String> tnames;
    ArrayList<String> time;
    ArrayList<String> roomno;
    ArrayList<String> days;
    ArrayList<String> subject;


    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semestere);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TODO

        listView = (ListView)findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("TTID/001");

        tnames = new ArrayList<>();
        time = new ArrayList<>();
        roomno = new ArrayList<>();
        days = new ArrayList<>();
        subject = new ArrayList<>();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                TimeTableValues timeTableValues = dataSnapshot.getValue(TimeTableValues.class);
                //  Log.d("key value",dataSnapshot.getValue().toString());
                for (DataSnapshot dt: dataSnapshot.getChildren()){
                    for (DataSnapshot dt1: dt.getChildren()){
                        timeTableValues = dt1.getValue(TimeTableValues.class);
                        tnames.add(timeTableValues.getTeacher());
                        time.add(timeTableValues.getTime());
                        roomno.add(timeTableValues.getRoomno());
                        subject.add(timeTableValues.getSubject());
                        days.add(dt.getKey().toString());
                        listView.setAdapter(new TimeTableAdapter(getApplicationContext(),roomno,tnames,time,subject,days));
                        //Log.d("More Values",dt1.getKey().toString()+":"+dt1.'getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.semestere, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(SemestereActivity.this,NaviActivity.class));
            finish();
        } else if (id == R.id.nav_admission) {
            startActivity(new Intent(SemestereActivity.this,AdmissionActivity.class));
            finish();
        } else if (id == R.id.nav_classroom) {
            /*startActivity(new Intent(NaviActivity.this,AdmissionActivity.class));
            finish();*/
            Toast.makeText(this, "Classroom section", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_semester) {
            Toast.makeText(this, "You are in Admission section", Toast.LENGTH_SHORT).show();
            /*s*/
            Toast.makeText(this, "Semester Section", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_examination) {
            /*startActivity(new Intent(NaviActivity.this,AdmissionActivity.class));
            finish();*/
            Toast.makeText(this, "Examination Section", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_career) {
           /* startActivity(new Intent(NaviActivity.this,AdmissionActivity.class));
            finish();*/
            Toast.makeText(this, "Placement Section", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_update) {
            Toast.makeText(this, "Feature Coming soon!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(SemestereActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
