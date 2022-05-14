package com.sanket.royalteleadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String ONESIGNAL_APP_ID = "2f387f60-5f5d-4b02-99f6-1dfe6e3d960b";

    String[] services={"BSNL FIBER","RAILWIRE"};
    ImageButton imageButton;
    Button submit;
    EditText editText,content;
    Spinner serve;
    String time , title, header;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
       serve =(Spinner) findViewById(R.id.spinner);
        serve.setOnItemSelectedListener(this);
        ArrayAdapter service = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,services);
        serve.setAdapter(service);
        editText=(EditText) findViewById(R.id.editTextTime);
        content=(EditText)findViewById(R.id.desc);
        videoView =(VideoView)findViewById(R.id.vview);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.techbg);
        videoView.setVideoURI(uri);

        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });


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

                //
                // alertDialoge();
                sendNotification();
            }
        });
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);







    }

    private void sendNotification() {

        OSDeviceState deviceState=OneSignal.getDeviceState();
        String userId=deviceState.getUserId();


        try {
            OneSignal.postNotification(new JSONObject("{'contents': {'en':'Tag substitution value for key1 = {{key1}}'}, " +
                            "'app_id':{ '2f387f60-5f5d-4b02-99f6-1dfe6e3d960b'},"+
                            "'included_segments':{ ['All']},"+
                            "'headings': {'en': 'Tag sub Title HI {{user_name}}'}, " +
                            "'data': {'openURL': 'https://imgur.com'}," +
                            "'buttons':[{'id': 'id1', 'text': 'Go to GreenActivity'}, {'id':'id2', 'text': 'Go to MainActivity'}]}"),
                    new OneSignal.PostNotificationResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject response) {

                            Toast.makeText(getApplicationContext() , " Success :  "+ response.toString(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(JSONObject response) {
                            Toast.makeText(getApplicationContext() , " failure :  "+ response.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext() , " exception :  "+e , Toast.LENGTH_LONG).show();
        }

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
        builder.setTitle(title + " at "+ time);
        builder.setMessage(content.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

}