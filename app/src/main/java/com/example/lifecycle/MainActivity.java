package com.example.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {
    private Context context;
    private int duration = Toast.LENGTH_SHORT;
    //PLUMBING: Pairing GUI controls with Java objects
    private Button btnExit;
    private EditText txtColorSelected;
    private TextView txtSpyBox;
    private LinearLayout myScreen;
    private String PREFNAME = "nhom08";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        txtColorSelected = (EditText)findViewById(R.id.editText1);
        btnExit = (Button) findViewById(R.id.button1);
        txtSpyBox = (TextView)findViewById(R.id.textView1);
        myScreen = (LinearLayout)findViewById(R.id.myScreen1);



        btnExit.setOnClickListener(v -> {
            finish();
        });

        txtColorSelected.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { /* nothing TODO, needed by interface */ }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* nothing TODO, needed by interface */ }
            @Override
            public void afterTextChanged(Editable s) {
                //set background to selected color
                String chosenColor = s.toString().toLowerCase(Locale.US);
                txtSpyBox.setText(chosenColor);
                setBackgroundColor(chosenColor, myScreen);

            }
        });

        context = getApplicationContext();
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMeUsingSavedStateData();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String chosenColor = txtSpyBox.getText().toString();
        saveStateData(chosenColor);
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    private void setBackgroundColor(String chosenColor, LinearLayout myScreen){

        int color = getColorFromName(chosenColor);
        myScreen.setBackgroundColor(color);
    }

    public static int getColorFromName(String chosenColor) {

        if (chosenColor == null) return 0xffffffff;

        chosenColor = chosenColor.toLowerCase();

        if (chosenColor.contains("hoang")) return 0xffff0000; // RED
        if (chosenColor.contains("huy")) return 0xff00ff00;   // GREEN
        if (chosenColor.contains("phuong")) return 0xff0000ff; // BLUE
        if (chosenColor.contains("thuan")) return 0xffffffff; // WHITE
        if (chosenColor.contains("quang")) return 0xFF6200EE; // PURPLE

        return 0xffffffff;
    }

    private void saveStateData(String chosenColor){
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor myPrefEditor = myPrefContainer.edit();
        String key = "chosenBackgroundColor", value = txtSpyBox.getText().toString();
        myPrefEditor.putString(key,value);
        myPrefEditor.commit();
    }

    private void updateMeUsingSavedStateData() {
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        String key = "chosenBackgroundColor";
        String defaultValue = "white";
        if (( myPrefContainer != null ) && myPrefContainer.contains(key)){
            String color = myPrefContainer.getString(key, defaultValue);
            setBackgroundColor(color, myScreen);
        }
    }
}