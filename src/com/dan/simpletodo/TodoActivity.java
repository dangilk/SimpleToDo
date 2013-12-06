package com.dan.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int EDIT_REQUEST = 1;
	int editingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    public void addTodoItem(View v){
    	
    	EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
    	String item = etNewItem.getText().toString();
    	if(item.length()==0){
    		return;
    	}
    	itemsAdapter.add(item);
    	etNewItem.setText("");
    	saveItems();
    }
    
    private void setupListViewListener(){
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
    		@Override
    		public boolean onItemLongClick(AdapterView<?> aView,View item, int pos, long id){
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			saveItems();
    			return true;
    		}
    	});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> aView, View item, int pos,
					long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("item", items.get(pos));
				editingItem = pos;
				startActivityForResult(i,EDIT_REQUEST);
			}
    	});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above
      if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST) {
         // Extract name value from result extras
         String updated = data.getExtras().getString("updated");
         if(updated.length()==0){
        	 return;
         }
         items.set(editingItem, updated);
         itemsAdapter.notifyDataSetInvalidated();
         saveItems();
      }
    } 
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}catch(IOException e){
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    private void saveItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		FileUtils.writeLines(todoFile, items);
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
