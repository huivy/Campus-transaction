package page.page1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static page.page1.LoginMainActivity.post_userid;

public class item_info extends AppCompatActivity {
    String TABLENAME = "iteminfo";
    byte[] imagedata;
    Bitmap imagebm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);
        final DatabaseHelper dbtest = new DatabaseHelper(this);
        final Intent intent = getIntent();
        final SQLiteDatabase db = dbtest.getWritableDatabase();
        ImageView image = (ImageView)findViewById(R.id.imageView);
        TextView price = (TextView)findViewById(R.id.item_price);
        TextView title = (TextView)findViewById(R.id.item_title) ;
        TextView info = (TextView)findViewById(R.id.item_info);
        TextView contact = (TextView)findViewById(R.id.contact);
        Cursor cursor = db.query(TABLENAME,null,"id=?",new String[]{intent.getStringExtra("id")},null,null,null,null); // 根据接收到的id进行数据库查询
        Log.i("商品的id是",intent.getStringExtra("id"));
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                imagedata = cursor.getBlob(6);
                imagebm = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
                image.setImageBitmap(imagebm);
                title.setText(cursor.getString(2));
                price.setText(cursor.getString(5));
                info.setText(cursor.getString(4));
                contact.setText(cursor.getString(8));
                cursor.moveToNext();
            }
        }
        ListView commentList = (ListView)findViewById(R.id.commentList);
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor_ = db.query("comments",null,"itemId=?",new String[]{intent.getStringExtra("id")},null,null,null,null); // 数据库查询
        if (cursor_.moveToFirst()){
            while (!cursor_.isAfterLast()){
                item = new HashMap<String, Object>();  // 为列表项赋值
                item.put("userId",cursor_.getString(0));
                item.put("comment",cursor_.getString(2));
                item.put("time",cursor_.getString(3));
                cursor_.moveToNext();
                data.add(item); // 加入到列表中
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.comment_item, new String[] { "userId", "comment", "time"},
                new int[] { R.id.userId, R.id.commentInfo, R.id.time });
        commentList.setAdapter(simpleAdapter);
        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment = (EditText)findViewById(R.id.comment);
                String submit_comment = comment.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                Date curDate = new Date(System.currentTimeMillis());
                String time = formatter.format(curDate);
                ContentValues values=new ContentValues();
                values.put("userId",post_userid);
                values.put("itemId",intent.getStringExtra("id"));
                values.put("comment",submit_comment);
                values.put("time",time);
                db.insert("comments",null,values);
                Log.i("1","评论成功");
                Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
                Intent intent_=new Intent(item_info.this,item_info.class);
                intent_.putExtra("id",intent.getStringExtra("id"));
                startActivity(intent_);
            }
        });
    }
}
