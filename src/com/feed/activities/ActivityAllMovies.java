package com.feed.activities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.feed.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.feed.adapters.MovieAdapter;
import com.feed.entities.Movie;
import com.feed.services.Callback;
import com.feed.services.DownloadData;
import com.feed.services.ServiceManager;
import com.feed.services.UrlBuilder.ServiceMethod;
import com.feed.utils.FeedConstants;
import com.feed.views.CustomTextViewTitlesApp;

/**
 * Main Activity that holds the list of imdb movies. Information about these
 * movies is received in json format.
 * 
 * @author oana
 * 
 */
public class ActivityAllMovies extends ActionBarActivity {
	private Activity activity;
	private MovieAdapter movieAdapter;
	private ArrayList<Movie> moviesList;
	private ListView lwMovies;
	private CustomTextViewTitlesApp txtErrorNetwork;
	private ServiceManager serviceAllMovies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_all_movies);

		serviceAllMovies = new ServiceManager(this);
		moviesList = new ArrayList<Movie>();
		activity = this;

		setUI();
		getAllMovies();
		setActions();
	}

	private void setUI() {
		lwMovies = (ListView) findViewById(R.id.lw_movies);
		txtErrorNetwork = (CustomTextViewTitlesApp) findViewById(R.id.txt_error_network);
	}

	/**
	 * Call service to get a json feed with all imdb movies. From this feed will
	 * be extracted information like title and year of movie for being used to
	 * get details about it later.
	 */
	private void getAllMovies() {
		Object[] paramAllMovies = {};

		serviceAllMovies.call(ServiceMethod.GetAllMovies,
				FeedConstants.SERVICE_URL_ALL_MOVIES, paramAllMovies,
				new Callback() {

					public void onError(Exception e) {
						// user should be alerted that there is no network
						// connection and data can't be reached
						String errorMessage = e.getMessage();
						if (errorMessage.equals(DownloadData.error100)) {
							txtErrorNetwork.setVisibility(View.VISIBLE);
							txtErrorNetwork.setText(errorMessage);
						}
					}

					public <T> void finished(T data) {
						// get jsonObject response
						JSONObject jsonObject = serviceAllMovies.getResult();
						Iterator<String> iter = jsonObject.keys();
						JSONArray iterNames = jsonObject.names();

						int counter = 0;
						while (iter.hasNext()) {
							String key = iter.next();

							try {
								JSONObject value = (JSONObject) jsonObject
										.get(key);
								// extract movie title and movie year and create
								// new object that will be added in moviesList
								Movie movie = new Movie();
								movie.setTitle(value
										.optString(FeedConstants.TITLE
												.toLowerCase()));
								movie.setYear(String.valueOf(iterNames
										.getInt(counter++)));
								moviesList.add(movie);
							} catch (JSONException e) {
								Toast.makeText(activity,
										"Something went wrong",
										Toast.LENGTH_LONG).show();
							}
						}

						movieAdapter = new MovieAdapter(activity, 0, moviesList);
						lwMovies.setAdapter(movieAdapter);

					}
				});

	}

	/**
	 * Set action on each list item. New Activity with details about the
	 * corresponding movie will be opened. Movie title and movie year will be
	 * passed through intent.
	 */
	private void setActions() {
		lwMovies.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				ArrayList<Movie> moviesList = ((MovieAdapter) lwMovies
						.getAdapter()).getMoviesList();
				Intent intent = new Intent(activity, ActivityMovieDetails.class);
				intent.putExtra(FeedConstants.TITLE, moviesList.get(pos)
						.getTitle());
				intent.putExtra(FeedConstants.YEAR, moviesList.get(pos)
						.getYear());
				startActivity(intent);
			}
		});
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
