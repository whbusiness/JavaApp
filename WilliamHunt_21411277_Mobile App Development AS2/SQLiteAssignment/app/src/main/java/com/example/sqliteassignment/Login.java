package com.example.sqliteassignment;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Objects;

public class Login extends AppCompatActivity {

    dbClass db;
    LinearLayout.LayoutParams params;
    public static String strEmail;
    public static String strPassword;

    public static Boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new dbClass(this);
        //db.CreateTable();
        //find the components on the Login page of the app
        EditText edtEmailLogin = findViewById(R.id.edtEmailLogin);
        EditText edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        params = new LinearLayout.LayoutParams(
                /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 2, 0, 0);
        btnLogin.setLayoutParams(params);
        btnLogin.setBackgroundColor(Color.parseColor("#FFB8FF"));
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setLayoutParams(params);
        btnRegister.setBackgroundColor(Color.parseColor("#FFB8FF"));
        TextView TxtDisplay = findViewById(R.id.TxtDisplay);
        edtEmailLogin.setTextColor(Color.parseColor("#000000"));
        edtPassword.setTextColor(Color.parseColor("#000000"));
        btnLogin.setTextColor(Color.parseColor("#000000"));
        btnRegister.setTextColor(Color.parseColor("#000000"));
        TxtDisplay.setTextColor(Color.parseColor("#000000"));
        //db.CreateTable();
        /*ProductsDBClass db2 = new ProductsDBClass(this);
        db2.DropTable();*/

        String AdminUserName = "Admin";
        String AdminPassword = "1234";
        //Create an intent that will change the current activity from MainActivity to Quiz when called
        Intent RegisterPage = new Intent(this, RegisterActivity.class);
        Intent MainScreenPage = new Intent(this, MainScreen.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //When the user presses the login button
                //Store User Input in a string
                strEmail = edtEmailLogin.getText().toString();
                strPassword = edtPassword.getText().toString();
                if(!db.CheckForLogin() && !strEmail.equals(AdminUserName) || !db.CheckForLogin() && !strPassword.equals(AdminPassword)){
                    Toast.makeText(Login.this, "Incorrect Login", Toast.LENGTH_SHORT).show();
                }
                if(db.CheckForLogin()){
                    MainScreenPage.putExtra("Username", strEmail);
                    startActivity(MainScreenPage);
                    dbClass.CorrectLogin = false;
                    isAdmin = false;
                }
                if(strEmail.equals(AdminUserName) && strPassword.equals(AdminPassword)){
                    isAdmin = true;
                    startActivity(MainScreenPage);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterPage);
            }
        });
    }
    @Override
    protected void onResume() { //called when activity goes into foreground
        super.onResume();
        //define layout
        LinearLayout ThisLayout = findViewById(R.id.LoginLayout);
        //ThisLayout.setBackgroundColor(Color.parseColor("#8F00FF"));
        ThisLayout.setBackgroundColor(getResources().getColor(R.color.teal_200));
    }
}