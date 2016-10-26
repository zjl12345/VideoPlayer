package com.feicuiedu.videonews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feicuiedu.videonews.DemoA.DemoAactivity;
import com.feicuiedu.videonews.DemoB.DemoBactivity;
import com.feicuiedu.videonews.DemoC.DemoCactivity;
import com.feicuiedu.videonews.DemoD.DemoDactivity;
import com.feicuiedu.videonews.DemoE.DemoEactivity;
import com.feicuiedu.videonews.DemoF.DemoFactivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView mLv;
    private ArrayAdapter<String> adapter;
    private String[] datas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv = (ListView) findViewById(R.id.main_lv);

        datas = new String[]{
                "A Andorid MedioPlayer+SurfaceView",
                "B Vitamio MediaPlayer+SurfaceView",
                "C Android VideoView",
                "D Vitamio VideoView",
                "E Vitamio MediaController",
                "F Vitamio Buffer",
                "G test videoView"
        };

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);

        mLv.setAdapter(adapter);

        mLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent.setClass(this, DemoAactivity.class);
                startActivity(intent);
                break;
            case 1:
                intent.setClass(this, DemoBactivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, DemoCactivity.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(this, DemoDactivity.class);
                startActivity(intent);
                break;
            case 4:
                intent.setClass(this, DemoEactivity.class);
                startActivity(intent);
                break;
            case 5:
                intent.setClass(this, DemoFactivity.class);
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
        }
    }
}

