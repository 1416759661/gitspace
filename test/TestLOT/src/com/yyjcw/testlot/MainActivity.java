package com.yyjcw.testlot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
        	case R.id.action_settings:
	            Intent intent1 = new Intent(MainActivity.this,SettingActivity.class);
	            startActivity(intent1);
	            return true;
        	case R.id.action_quit:
        		finish();
        		return true;
        	case R.id.action_chatview:
        		Intent intent2 = new Intent(MainActivity.this,ChatViewActivity.class);
	            startActivity(intent2);
	            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
