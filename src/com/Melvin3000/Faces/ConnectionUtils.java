package com.Melvin3000.Faces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ConnectionUtils {
	
	/**
	 * Connects to url and returns ArrayList of lines
	 * @param urlString url to connect to
	 * @return returns html request if succsessful else null
	 */
	public static List<String> get(String urlString) {

		List<String> lines = new ArrayList<String>();
		BufferedReader in = null;

		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return lines;
	}

}
