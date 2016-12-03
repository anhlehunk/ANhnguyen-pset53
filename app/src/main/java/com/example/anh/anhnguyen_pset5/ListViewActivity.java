package com.example.anh.anhnguyen_pset5;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class ListViewActivity extends AppCompatActivity {
    final String[] from = new String[] {TodoManager.CONTENT};
    final int[] to = new int[] {R.id.listItem};
    private String nameList;
    private String nameOfList;
    private TodoManager db;
    private Cursor getCursor;
    private ListView listOfLists;
    private SimpleCursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = TodoManager.getInstance(this);
        placeList();
    }

    public void addNewList(View view){
        EditText newList = (EditText) findViewById(R.id.listEdit);
        nameList = newList.getText().toString();
        newList.setText("");

        // Toast and return error if edit text is blank
        if(nameList.length() == 0){
            Toast blank = Toast.makeText(ListViewActivity.this, "Insert the list title", Toast.LENGTH_SHORT);
            blank.show();
        }
        else{
            db.makeList(nameList);
            getCursor.requery();
            cursorAdapter.notifyDataSetChanged();
            Toast added =   makeText(ListViewActivity.this, nameList + " is succesfully added", Toast.LENGTH_SHORT);
            added.show();
        }
    }

    private void placeList() {
        getCursor = db.getList();
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, getCursor, from, to, 0);
        listOfLists = (ListView) findViewById(R.id.listofLists);
        listOfLists.setAdapter(cursorAdapter);

        // open list to view tasks
        listOfLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor rowData = db.getListRow(l);
                if (rowData.moveToFirst()) {
                    nameOfList = rowData.getString(1);
                }
                rowData.close();

                Intent viewContent= new Intent(ListViewActivity.this, ItemViewActivity.class);
                viewContent.putExtra("item", l);
                viewContent.putExtra("title", nameOfList);
                startActivity(viewContent);
            }
        });

        // long click to delete
        listOfLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // delete list
                db.list_delete(l);
                db.delete_all(l);
                getCursor.requery();
                cursorAdapter.notifyDataSetChanged();

                // Toast
                Toast deleted = makeText(ListViewActivity.this, "List is deleted", Toast.LENGTH_SHORT);
                deleted.show();
                return true;
            }});}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
