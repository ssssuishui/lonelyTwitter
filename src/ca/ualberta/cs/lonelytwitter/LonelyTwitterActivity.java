package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity { //Activity is a class by android, for google to implement

	private static final String FILENAME = "file.sav";//static-class specific, final-cannot change, 
														//capitalize things not changing
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayAdapter<String> adapter;
	private ArrayList <String> tweets;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); //instruct android on what will be seen 'R' insides resources

		bodyText = (EditText) findViewById(R.id.body); //body from resources, object EditText, saved
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); //find the object

		saveButton.setOnClickListener(new View.OnClickListener() { //new... anonymous class below

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				saveInFile(text, new Date(System.currentTimeMillis()));
				tweets.add(text);
				adapter.notifyDataSetChanged();//tell adapter we changed things when tweet

			}
		});
	}

	@Override
	protected void onStart() {

		ArrayList <User> array = new ArrayList<User>();
		
		// TODO Auto-generated method stub
		super.onStart();
		tweets = loadFromFile();
		if(tweets==null){
			tweets = new ArrayList<String>();
		}
		adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}

	private ArrayList<String>  loadFromFile() {
		Gson gson = new Gson();
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			// Taken from http://google-gson.googlecode.com/svn/tags/gson-1.7.2/docs/javadocs/com/google/gson/Gson.html
			Type typeofT = new TypeToken<ArrayList<String>>(){}.getType();
			gson.fromJson(in, typeofT);
			fis.close();
			//only read the file, not doing anything

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweets;
	}
	
	private void saveInFile(String text, Date date) {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0); //Context.MODE_APPEND for append, 0 for override
			OutputStreamWriter osw = new OutputStreamWriter(fos);//wrapping the output stream
			gson.toJson(tweets, osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}