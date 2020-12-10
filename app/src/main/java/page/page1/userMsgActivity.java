package page.page1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class userMsgActivity extends AppCompatActivity {
    private TextView userid;
    private TextView username;
    private TextView usersubject;
    private TextView userphone;
    private TextView userqq;
    private TextView useraddress;
    private Button userchange;
    private Button back;
    protected Intent intent;
    private String id,name,subject,phone,qq,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_msg);

        userid = (TextView) findViewById(R.id.showUser);
        username = (TextView) findViewById(R.id.name);
        usersubject = (TextView) findViewById(R.id.subject);
        userphone = (TextView) findViewById(R.id.phone);
        userqq = (TextView) findViewById(R.id.qq);
        useraddress = (TextView) findViewById(R.id.address);
        userchange = (Button) findViewById(R.id.changemsg);
        back = (Button) findViewById(R.id.back);
        id = LoginMainActivity.post_userid;
        userid.setText(id);
        if (id.equals("") || id == null) {
            Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            intent = new Intent(userMsgActivity.this, LoginMainActivity.class);
            startActivity(intent);
        } else {//账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq,地址address
            DatabaseHelper dbhelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            try {
                String sql = "SELECT * FROM users WHERE userId=?";
                Cursor cursor = db.rawQuery(sql, new String[]{id});
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "用户不存在！", Toast.LENGTH_SHORT).show();
                } else {
                    if (cursor.moveToFirst()) {
                        name = cursor.getString(cursor.getColumnIndex("name"));
                        subject = cursor.getString(cursor.getColumnIndex("subject"));
                        phone = cursor.getString(cursor.getColumnIndex("phone"));
                        qq = cursor.getString(cursor.getColumnIndex("qq"));
                        address = cursor.getString(cursor.getColumnIndex("address"));
                    }
                    Log.i("123","1233333");
                    username.setText(name);
                    userphone.setText(phone);
                    usersubject.setText(subject);
                    userqq.setText(qq);
                    useraddress.setText(address);
                    Log.i("123","12344444");

                }
                cursor.close();
                db.close();
            } catch (SQLiteException e) {
                Toast.makeText(getApplicationContext(), "无法显示个人信息", Toast.LENGTH_SHORT).show();
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(userMsgActivity.this,MyselfActivity.class);
                startActivity(intent);
            }
        });

        userchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(userMsgActivity.this,setMymsgActivity.class);
                startActivity(intent);
            }
        });
    }
}
