package com.sanket.royalteleadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] services={"BSNL FIBER","RAIL WIRE"};
    ImageButton imageButton;
    Button submit;
    EditText editText,content;
    Spinner serve;
    String time , title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       serve =(Spinner) findViewById(R.id.spinner);
        serve.setOnItemSelectedListener(this);
        ArrayAdapter service = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,services);
        serve.setAdapter(service);
        editText=(EditText) findViewById(R.id.editTextTime);
        content=(EditText)findViewById(R.id.desc);
        imageButton=(ImageButton) findViewById(R.id.pick);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialoge();
            }
        });







    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        title =serve.getSelectedItem().toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showTimePicker(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {

            private TimePicker timePicker;
            private int selectedHour;
            private int selectedMinute;



            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                this.timePicker = timePicker;
                this.selectedHour = selectedHour;
                this.selectedMinute = selectedMinute;
                editText.setText(selectedHour+":"+selectedMinute);
                time= editText.getText().toString();



            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
    private void alertDialoge() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(title + "at"+ time);
        builder.setMessage(content.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}