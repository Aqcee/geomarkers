package examplecom.geomarkers;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainLayoutActivity extends AppCompatActivity implements View.OnClickListener {
int id = 0;
    boolean openornot=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //db.delete("geomarkers",null,null);            //если присутствуют баги разкоммитить это и запустить один раз (очищает Базу данных)

        Integer[] MassIntro = new Integer[]{R.id.TodoList};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }


        LinearLayout layout = (LinearLayout) findViewById(R.id.others);
        layout.removeAllViewsInLayout();


        // layout.removeAllViewsInLayout(); ////если присутствуют баги разкоммитить это и запустить один раз
        //дальше происходят страшные вещи которые я сам не понимаю однако попытался прокоментировать;

        //запросы к БД + построение списка
        Cursor cursor = db.query("geomarkers",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int columnIdIndex = cursor.getColumnIndex("id");
            int columnNameIndex = cursor.getColumnIndex("name");
            int columnDescIndex = cursor.getColumnIndex("description");
            do{
                int id = cursor.getInt(columnIdIndex);
                String name = cursor.getString(columnNameIndex);
                String description = cursor.getString(columnDescIndex);
                LayoutInflater inflater = LayoutInflater.from(this);
                LinearLayout custom_layout = (LinearLayout) inflater.inflate(R.layout.empty_marker, null, false);
                TextView textmarker = (TextView) custom_layout.findViewById(R.id.name);
                ImageView shesterenka = (ImageView) custom_layout.findViewById(R.id.settings);
                shesterenka.setOnClickListener(this);
                TextView desc = (TextView) custom_layout.findViewById(R.id.descriptionview);
                desc.setText(description);
                custom_layout.setId(id);
                textmarker.setText(name);
                custom_layout.setOnClickListener(this);
                layout.addView(custom_layout);
                Integer[] MassRoboto = new Integer[]{R.id.name, R.id.description_text};
                for (Integer i = 0; i < MassRoboto.length; i++) {
                    TextView lay = (TextView) custom_layout.findViewById(MassRoboto[i]);
                    lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
                }
            }while(cursor.moveToNext());
        }
        else{
            //нихера не делать
        }
        cursor.close();
        dbHelper.close();


        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout addbutton = new LinearLayout(this);
        int gravity = Gravity.CENTER;
        lParams.gravity = gravity;
        addbutton.setOrientation(LinearLayout.VERTICAL);
        addbutton.setBottom(5);
        addbutton.setBackgroundResource(R.drawable.default_rectangle);
        addbutton.setOnClickListener(this);
        addbutton.setId(R.id.addbutton);
        layout.addView(addbutton, lParams);
        LinearLayout addplus = (LinearLayout) findViewById(R.id.addbutton);
        ImageView imgv = new ImageView(this);
        imgv.setBackgroundResource(R.drawable.plus);
        LinearLayout.LayoutParams plusParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plusParams.gravity = gravity;
        plusParams.setMargins(10, 10, 10, 10);
        plusParams.gravity = Gravity.CENTER;
        addplus.addView(imgv, plusParams);



        TextView textviewdesc = (TextView)findViewById(R.id.descriptionview);
        textviewdesc.setVisibility(View.GONE);
        LinearLayout gonedlayout = (LinearLayout) findViewById(R.id.main_action);
        gonedlayout.setVisibility(View.GONE);
        ImageView imagegone = (ImageView) findViewById(R.id.more);
        imagegone.setVisibility(View.VISIBLE);
    }




    @Override
    public void onClick(View v) {


    if(v.getId()==R.id.addbutton) {
        Intent intObj = new Intent(this, SecondActivity.class); //стартуем СекондАктивити.класс
        startActivity(intObj);
    }
    else if (v.getId()==R.id.settings) {
        Intent intObj = new Intent(this, SecondActivity.class); //стартуем СекондАктивити.класс для редактирования или удаления заметок
        LinearLayout layoutparent = (LinearLayout)v.getParent().getParent().getParent().getParent().getParent();
        id =  layoutparent.getId();;
        intObj.putExtra("id",id);
        startActivity(intObj);
        v.getParent();
    }
    else{
        /**/

        if(!openornot) {
            try {
                LinearLayout gonedlayout = (LinearLayout) v.findViewById(R.id.main_action);
                gonedlayout.setVisibility(View.VISIBLE);
                TextView textviewdesc = (TextView)findViewById(R.id.descriptionview);
                textviewdesc.setVisibility(View.VISIBLE);
                ImageView imagegone = (ImageView) v.findViewById(R.id.more);
                imagegone.setVisibility(View.GONE);

                openornot = !openornot;
            }catch (Exception e){
                Log.e("error",e.toString());
            }

        }
        else{
            try {
                LinearLayout gonedlayout = (LinearLayout) v.findViewById(R.id.main_action);
                gonedlayout.setVisibility(View.GONE);
                TextView textviewdesc = (TextView)findViewById(R.id.descriptionview);
                textviewdesc.setVisibility(View.GONE);
                ImageView imagegone = (ImageView) v.findViewById(R.id.more);
                imagegone.setVisibility(View.VISIBLE);
                openornot = !openornot;
            }catch (Exception e){
                Log.e("error",e.toString());
            }
            }
    }
    }
}











