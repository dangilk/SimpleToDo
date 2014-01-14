package com.dan.simpletodo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditItemActivity extends Activity {
	EditText etItem;
	Button btnUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		etItem = (EditText) findViewById(R.id.etItem);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		String itemText = getIntent().getStringExtra("item");
		//initialize the update text --
		etItem.setText(itemText);
		etItem.setSelection(itemText.length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void onUpdate(View v){
		//when updated, pass the new description back to previous activity
		Intent data = new Intent();
		data.putExtra("updated",etItem.getText().toString());
		setResult(RESULT_OK,data);
		finish();
	}
	

}
