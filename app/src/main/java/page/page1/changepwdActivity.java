package page.page1;

import android.content.ContentValues;
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

public class changepwdActivity extends AppCompatActivity {
    private TextView showuser;
    private EditText oldpwd;
    private EditText newpwd;
    private EditText newpwd1;
    private Button change;
    private Button back;
    private String oldpass,newpass,newpass1,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);

        showuser=(TextView)findViewById(R.id.showuser);
        oldpwd=(EditText)findViewById(R.id.oldpwd);
        newpwd=(EditText)findViewById(R.id.newpwd);
        newpwd1=(EditText)findViewById(R.id.newpwd1);
        change=(Button)findViewById(R.id.change);
        back=(Button)findViewById(R.id.back);
        user=LoginMainActivity.post_userid;
        showuser.setText(user);
        if(user.equals("")||user==null){
            Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(changepwdActivity.this,LoginMainActivity.class);
            startActivity(intent);
        }
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("用户数据",user);
                oldpass=oldpwd.getText().toString();
                newpass=newpwd.getText().toString();
                newpass1=newpwd1.getText().toString();

                boolean flag=true;
                if(oldpass.equals("")||oldpass==null){
                    Toast.makeText(getApplicationContext(), "请输入旧密码！", Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if(newpass.equals("")||newpass==null){
                    Toast.makeText(getApplicationContext(), "请输入新密码！", Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if (!newpass.equals(newpass1)){
                    Toast.makeText(getApplicationContext(), "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if(flag){
                    checkpass(oldpass,newpass);
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(changepwdActivity.this,MyselfActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkpass(String oldpass,String newpass){
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        try{
            String sql="SELECT * FROM users WHERE userId=? and passWord=?";
            Cursor cursor=db.rawQuery(sql,new String[]{user,oldpass});
            if(cursor.getCount()==0){
                Toast.makeText(getApplicationContext(), "用户旧密码错误！", Toast.LENGTH_SHORT).show();
            }
            else{
                ContentValues values=new ContentValues();
                values.put("passWord",newpass);
                db.update("users",values,"userId=?",new String[] {user});
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(changepwdActivity.this,MyselfActivity.class);
                startActivity(intent);
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
        }
    }
}
