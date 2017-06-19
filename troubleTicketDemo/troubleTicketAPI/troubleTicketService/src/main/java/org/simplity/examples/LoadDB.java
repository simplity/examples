package org.simplity.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.simplity.json.JSONArray;
import org.simplity.json.JSONObject;

public class LoadDB {
	final static String filePath = "D:\\Workspace\\simplity\\examples\\troubleTicketDemo\\troubleTicketAPI\\troubleTicketService\\src\\main\\resources\\db\\troubleTicket.json";
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException, IOException {
		JSONArray js = loadArray();

		Connection conn = null;
		conn = DriverManager.getConnection(
				"jdbc:h2:file:D:\\Workspace\\simplity\\examples\\troubleTicketDemo\\troubleTicketAPI\\troubleTicketService\\target\\troubleTicket",
				"sa", "");
		Statement statement = conn.createStatement();

		int counter = 0;
		for (Object rec : js) {
			if (counter >= 100) {
				break;
			}
			String id = null;
			String href = null;
			String correlationId = wrap(((JSONObject) rec).optString("correlationId", null));
			String description = wrap(((JSONObject) rec).optString("description", ""));
			String severity = wrap(((JSONObject) rec).optString("severity", null));
			String type = wrap(((JSONObject) rec).optString("type", ""));
			String creationDate = wrapDate(((JSONObject) rec).optString("creationDate"));
			String targetResolutionDate = wrapDate(((JSONObject) rec).optString("targetResolutionDate"));
			String status = wrap(((JSONObject) rec).optString("status", null));
			String substatus = null;
			String statusChangeReason = wrap(((JSONObject) rec).optString("statusChangeReason", null));
			String statusChangeDate = wrapDate(((JSONObject) rec).optString("statusChangeDate"));
			String resolution = null;
			String sqlQuery = "INSERT INTO PUBLIC.TICKET VALUES (" + id + "," + href + "," + correlationId + "," + description
					+ "," + severity + "," + type + "," + creationDate + "," + targetResolutionDate + "," + status + ","
					+ substatus + "," + statusChangeReason + "," + statusChangeDate + "," + resolution + ")";
			System.out.println(sqlQuery);
			statement.execute(sqlQuery);
			
			counter++;
			// JSONArray relatedParty =
			// ((JSONObject)rec).getJSONArray("relatedParty");
			// JSONArray relatedObject =
			// ((JSONObject)rec).getJSONArray("relatedObject");
			// JSONArray note = ((JSONObject)rec).getJSONArray("note");
		}
		conn.close();
	}

	private static String wrapDate(String stringDate) {
		if(stringDate!=null){	
			SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-DD'T'HH:MM:SSZ");
			//sf.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				Timestamp out = new java.sql.Timestamp(sf.parse(stringDate).getTime());
				return wrap(out.toString()) ;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	private static String wrap(String optString) {
		if(optString!=null){			
			return "'"+optString+"'";
		}
		return null;
	}

	private static JSONArray loadArray() throws IOException {
		File fl = new File(filePath);
		BufferedReader bf = new BufferedReader(new FileReader(fl));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		return new JSONArray(sb.toString());
	}
}
