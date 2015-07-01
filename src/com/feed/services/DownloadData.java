package com.feed.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class DownloadData extends AsyncTask<String, Void, String> {

	JSONObject jsonObject;
	Callback c;
	boolean connection;
	public final static String error100 ="No internet connection !";

	public DownloadData(Callback c, boolean connection) {
		this.c = c;
		this.connection = connection;
	}

	@Override
	protected String doInBackground(String... urls) {
		if (connection == true) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(urls[0]);
			HttpResponse response;
			StringBuilder sb = new StringBuilder();
			HttpEntity entity = null;
			String line = null;

			try {

				response = httpclient.execute(httpget);
				entity = response.getEntity();

				if (entity != null) {

					InputStream instream = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream));

					try {
						while ((line = reader.readLine()) != null)
							sb.append(line + "\n");
					} catch (IOException e) {
						e.printStackTrace();

					} finally {
						try {
							instream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			}
			return sb.toString();
		}
		return error100;
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			if (connection == false)
				c.onError(new Exception(error100));
			else {
				jsonObject = new JSONObject(result);
				c.finished(result);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject getJSONObject() {
		return this.jsonObject;
	}
}
