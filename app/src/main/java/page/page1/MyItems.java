package page.page1;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static page.page1.LoginMainActivity.post_userid;

public class MyItems extends AppCompatActivity implements View.OnClickListener{
    String TABLENAME = "iteminfo";
    byte[] imagedata;
    Bitmap imagebm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper database = new DatabaseHelper(this);
        final SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = (ListView)findViewById(R.id.show_fabu);
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
                //kind1.setImageBitmap(imagebm);
                item.put("image",imagebm);
                cursor.moveToNext();
                data.add(item); // 加入到列表中
            }
        }
        /*
        item = new HashMap<String, Object>();
        item.put("id",1);
        item.put("userid","ysh");
        item.put("image", R.drawable.buy_item1);
        item.put("title","一个九成新的篮球");
        item.put("kind","体育用品");
        item.put("info", "刚买没多久，希望转卖出去...");
        item.put("price", "59元");
        data.add(item);
        item = new HashMap<String, Object>();
        item.put("id",2);
        item.put("userid","xg");
        item.put("image", R.drawable.buy_item2);
        item.put("title","一个八成新的篮球");
        item.put("kind","体育用品");
        item.put("info", "刚买没多久，希望转卖出去...");
        item.put("price", "59元");
        data.add(item);
        item = new HashMap<String, Object>();
        item.put("id",3);
        item.put("userid","hdq");
        item.put("image", R.drawable.buy_item3);
        item.put("title","一个八成新的篮球");
        item.put("kind","体育用品");
        item.put("info", "刚买没多久，希望转卖出去...");
        item.put("price", "59元");
        data.add(item);
        */
        // 使用SimpleAdapter布局listview
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.activity_my_fabu, new String[] { "image", "title", "kind", "info", "price" },
                new int[] { R.id.item_image, R.id.title, R.id.kind, R.id.info, R.id.price });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView iv = (ImageView)view;
                    iv.setImageBitmap( (Bitmap)data );
                    return true;
                }else{
                    return false;
                }
            }
        });
        listView.setAdapter(simpleAdapter);

        Button button1=(Button)findViewById(R.id.but1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyItems.this,main_page.class);
                startActivity(intent);
            }
        });

        Button button2=(Button)findViewById(R.id.but2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyItems.this,MyItems.class);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String delId = data.get(position).get("id").toString();
                if(db.delete(TABLENAME,"id=?",new String[]{delId}) > 0) {
                    Toast.makeText(getApplicationContext(), "删除成功，请刷新", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else {
                    return false;
                }
            }
        });

    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
        }
    }
}
