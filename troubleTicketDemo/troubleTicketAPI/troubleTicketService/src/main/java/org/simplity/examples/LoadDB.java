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
import java.util.Date;

import org.simplity.json.JSONArray;
import org.simplity.json.JSONObject;

public class LoadDB {
	final static String filePath = "D:\\Workspace\\simplity\\examples\\troubleTicketDemo\\troubleTicketAPI\\troubleTicketService\\src\\main\\resources\\content.json";
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
			if (counter >= 2) {
				break;
			}
			String id = null;
			String href = null;
			String correlationId = wrap(((JSONObject) rec).optString("correlationId", null));
			String description = wrap(((JSONObject) rec).optString("description", ""));
			String severity = wrap(((JSONObject) rec).optString("severity", null));
			String type = wrap(((JSONObject) rec).optString("type", ""));
			Date creationDate = wrapDate(((JSONObject) rec).optDate("creationDate"));
			Date targetResolutionDate = wrapDate(((JSONObject) rec).optDate("targetResolutionDate"));
			String status = wrap(((JSONObject) rec).optString("status", null));
			String substatus = null;
			String statusChangeReason = wrap(((JSONObject) rec).optString("statusChangeReason", null));
			Date statusChangeDate = wrapDate(((JSONObject) rec).optDate("statusChangeDate"));
			String resolution = null;
			String sqlQuery = "INSERT INTO PUBLIC.TICKET VALUES (" + id + "," + href + "," + correlationId + "," + description
					+ "," + severity + "," + type + "," + creationDate + "," + targetResolutionDate + "," + status + ","
					+ substatus + "," + statusChangeReason + "," + statusChangeDate + "," + resolution + ")";
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

	private static Date wrapDate(Date optDate) {
		if(optDate!=null){			
			return new java.sql.Date(optDate.getTime());
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
