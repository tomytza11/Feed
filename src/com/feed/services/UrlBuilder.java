package com.feed.services;

public class UrlBuilder {

	public enum ServiceMethod {
		GetAllMovies("imdb.json"), // used in main activity
		GetMovieDetails("","t","y"); // used in details activity
				
		private String methodEndPoint;
		private String[] paramNames;

		private ServiceMethod(String methodEndPoint, String... paramNames) {
			this.methodEndPoint = methodEndPoint;
			this.paramNames = paramNames;
		}

		/**
		 * Get complete url based on partial url and it's parameters
		 * @param urlOfResource
		 * @param paramValues - parameters of url
		 * @return
		 */
		public String getUrl(String urlOfResource, Object... paramValues) {
			String paramsString = "?";

			for (int i = 0; i < paramValues.length; i++)
				if (paramValues[i] != null){
					paramValues[i] = ((String)paramValues[i]).replace(" ", "%20");
					paramsString += paramNames[i] + "=" + paramValues[i];
					if(i != paramValues.length-1)
						paramsString +="&";
					}
			   return urlOfResource + methodEndPoint + paramsString;
		}
	}
	
}

