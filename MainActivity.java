package nz.com.example.ch11_player;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore.MediaColumns;

public class MainActivity extends Activity {
	Uri uri;
	TextView txvName, txvUri;
	boolean isVideo = false; // Checking the record is the video or not

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); // Setting the screen is not rotate with the phone.
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Setting the screen presents on the portrait.
		
		txvName = (TextView)findViewById(R.id.txvName);
		txvUri = (TextView)findViewById(R.id.txvUri);
		
		uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome);
		
		txvName.setText("welcome.mp3");
		txvUri.setText("The music in the Player's programe: " + uri.toString());
	}
	
	public void onPick(View v) {
		Intent it = new Intent(Intent.ACTION_GET_CONTENT);
		
		if (v.getId() == R.id.btnPickAudio) {
			it.setType("audio/*");
			startActivityForResult(it, 100);
		} else {
			it.setType("video/*");
			startActivityForResult(it, 101);
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
			isVideo = (requestCode == 101);
			
			uri = convertUri(data.getData());
			
			txvName.setText((isVideo? "Video:" : "Audio: ") + uri.getLastPathSegment());
			txvUri.setText("The Path of the file: " + uri.getPath());
		}
	}

	private Uri convertUri(Uri data) {
		// TODO Auto-generated method stub
		if (uri.toString().substring(0, 7).equals("content")) {
			
			String[] colName = {MediaColumns.DATA}; // Declaring the columns we want to query.
			Cursor cursor = getContentResolver().query(uri, colName, null, null, null); // Using the Uri doing the query.
			uri = Uri.parse("file://" + cursor.getString(0));
		}
		return uri;
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
