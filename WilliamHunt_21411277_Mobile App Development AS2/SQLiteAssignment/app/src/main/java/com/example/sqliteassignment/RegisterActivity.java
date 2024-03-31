package com.example.sqliteassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    List<Events> eventsList;
    LinearLayout.LayoutParams params;
    dbClass db;
    int currentPos = 0;
    public static String EventName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView txtID = findViewById(R.id.txtID);
        EditText edtEventName = findViewById(R.id.edtName);
        EditText edtEventInfo = findViewById(R.id.edtInfo);
        EditText edtEventTime = findViewById(R.id.edtTime);
        EditText edtEventVenue = findViewById(R.id.edtVenue);
        edtEventInfo.setTextColor(Color.parseColor("#000000"));
        edtEventName.setTextColor(Color.parseColor("#000000"));
        edtEventTime.setTextColor(Color.parseColor("#000000"));
        edtEventVenue.setTextColor(Color.parseColor("#000000"));
        edtEventName.setHint("Username/Email");
        edtEventInfo.setHint("Hobbies");
        edtEventTime.setHint("Password");
        edtEventVenue.setHint("Postcode");
        edtEventInfo.setHintTextColor(Color.parseColor("#000000"));
        edtEventTime.setHintTextColor(Color.parseColor("#000000"));
        edtEventVenue.setHintTextColor(Color.parseColor("#000000"));
        edtEventName.setHintTextColor(Color.parseColor("#000000"));
        Button btnAdd = findViewById(R.id.AddBtn);
        btnAdd.setTextColor(Color.parseColor("#000000"));
        btnAdd.setBackgroundColor(Color.parseColor("#FFB8FF"));
        btnAdd.setTextColor(Color.parseColor("#000000"));
        Button btnReturn = findViewById(R.id.btnLoginReturn);
        btnReturn.setBackgroundColor(Color.parseColor("#FFB8FF"));
        btnReturn.setTextColor(Color.parseColor("#000000"));
        Button btnClear = findViewById(R.id.ClearBtn);
        btnClear.setBackgroundColor(Color.parseColor("#FFB8FF"));
        btnClear.setTextColor(Color.parseColor("#000000"));

        db = new dbClass(this);
        eventsList = db.getAll();
        currentPos = 0;

        Intent ReturnIntent = new Intent(this, Login.class);

        if(eventsList.size() > 0){
            loadData(currentPos);
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtID.getText().toString();
                EventName = edtEventName.getText().toString();
                String EventInfo = edtEventInfo.getText().toString();
                String EventTime = edtEventTime.getText().toString();
                String EventVenue = edtEventVenue.getText().toString();
                String Picture1 = "Picture1";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                Date currentTime = Calendar.getInstance().getTime();
                String date = dateFormat.format(calendar.getTime());
                String Datetime = date + " " + currentTime;
                if(EventName.isEmpty() && EventInfo.isEmpty() && EventTime.isEmpty() && EventVenue.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email And Password are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(EventName.isEmpty() || EventTime.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email And Password are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(id.isEmpty() && !EventName.isEmpty() && !EventTime.isEmpty()){
                    Events e1 = new Events(EventName, EventTime, EventInfo, EventVenue, Datetime, Picture1);
                    db.addEvents(e1);
                    //Toast.makeText(MainActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Please click on the clear button", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ReturnIntent);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        clearData();
    }
    @Override
    protected void onResume() { //called when activity goes into foreground
        super.onResume();
        //define layout
        ConstraintLayout ThisLayout = findViewById(R.id.RegisterLayout);
        ThisLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
    }

    public void loadData(int c){
        TextView txtID = findViewById(R.id.txtID);
        EditText edtEventName = findViewById(R.id.edtName);
        EditText edtEventInfo = findViewById(R.id.edtInfo);
        EditText edtEventTime = findViewById(R.id.edtTime);
        EditText edtEventVenue = findViewById(R.id.edtVenue);

        txtID.setText(Integer.toString(eventsList.get(c).getId()));
        edtEventName.setText(eventsList.get(c).getEventName());
        edtEventInfo.setText(eventsList.get(c).getEventInformation());
        edtEventTime.setText(eventsList.get(c).getEventTime());
        edtEventVenue.setText(eventsList.get(c).getEventVenue());
    }
    public void clearData(){
        TextView txtID = findViewById(R.id.txtID);
        EditText edtEventName = findViewById(R.id.edtName);
        EditText edtEventInfo = findViewById(R.id.edtInfo);
        EditText edtEventTime = findViewById(R.id.edtTime);
        EditText edtEventVenue = findViewById(R.id.edtVenue);

        txtID.setText("");
        edtEventName.setText("");
        edtEventInfo.setText("");
        edtEventTime.setText("");
        edtEventVenue.setText("");
    }
}