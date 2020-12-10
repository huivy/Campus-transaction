package page.page1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main_page extends AppCompatActivity implements View.OnClickListener{

    String TABLENAME = "iteminfo";
    Intent intent;
    byte[] imagedata;
    Bitmap imagebm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        DatabaseHelper database = new DatabaseHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = (ListView)findViewById(R.id.listView);
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor = db.query(TABLENAME,null,null,null,null,null,null,null); // 数据库查询
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                item = new HashMap<String, Object>();  // 为列表项赋值
                item.put("id",cursor.getInt(0));
                item.put("userid",cursor.getString(1));
                item.put("title",cursor.getString(2));
                item.put("kind",cursor.getString(3));
                item.put("info",cursor.getString(4));
                item.put("price",cursor.getString(5));
                imagedata = cursor.getBlob(6);
                imagebm = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
                item.put("image",imagebm);
                cursor.moveToNext();
                data.add(item); // 加入到列表中
            }
        }
        /*
        item = new HashMap<String, Object>();
        item.put("userid","ysh");
        item.put("image", R.drawable.buy_item1);
        item.put("title","一个九成新的篮球");
        item.put("kind","体育用品");
        item.put("info", "刚买没多久，希望转卖出去...");
        item.put("price", "59元");
        data.add(item);
        item = new HashMap<String, Object>();
        item.put("userid","xg");
        item.put("image", R.drawable.buy_item2);
        item.put("title","一个八成新的篮球");
        item.put("kind","体育用品");
        item.put("info", "刚买没多久，希望转卖出去...");
        item.put("price", "59元");
        data.add(item);
        item = new HashMap<String, Object>();
        item.put("userid","hdq");
        item.put("image", R.drawable.buy_item3);
        item.put("title","一个八成新的篮球");
        item.put("kind","体育用品");
        item.put("info", "刚买没多久，希望转卖出去...");
        item.put("price", "59元");
        data.add(item);
        */
        // 使用SimpleAdapter布局listview
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.listitem, new String[] { "image", "title", "kind", "info", "price" },
                new int[] { R.id.item_image, R.id.title, R.id.kind, R.id.info, R.id.price });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView  && data instanceof Bitmap){
                    ImageView iv = (ImageView)view;
                    iv.setImageBitmap( (Bitmap)data );
                    return true;
                }else{
                    return false;
                }
            }
        });
        listView.setAdapter(simpleAdapter);


        ImageView kind1 = (ImageView) findViewById(R.id.kind1);
        kind1.setOnClickListener(this);
        ImageView kind2 = (ImageView) findViewById(R.id.kind2);
        kind2.setOnClickListener(this);
        ImageView kind3 = (ImageView) findViewById(R.id.kind3);
        kind3.setOnClickListener(this);
        ImageView kind4 = (ImageView) findViewById(R.id.kind4);
        kind4.setOnClickListener(this);
        // 为列表项设置监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(main_page.this, item_info.class);
                intent.putExtra("id", data.get(position).get("id").toString()); // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                startActivity(intent);
            }
        });

        RadioButton btn1 = (RadioButton)findViewById(R.id.button_1);
        RadioButton btn2 = (RadioButton)findViewById(R.id.button_2);
        RadioButton btn3 = (RadioButton)findViewById(R.id.button_3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.kind1:
                Intent KindIntent1 = new Intent(this,kind_page1.class);
                startActivity(KindIntent1);
                break;
            case R.id.kind2:
                Intent KindIntent2 = new Intent(this,kind_page2.class);
                startActivity(KindIntent2);
                break;
            case R.id.kind3:
                Intent KindIntent3 = new Intent(this,kind_page3.class);
                startActivity(KindIntent3);
                break;
            case R.id.kind4:
                Intent KindIntent4 = new Intent(this,kind_page4.class);
                startActivity(KindIntent4);
                break;
            case R.id.button_1:
                Intent button1 = new Intent(main_page.this,main_page.class);
                startActivity(button1);
                break;
            case R.id.button_2:
                Intent button2 = new Intent(this,AddItem.class);
                startActivity(button2);
                break;
            case R.id.button_3:
                Intent button3 = new Intent(this,MyselfActivity.class);
                startActivity(button3);
                break;

        }
    }
}
