package com.example.lpwd;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lpwd.database.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.Inflater;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddDialogFragment.onItemClickListener{
    ImageButton btn_add = null;
    ArrayAdapter<String> adapter;
    List<String> value = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        findViews();
        showAll();
    }
    /**
     * 显示数据
     */
    public void showAll() {

        MyDataBaseHelper MyDataBaseHelper = new MyDataBaseHelper(this);
        final SQLiteDatabase sqliteDatabase = MyDataBaseHelper.getWritableDatabase();
        final ListView listView = (ListView) findViewById(R.id.list);
        final List<String> dbValue = new ArrayList<>();
        //创建游标对象
        Cursor cursor = sqliteDatabase.query("user", new String[]{"id","name", "pwd"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            value.add("账号：" + name +" "+ "密码：" + pwd);
            dbValue.add(name);
        }
        // 关闭游标，释放资源
        cursor.close();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, value);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String delVal = dbValue.get(position);
                sqliteDatabase.delete("user","name = '"+delVal+"'",null);
                Intent intent = new Intent(MainActivity.this,MainActivity.class)
                        .setFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        });
    }
    /**
     * 添加按钮点击弹出AddDialog
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                AddDialogFragment addDialogFragment = new AddDialogFragment();
                addDialogFragment.setOnItemClickListener(MainActivity.this);
                addDialogFragment.show(getSupportFragmentManager(),"dialog");
                break;
            default:
                System.out.println("err");
                break;
        }
    }
    /**
     * 初始化组件
     */
    public void findViews(){
        btn_add = this.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
    }
    /**
     * 创建数据库
     */
    public void initDatabase(){
        MyDataBaseHelper MyDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqliteDatabase = MyDataBaseHelper.getWritableDatabase();
        sqliteDatabase.close();
    }

    @Override
    public void onItemClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                Toast.makeText(this, "我知道了", Toast.LENGTH_SHORT).show();
                adapter.clear();
                showAll();
                break;
        }

    }
}
