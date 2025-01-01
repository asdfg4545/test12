package com.example.final_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHelper helper;
    private ListView listView;
    private ImageButton Add;
    private List<costList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        helper = new DBHelper(MainActivity.this);
        listView = findViewById(R.id.list_view);
        Add = findViewById(R.id.add);
    }

    private void initData() {
        list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("account", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            costList clist = new costList();
            clist.set_id(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
            clist.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("Title")));
            clist.setDate(cursor.getString(cursor.getColumnIndexOrThrow("Date")));
            clist.setMoney(cursor.getString(cursor.getColumnIndexOrThrow("Money")));
            list.add(clist);
        }
        listView.setAdapter(new ListAdapter(this, list));
        cursor.close();
        db.close();
    }

    public void addAccount(View view) {
        Intent intent = new Intent(MainActivity.this, new_cost.class);
        startActivityForResult(intent, 1);
    }

    public void deleteAccount(View view) {
        View parent = (View) view.getParent(); // 找到 list_item 的父視圖
        TextView tv_title = parent.findViewById(R.id.tv_title); // 獲取隱藏的 tv_title
        String title = tv_title.getText().toString(); // 獲取該條目的 title

        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsDeleted = db.delete("account", "Title = ?", new String[]{title});
        db.close();

        if (rowsDeleted > 0) {
            Toast.makeText(this, "刪除成功", Toast.LENGTH_SHORT).show();
            initData(); // 刷新數據
        } else {
            Toast.makeText(this, "刪除失敗", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1)
            this.initData();
    }
}

