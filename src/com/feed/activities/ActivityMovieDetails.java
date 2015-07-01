package com.feed.activities;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feed.R;
import com.feed.services.Callback;
import com.feed.services.ServiceManager;
import com.feed.services.UrlBuilder.ServiceMethod;
import com.feed.utils.FeedConstants;
import com.feed.views.CustomTextViewDetail;

/**
 * Activity that holds details about a movie from imdb movies list.
 * 
 * @author oana
 * 
 */
public class ActivityMovieDetails extends Activity {
	private ServiceManager serviceMovieDetails;
	private CustomTextViewDetail txtTitle;
	private CustomTextViewDetail txtYear;
	private CustomTextViewDetail txtRated;
	private CustomTextViewDetail txtType;
	private CustomTextViewDetail txtWriter;
	private CustomTextViewDetail txtReleased;
	private CustomTextViewDetail txtRuntime;
	private CustomTextViewDetail txtAwards;
	private CustomTextViewDetail txtMetascore;
	private CustomTextViewDetail txtImdbRating;
	private CustomTextViewDetail txtImdbVotes;
	private CustomTextViewDetail txtPlot;
	private CustomTextViewDetail txtGenre;
	private CustomTextViewDetail txtLanguage;
	private CustomTextViewDetail txtActors;
	private CustomTextViewDetail txtCountry;
	private ImageView iwIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_movie_details);

		serviceMovieDetails = new ServiceManager(this);

		setUI();
		getDetails();
	}

	public void setUI() {
		txtTitle = (CustomTextViewDetail) findViewById(R.id.txt_title);
		txtLanguage = (CustomTextViewDetail) findViewById(R.id.txt_language);
		txtActors = (CustomTextViewDetail) findViewById(R.id.txt_actors);
		txtYear = (CustomTextViewDetail) findViewById(R.id.txt_year);
		txtRated = (CustomTextViewDetail) findViewById(R.id.txt_rated);
		txtType = (CustomTextViewDetail) findViewById(R.id.txt_type);
		txtWriter = (CustomTextViewDetail) findViewById(R.id.txt_writer);
		txtImdbRating = (CustomTextViewDetail) findViewById(R.id.txt_imdb_rating);
		txtImdbVotes = (CustomTextViewDetail) findViewById(R.id.txt_imdb_votes);
		txtPlot = (CustomTextViewDetail) findViewById(R.id.txt_plot);
		txtMetascore = (CustomTextViewDetail) findViewById(R.id.txt_metascore);
		txtGenre = (CustomTextViewDetail) findViewById(R.id.txt_genre);
		txtAwards = (CustomTextViewDetail) findViewById(R.id.txt_awards);
		txtRuntime = (CustomTextViewDetail) findViewById(R.id.txt_runtime);
		txtCountry = (CustomTextViewDetail) findViewById(R.id.txt_country);
		iwIcon = (ImageView) findViewById(R.id.iw_icon);
	}

	/**
	 * Get details about movie based on it's title and year. Details will be
	 * returned in json format.
	 */
	public void getDetails() {
		Object[] param = new Object[10];
		param[0] = getIntent().getStringExtra(FeedConstants.TITLE);
		param[1] = getIntent().getStringExtra(FeedConstants.YEAR);
		serviceMovieDetails.call(ServiceMethod.GetMovieDetails,
				FeedConstants.SERVICE_URL_MOVIE_DETAILS, param, new Callback() {

					public void onError(Exception e) {
						Toast.makeText(getApplication(),
								"Something went wrong", Toast.LENGTH_LONG)
								.show();
					}

					public <T> void finished(T data) {
						JSONObject jsonObject = serviceMovieDetails.getResult();

						// set specific details in customViews
						txtTitle.setText(jsonObject
								.optString(FeedConstants.TITLE));
						txtLanguage.setText(jsonObject
								.optString(FeedConstants.LANGUAGE));
						txtActors.setText(jsonObject
								.optString(FeedConstants.ACTORS));
						txtYear.setText(jsonObject
								.optString(FeedConstants.YEAR));
						txtRated.setText(jsonObject
								.optString(FeedConstants.RATED));
						txtType.setText(jsonObject
								.optString(FeedConstants.TYPE));
						txtWriter.setText(jsonObject
								.optString(FeedConstants.WRITER));
						txtImdbRating.setText(jsonObject
								.optString(FeedConstants.IMDB_RATING));
						txtImdbVotes.setText(jsonObject
								.optString(FeedConstants.IMDB_SCORE));
						txtPlot.setText(jsonObject
								.optString(FeedConstants.PLOT));
						txtAwards.setText(jsonObject
								.optString(FeedConstants.AWARDS));
						txtMetascore.setText(jsonObject
								.optString(FeedConstants.METASCORE));
						txtGenre.setText(jsonObject
								.optString(FeedConstants.GENRE));
						txtRuntime.setText(jsonObject
								.optString(FeedConstants.RUNTIME));
						txtCountry.setText(jsonObject
								.optString(FeedConstants.COUNTRY));

						// download and display the movie poster in ImageView
						Bitmap bitmap = downloadImage(jsonObject
								.optString(FeedConstants.POSTER));
						iwIcon.setImageBitmap(bitmap);

					}
				});
	}

	/**
	 * Download movie poster from url.
	 * 
	 * @param url
	 *            - url of movie poster
	 * @return
	 */
	public static Bitmap downloadImage(String url) {

		AsyncTask<String, Void, Bitmap> dl = new DownloadImageTask()
				.execute(url);
		try {
			return dl.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;

	}
}

/**
 * Download movie poster from url in Bitmap object
 * 
 * @author oana
 * 
 */
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	Bitmap my_bitmap;

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		my_bitmap = result;
	}
}
