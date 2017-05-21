package com.testandroid.chaiyasit.foodguide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class home extends AppCompatActivity {
    ListView lv;
    private final Context context = this;
    private TextView mTextMessage;
    ArrayAdapter<String> adapter;
    VivzAdapter adapter2;
    ArrayList<String>name;
    ArrayList<String>raw_material;
    public static ArrayList<FoodData>foodMenu = new ArrayList<>();
    EditText et_input;
    Button b_write,b_read;
    TextView tv_show;
    String filename = "file.txt";
    ArrayList<String> data = new ArrayList<String>();
    String str = "";
    String selectedItem;
    boolean checkCol = false;
    int [] img_food = {R.drawable.a0,R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,
            R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,
            R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17
            ,R.drawable.a18,R.drawable.a19};

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    checkCol=true;
                    b_write.setVisibility(View.GONE);
                    et_input.setVisibility(View.GONE);
                    int cnt=0;
                    ArrayList<String>MenuRec = new ArrayList<>();
                    for(FoodData tmpFood : foodMenu){
                        ArrayList<String> tmpCon = tmpFood.getData();
                        cnt=0;
                        for(String t_con : tmpCon){
                            for(String t_data : data){
                                if(t_con.equalsIgnoreCase(t_data))
                                    cnt++;
                            }
                        }
                        if(cnt==tmpFood.getData().size()){
                            MenuRec.add(tmpFood.GetmenuName());
                        }
                    }
                    setname(MenuRec);
                    lv.setAdapter(adapter2);
                    return true;
                case R.id.navigation_dashboard:
                    checkCol=true;
                    // Raw_math();
                    //ArrayList<String> tmp = readfile(filename);
                    Raw_math();
                    b_write.setVisibility(View.GONE);
                    et_input.setVisibility(View.GONE);
                    lv.setAdapter(adapter);
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    Raw_math();
                    b_write.setVisibility(View.VISIBLE);
                    et_input.setVisibility(View.VISIBLE);
                    et_input.setText("");
                    et_input.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            et_input.setText("");
                        }
                    });
                    //mTextMessage.setText("Hello");
                    checkCol=false;
                    lv.setAdapter(adapter);
                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //คลิกแบบ 3 วิขึ้นไป
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            selectedItem = ((TextView) view).getText().toString();
                            if(checkCol==false) {
                                if (selectedItem.equalsIgnoreCase(data.get(position))) {
                                    checkCol = true;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("Do you want to remove " + selectedItem + "?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            data.remove(position);
                                            save(filename, data);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    // Create and show the dialog
                                    builder.show();
                                }
                            }
                            // Signal OK to avoid further processing of the long click
                            return true;
                        }
                    });
                    return true;
            }
            return false;
        }

    };
    private void Raw_math(){
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, data);
    }
    private void setname(ArrayList<String> name){
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, name);
        adapter2 = new VivzAdapter(this,name,img_food);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        FoodData tmpD = new FoodData("ผัดกะเพราหมู");
        String [] tmp = {"หมู","กะเพรา","พริก","กระเทียม"};
        tmpD.addData(tmp);//add to array
        String [] tmp_con = {"เนื้อหมู","กะเพราเด็ดเป็นใบ","น้ำปลา","พริกขี้หนู","กระเทียม","พริกเหลือง","น้ำตาลปีป","น้ำซุป","น้ำมัน"};
        tmpD.addCondiment(tmp_con);
        foodMenu.add(tmpD);//add to Arraylist
        FoodData tmpD1 = new FoodData("ผัดกะเพราไก่");
        String [] tmp1 = {"ไก่","กะเพรา","พริก","กระเทียม"};
        tmpD1.addData(tmp1);//add to array
        String [] tmp1_con = {"เนื้อไก่","กะเพราเด็ดเป็นใบ","น้ำปลา","พริกขี้หนู","กระเทียม","พริกเหลือง","น้ำตาลปีป","น้ำซุป","น้ำมัน"};
        tmpD1.addCondiment(tmp1_con);
        foodMenu.add(tmpD1);//add to Arraylist
        FoodData tmpD2 = new FoodData("ผัดกะเพราทะเล");
        String [] tmp2 = {"ปลาหมึก","กุ้ง","กะเพรา","พริก","กระเทียม"};
        tmpD2.addData(tmp2);//add to array
        String [] tmp2_con = {"ปลาหมึก","กุ้ง","กะเพราเด็ดเป็นใบ","น้ำปลา","พริกขี้หนู","กระเทียม","น้ำตาลปีป","น้ำซุป","น้ำมัน"};
        tmpD2.addCondiment(tmp2_con);
        foodMenu.add(tmpD2);//add to Arraylist
        FoodData tmpD3 = new FoodData("แกงจืดฟัก");
        String [] tmp3 = {"หมูสับ","ฟัก","เห็ดหอม"};
        String [] tmp3_con = {"หมูสับ","ฟัก","เห็ดหอม","เกลือป่น","ซีอิ๊วขาว","ผงปรุงรส"};
        tmpD3.addCondiment(tmp3_con);
        tmpD3.addData(tmp3);//add to array
        foodMenu.add(tmpD3);//add to Arraylist
        FoodData tmpD4 = new FoodData("แกงจืดผักกาดขาว");
        String [] tmp4 = {"หมูสับ","ผักกาดขาว","เห็ดหูหนู","เต้าหู้ไข่","แครอท"};
        String [] tmp4_con = {"หมูสับ","ผักกาดขาว","เห็ดหูหนู","เต้าหู้ไข่","แครอท","เกลือป่น","ซีอิ๊วขาว","ผงปรุงรส"};
        tmpD4.addCondiment(tmp4_con);
        tmpD4.addData(tmp4);//add to array
        foodMenu.add(tmpD4);//add to Arraylist
        FoodData tmpD5 = new FoodData("ปลานิลราดพริก");
        String [] tmp5 = {"ปลานิล","กระเทียม","พริก","มะขามเปียก"};
        String [] tmp5_con = {"ปลานิล","กระเทียม","พริก","มะขามเปียก","น้ำตาลปี๊บ","น้ำเปล่า","เกลือ","น้ำมัน"};
        tmpD5.addCondiment(tmp5_con);
        tmpD5.addData(tmp5);//add to array
        foodMenu.add(tmpD5);//add to Arraylist
        FoodData tmpD6 = new FoodData("ต้มยำกุ้ง");
        String [] tmp6 = {"กุ้ง","พริก","เห็ดฟาง","ข่า","ใบมะกรูด","ตะไคร้","พริกเผา","มะนาว"};
        String [] tmp6_con = {"กุ้ง","พริก","เห็ดฟาง","ข่า","ใบมะกรูด","ตะไคร้","มะนาว","พริกเผา","น้ำเปล่า","เกลือ","กะทิหรือนมข้นจืด","น้ำตาล"};
        tmpD6.addCondiment(tmp6_con);
        tmpD6.addData(tmp6);//add to array
        foodMenu.add(tmpD6);//add to Arraylist
        FoodData tmpD7 = new FoodData("ต้มยำทะเล");
        String [] tmp7_con = {"ปลาหมึก","กุ้ง","พริก","เห็ดฟาง","ข่า","ใบมะกรูด","ตะไคร้","มะนาว","พริกเผา","น้ำเปล่า","เกลือ","กะทิหรือนมข้นจืด","น้ำตาล"};
        tmpD7.addCondiment(tmp7_con);
        String [] tmp7 = {"ปลาหมึก","กุ้ง","พริก","เห็ดฟาง","ข่า","ใบมะกรูด","ตะไคร้","มะนาว","พริกเผา"};
        tmpD7.addData(tmp7);//add to array
        foodMenu.add(tmpD7);//add to Arraylist
        FoodData tmpD8 = new FoodData("คะน้าหมูกรอบ");
        String [] tmp8 = {"หมูกรอบ","พริก","กระเทียม","คะน้า"};
        tmpD8.addData(tmp8);//add to array
        String [] tmp8_con = {"หมูกรอบ","พริก","กระเทียม","คะน้า","ซอสหอยนางรม","เต้าเจี้ยว","น้ำซุป","น้ำมัน","น้ำตาลทราย"};
        tmpD8.addCondiment(tmp8_con);
        foodMenu.add(tmpD8);//add to Arraylist
        FoodData tmpD9 = new FoodData("คะน้าหมูชิ้น");
        String [] tmp9 = {"หมูชิ้น","พริก","กระเทียม","คะน้า"};
        String [] tmp9_con = {"หมูชิ้น","พริก","กระเทียม","คะน้า","ซอสหอยนางรม","เต้าเจี้ยว","น้ำซุป","น้ำมัน","น้ำตาลทราย"};
        tmpD9.addCondiment(tmp9_con);
        tmpD9.addData(tmp9);//add to array
        foodMenu.add(tmpD9);//add to Arraylist
        FoodData tmpD10 = new FoodData("คะน้าน้ำมันหอย");
        String [] tmp10 = {"หมู","คะน้า","กระเทียม"};
        String [] tmp10_con = {"หมู","คะน้า","กระเทียม","ซีอิ้วขาว","น้ำมัน","น้ำตาลทราย"};
        tmpD10.addCondiment(tmp10_con);
        tmpD10.addData(tmp10);//add to array
        foodMenu.add(tmpD10);//add to Arraylist
        FoodData tmpD11 = new FoodData("ผัดพริกเผาหมูกรอบ");
        String [] tmp11 = {"หมูกรอบ","พริกเผา","โหระพา"};
        tmpD11.addData(tmp11);//add to array
        String [] tmp11_con = {"หมูกรอบ","พริกเผา","โหระพา","ซีอิ้วขาว","น้ำมัน","น้ำตาลทราย"};
        tmpD11.addCondiment(tmp11_con);
        foodMenu.add(tmpD11);//add to Arraylist
        FoodData tmpD12 = new FoodData("ผัดพริกเผาไก่");
        String [] tmp12 = {"ไก่","พริกเผา","โหระพา"};
        String [] tmp12_con = {"ไก่","พริกเผา","โหระพา","ซีอิ้วขาว","น้ำมัน","น้ำตาลทราย"};
        tmpD12.addCondiment(tmp12_con);
        tmpD12.addData(tmp12);//add to array
        foodMenu.add(tmpD12);//add to Arraylist
        FoodData tmpD13 = new FoodData("สุกี้");
        String [] tmp13 = {"หมู","ผักบุ้ง","ผักกาดขาว","ไข่"};
        tmpD13.addData(tmp13);//add to array
        String [] tmp13_con = {"หมู","ผักบุ้ง","ผักกาดขาว","ไข่","น้ำซุป","น้ำตาลทราย","น้ำจิ้มสุกี้"};
        tmpD13.addCondiment(tmp13_con);
        foodMenu.add(tmpD13);//add to Arraylist
        FoodData tmpD14 = new FoodData("ผัดเปรี้ยวหวานทะเล");
        String [] tmp14 = {"กุ้ง","กระเทียม","แตงกวา","ปลาหมึก","พริกหยวก","หอมหัวใหญ่","มะเขือเทศ","ต้นหอม"};
        tmpD14.addData(tmp14);//add to array
        String [] tmp14_con = {"กุ้ง","กระเทียม","แตงกวา","ปลาหมึก","พริกหยวก","หอมหัวใหญ่","มะเขือเทศ","ต้นหอม","ซอสมะเขือเทศ","น้ำตาล","เกลือ","น้ำมัน","น้ำปลา","ซอสปรุงรส"};
        tmpD14.addCondiment(tmp14_con);
        foodMenu.add(tmpD14);//add to Arraylist
        FoodData tmpD15 = new FoodData("ปูผัดผงกะหรี่");
        String [] tmp15 = {"ปูทะเล","หอมหัวใหญ่","ต้นหอม","ไข่ไก่","พริก","กระเทียม"};
        tmpD15.addData(tmp15);//add to array
        String [] tmp15_con = {"ปูทะเล","หอมหัวใหญ่","ต้นหอม","ไข่ไก่","พริก","กระเทียม","ผงกะหรี่","ซอสหอย","ซีอิ๊วขาว","ซอสพริก","พริกเผา","น้ำตาลทราย","พริกบด","นมสดจืด","น้ำมันพริกเผา"};
        tmpD15.addCondiment(tmp15_con);
        foodMenu.add(tmpD15);//add to Arraylist
        FoodData tmpD16 = new FoodData("ข้าวผัดหมู");
        String [] tmp16 = {"หมู","มะเขือเทศ","ต้นหอม","กระเทียม","ไข่"};
        tmpD16.addData(tmp16);//add to array
        String [] tmp16_con = {"หมู","มะเขือเทศ","ต้นหอม","กระเทียม","ไข่","เกลือ","น้ำปลา","พริกไทย","ซอสหอย","ซีอิ๊วขาว"};
        tmpD16.addCondiment(tmp16_con);
        foodMenu.add(tmpD16);//add to Arraylist
        FoodData tmpD17 = new FoodData("ข้าวผัดกุ้ง");
        String [] tmp17 = {"กุ้ง","มะเขือเทศ","ต้นหอม","กระเทียม","ไข่"};
        tmpD17.addData(tmp17);//add to array
        String [] tmp17_con = {"กุ้ง","มะเขือเทศ","ต้นหอม","กระเทียม","ไข่","เกลือ","น้ำปลา","พริกไทย","ซอสหอย","ซีอิ๊วขาว"};
        tmpD17.addCondiment(tmp17_con);
        foodMenu.add(tmpD17);//add to Arraylist
        FoodData tmpD18 = new FoodData("ปลาหมึกนึ่งมะนาว");
        String [] tmp18 = {"ปลาหมึก","มะนาว","กระเทียม","ผักชี","พริก"};
        tmpD18.addData(tmp18);//add to array
        String [] tmp18_con = {"ปลาหมึก","มะนาว","กระเทียม","ผักชี","พริก","น้ำปลา","น้ำตาลทราย","น้ำซุป หรือน้ำเปล่า","ซีอิ๊วขาว"};
        tmpD18.addCondiment(tmp18_con);
        foodMenu.add(tmpD18);//add to Arraylist
        FoodData tmpD19 = new FoodData("ปลานิลนึ่งมะนาว");
        String [] tmp19 = {"ปลานิล","มะนาว","กระเทียม","ผักชี","พริก"};
        tmpD19.addData(tmp19);//add to array
        String [] tmp19_con = {"ปลานิล","มะนาว","กระเทียม","ผักชี","พริก","น้ำปลา","น้ำตาลทราย","น้ำซุป หรือน้ำเปล่า","ซีอิ๊วขาว"};
        tmpD19.addCondiment(tmp19_con);
        foodMenu.add(tmpD19);//add to Arraylist

//getMenuData
        name = new ArrayList<>();
        //raw_material = new ArrayList<>();
//        for (FoodData tt : foodMenu) {
//            //System.out.println(tt.GetmenuName());
//            name.add(tt.GetmenuName());
//            if(raw_material.size()<5)
//                raw_material.add(tt.GetmenuName());
////            for(String t : tt.getData()){
////                System.out.println(t);
////            }
//        }


        data = readfile(filename);
        int cnt=0;
        //check menu recoment
        ArrayList<String>MenuRec = new ArrayList<>();
        for(FoodData tmpFood : foodMenu){
            ArrayList<String> tmpCon = tmpFood.getData();
            cnt=0;
            for(String t_con : tmpCon){
                for(String t_data : data){
                    if(t_con.equalsIgnoreCase(t_data))
                        cnt++;
                }
            }
            if(cnt==tmpFood.getData().size()){
                MenuRec.add(tmpFood.GetmenuName());
            }
        }
//        name = MenuRec;
        et_input = (EditText) findViewById(R.id.et_input);
        b_write = (Button) findViewById(R.id.b_save);
        b_write.setVisibility(View.GONE);
        et_input.setVisibility(View.GONE);


        b_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //data.add(et_input.getText().toString());
                boolean check_haveStock = false;
                for(String t_d : data){
                    if(et_input.getText().toString().equalsIgnoreCase(t_d)){
                        check_haveStock = true; break;
                    }
                }
                if(check_haveStock==false){
                    data.add(et_input.getText().toString());
                    save(filename,data);
                    Toast.makeText(home.this, "Add item success.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(home.this, "Item is haved.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //mTextMessage = (TextView) findViewById(R.id.message);
        lv = (ListView) findViewById(R.id.menuList);
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, name);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setname(MenuRec);

        lv.setAdapter(adapter2);
////testSavefile
//        FileOutputStream outputStream;
//
//        try {
//            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            outputStream.close();
//            Toast.makeText(home.this, "Add item success.",Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(home.this, "Error add item",Toast.LENGTH_SHORT).show();
//        }
////endtest
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String t_getPos = lv.getItemAtPosition(position).toString();
                //Toast.makeText(home.this, position+"",Toast.LENGTH_SHORT).show();
                for(FoodData t_food : foodMenu){
                    if(t_food.GetmenuName().equalsIgnoreCase(t_getPos)){
                        Intent intent = new Intent(home.this,calActivity.class);
                        intent.putExtra("menuName",t_getPos);
                        intent.putExtra("index", foodMenu.indexOf(t_food)+"");
                        //Toast.makeText(home.this,foodMenu.indexOf(t_food)+"",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        break;
                    }
                }
//                Intent intent = new Intent(home.this,calActivity.class);
//                intent.putExtra("Country_name",t_getPos);
//                intent.putExtra("data", foodMenu.indexOf(t_getPos)+"");
//                startActivity(intent);

            }
        });
    }
    public void save(String filename,ArrayList<String> data){
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            for (String text: data) {
                if(text.equalsIgnoreCase("")||text.equalsIgnoreCase("\n")){}
                else{
                    text+="\n";
                    outputStream.write(text.getBytes());
                }
                //outputStream.flush();
            }
            outputStream.close();
//            Toast.makeText(home.this, "Add item success.",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(home.this, "Error add item",Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> readfile(String filename){
        ArrayList<String>dataoutput = new ArrayList<>();
        String text = "";
        try {
            FileInputStream file = openFileInput(filename);
            int size = file.available();
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();
            text = new String(buffer);
            //Toast.makeText(home.this, "Read file",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(home.this, "Error read file",Toast.LENGTH_SHORT).show();
        }
        String[] splt = text.split("\n");
        for(String txt : splt){
            if(txt.equalsIgnoreCase("\n")||txt.equalsIgnoreCase("")){}
            else{
                dataoutput.add(txt);
            }
        }
        return dataoutput;
    }
    class VivzAdapter extends ArrayAdapter<String>{
        Context context;
        int[] tmp_img;
        ArrayList<String> data = new ArrayList<>();

        VivzAdapter(Context c,ArrayList<String> l,int[]img_smallFood){
            super(c,R.layout.list_view_image_layout,R.id.tv_name,l);
            this.tmp_img = img_smallFood;
            this.context = c;
            this.data = l;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_view_image_layout,parent,false);
            ImageView image = (ImageView) row.findViewById(R.id.imageView);
            TextView title = (TextView) row.findViewById(R.id.tv_name);
            ArrayList<FoodData> tmp_data = home.foodMenu;
            int index_of=0;
            for(FoodData tmp_d : tmp_data){
                if(tmp_d.GetmenuName().equalsIgnoreCase(data.get(position))){
                    index_of = tmp_data.indexOf(tmp_d);
                    break;
                }
            }

            image.setImageResource(tmp_img[index_of]);
            title.setText(data.get(position));

            return row;
        }
    }
}


