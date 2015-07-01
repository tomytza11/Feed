package com.feed.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feed.R;
import com.feed.entities.Movie;
import com.feed.views.CustomTextViewDetail;

public class MovieAdapter extends ArrayAdapter<Movie> implements Filterable {

	public MovieAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	private ArrayList<Movie> movieList;
	private Activity activity;

	public MovieAdapter(Activity activity, int textViewResourceId,
			ArrayList<Movie> movieList) {
		super(activity, textViewResourceId, movieList);
		this.movieList = movieList;
		this.activity = activity;
	}

	/**
	 * ViewHolder for list item that holds title and year in customTextViews
	 * objects
	 * 
	 * @author oana
	 * 
	 */
	public static class ViewHolder {
		public CustomTextViewDetail txtTitle;
		public CustomTextViewDetail txtYear;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;

		// check for reusing the view
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.movie_item, null);
			holder = new ViewHolder();
			holder.txtTitle = (CustomTextViewDetail) v
					.findViewById(R.id.txt_title);
			holder.txtYear = (CustomTextViewDetail) v
					.findViewById(R.id.txt_year);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		final Movie movieInList = getItem(position);
		if (movieInList != null) {
			// set movie information in list
			holder.txtTitle.setText(movieInList.getTitle());
			holder.txtYear.setText(movieInList.getYear());
		}
		return v;
	}

	/**
	 * Get moviesList
	 * @return
	 */
	public ArrayList<Movie> getMoviesList() {
		return this.movieList;
	}
}