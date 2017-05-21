package com.testandroid.chaiyasit.foodguide;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class menu extends AppCompatActivity {

    EditText et_input;
    Button b_write,b_read;
    TextView tv_show;
    String filename = "file.txt";
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> dataoutput = new ArrayList<String>();
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        et_input = (EditText) findViewById(R.id.et_input);
        b_write = (Button) findViewById(R.id.b_save);
        b_read = (Button) findViewById(R.id.b_read);
        tv_show = (TextView) findViewById(R.id.tv_show);

        data.add(et_input.getText().toString());

        b_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_show.setText(readfile(filename));

            }
        });

        b_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(filename,data);

            }
        });

    }

    public void save(String filename,ArrayList<String> data){
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            for (String text: data) {
                outputStream.write(text.getBytes());
            }
            outputStream.close();
            Toast.makeText(menu.this, "Save file",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(menu.this, "Error save file",Toast.LENGTH_SHORT).show();
        }

    }

    public String readfile(String filename) {
        String text = "";
        try {
            FileInputStream file = openFileInput(filename);
            int size = file.available();
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();
            text = new String(buffer);
            Toast.makeText(menu.this, "Read file",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(menu.this, "Error read file",Toast.LENGTH_SHORT).show();
        }



        return text;
    }
}
