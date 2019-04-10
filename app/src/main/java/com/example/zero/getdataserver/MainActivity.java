package com.example.zero.getdataserver;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_Sensor1,tv_Sensor2,tv_Sensor3,tv_Sensor4,tv_id;

    String url_create = "http://104.248.159.38/ambildata" ;

    DatabaseHelper mDB;
    String dataJason;
    String sen1;

    String mdataID,mdataSensor1,mdataSensor2,mdataSensor3,mdataSensor4;

    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB        = new DatabaseHelper(this);
        tv_Sensor1 = findViewById(R.id.dataSensor1);
        tv_Sensor2 = findViewById(R.id.dataSensor2);
        tv_Sensor3 = findViewById(R.id.dataSensor3);
        tv_Sensor4 = findViewById(R.id.dataSensor4);
        tv_id      = findViewById(R.id.data_id);

        btnShow = findViewById(R.id.btn_show);

        btnShow.setOnClickListener(this);

        TimerServer tmServer = new TimerServer();
        Timer timer1 = new Timer();

        timer1.schedule(tmServer, 1000, 5000);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,ShowData.class);
        startActivity(intent);
    }

    class TimerServer extends TimerTask {

        final class kirimServer extends AsyncTask<String, String, JSONObject> {

            @Override
            protected JSONObject doInBackground(String... strings) {
                JSONParser jsonParser = new JSONParser();
                JSONObject json;
                List<NameValuePair> params = new ArrayList<>();

                json = jsonParser.makeHttpRequest(url_create, "GET", params);


                try {

//                    dataJason = json.getString("voltage_satu");

//                    dataJason = json.toString();
//                    return dataJason;

                    return json;


                } catch (Exception e) {
                    e.printStackTrace();

                }

                return null;
            }

            @Override
            protected void onPostExecute(JSONObject s) {
                super.onPostExecute(s);

                try {

                    mdataID         = s.getString("id");
                    mdataSensor1    = s.getString("sensor1");
                    mdataSensor2    = s.getString("sensor2");
                    mdataSensor3    = s.getString("sensor3");
                    mdataSensor4    = s.getString("sensor4");


                    tv_id.setText(mdataID);
                    tv_Sensor1.setText(mdataSensor1);
                    tv_Sensor2.setText(mdataSensor2);
                    tv_Sensor3.setText(mdataSensor3);
                    tv_Sensor4.setText(mdataSensor4);

                    boolean suksesDatabase = mDB.insertData(mdataID,mdataSensor1,mdataSensor2,mdataSensor3,mdataSensor4);
                    if(suksesDatabase) {
                        //tampilkan data berhasil di imput
                        showToast("Data berhasil disimpan");
                    } else {
                        showToast("Data gagal disimpan");
                    }

                }catch (Exception e){

                    e.printStackTrace();
                }

//                tv_Sensor1.setText(s);
//
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void run() {
            kirimServer runServer = new kirimServer();

            runServer.execute();
        }

        private void showToast(String text){
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

}
