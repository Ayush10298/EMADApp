package com.example.emadapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdmissionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> events;
    ArrayList<String> start;
    ArrayList<String> end;
    ArrayList<String> venue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);
        this.setTitle("Events");
        listView = (ListView)findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("events/");

        events = new ArrayList<>();
        start = new ArrayList<>();
        end = new ArrayList<>();
        venue = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dt: dataSnapshot.getChildren()){
                    EventsValue eventsValues = dt.getValue(EventsValue.class);
                    events.add(eventsValues.getName());
                    start.add(eventsValues.getStart());
                    end.add(eventsValues.getEnd());
                    venue.add(eventsValues.getVenue());
                    listView.setAdapter(new EventAdapter(getApplicationContext(),events,venue,start,end));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AdmissionActivity.this.finish();
                            startActivity(new Intent(AdmissionActivity.this, MainActivity.class));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi, menu);
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
            startActivity(new Intent(AdmissionActivity.this,NaviActivity.class));
            finish();
        } else if (id == R.id.nav_admission) {

            Toast.makeText(this, "You are in Admission section", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_classroom) {
            /*startActivity(new Intent(NaviActivity.this,AdmissionActivity.class));
            finish();*/
            Toast.makeText(this, "Classroom section", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_semester) {
            startActivity(new Intent(AdmissionActivity.this,SemestereActivity.class));
            finish();
            //Toast.makeText(this, "Semester Section", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(AdmissionActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
