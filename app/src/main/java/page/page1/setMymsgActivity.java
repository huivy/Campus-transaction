package page.page1;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;
import static page.page1.R.id.address;
import static page.page1.R.id.phone;
import static page.page1.R.id.qq;
import static page.page1.R.id.subject;

public class setMymsgActivity extends AppCompatActivity {
    private TextView userid;
    private EditText username;
    private EditText usersubject;
    private EditText userphone;
    private EditText userqq;
    private EditText useraddress;
    private Button usersave;
    private Button back;
    protected Intent intent;
    private String post_name=null,post_subject=null,post_phone=null,post_qq=null,post_address=null,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mymsg);

        userid=(TextView)findViewById(R.id.showUser);
        username=(EditText)findViewById(R.id.name);
        usersubject=(EditText)findViewById(subject);
        userphone=(EditText)findViewById(phone);
        userqq=(EditText)findViewById(qq);
        useraddress=(EditText)findViewById(address);
        usersave=(Button)findViewById(R.id.save);
        back=(Button)findViewById(R.id.back);
        id=LoginMainActivity.post_userid;
        userid.setText(id);
        if(id.equals("")||id==null){
            Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            intent = new Intent(setMymsgActivity.this,MyselfActivity.class);
            startActivity(intent);
        }
      //账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq,地址address
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();

        usersave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq,地址address
                post_name=username.getText().toString();
                post_subject=usersubject.getText().toString();
                post_phone=userphone.getText().toString();
                post_qq=userqq.getText().toString();
                post_address=useraddress.getText().toString();
                ContentValues values=new ContentValues();
                if(!post_name.equals("")) {
                    values.put("name", post_name);
                }
                if(!post_subject.equals("")) {
                    values.put("subject", post_subject);
                }
                if(!post_phone.equals("")) {
                    values.put("phone", post_phone);
                }
                if(!post_qq.equals("")) {
                    values.put("qq", post_qq);
                }
                if(!post_address.equals("")) {
                    values.put("address", post_address);
                }
                saveValues(values);
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                intent = new Intent(setMymsgActivity.this,userMsgActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(setMymsgActivity.this,userMsgActivity.class);
                startActivity(intent);
            }
        });
    }
    private void saveValues(ContentValues values){
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        db.update("users",values,"userId=?",new String[] {id});
        db.close();
    }
}

