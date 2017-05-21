package com.testandroid.chaiyasit.foodguide;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class calActivity extends AppCompatActivity {

//    Toolbar toolbar2;
    ImageView imv;
    TextView tv;
    ListView listV;
    ArrayAdapter<String> adp;
    int [] img_food = {R.drawable.d0,R.drawable.d1,R.drawable.d2,R.drawable.d3,R.drawable.d4,
            R.drawable.d5,R.drawable.d6,R.drawable.d7,R.drawable.d8,R.drawable.d9,R.drawable.d10,
            R.drawable.d11,R.drawable.d12,R.drawable.d13,R.drawable.d14,R.drawable.d15,R.drawable.d16
            ,R.drawable.d17,R.drawable.d18,R.drawable.d19};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_layout);
        //Toast.makeText(calActivity.this, "calAct",Toast.LENGTH_SHORT).show();
//        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        tv = (TextView) findViewById(R.id.tv_title);

        imv = (ImageView) findViewById(R.id.imv2);
        Bundle bundle = getIntent().getExtras();
        //Toast.makeText(calActivity.this, "calAct",Toast.LENGTH_SHORT).show();
        if(bundle != null){
           // toolbar2.setTitle(bundle.getString("Country_name"));
            ArrayList<FoodData> food = home.foodMenu;
           // Toast.makeText(calActivity.this, bundle.getString("index"),Toast.LENGTH_SHORT).show();
            int index_food = Integer.parseInt(bundle.getString("index"));
            FoodData tmpFood = food.get(index_food);
            ArrayList<String> strCon = tmpFood.getCondiment();
            String str = "";
            listV = (ListView) findViewById(R.id.conList);
            adp = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, strCon);
            listV.setAdapter(adp);
            tv.setText(bundle.getString("menuName"));

            imv.setImageDrawable(ContextCompat.getDrawable(calActivity.this, img_food[index_food]));


        }
    }
}
