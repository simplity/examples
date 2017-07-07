package org.simplity.examples.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.simplity.examples.TTServiceMain;
import org.simplity.json.JSONArray;
import org.simplity.kernel.Application;
import org.simplity.service.JavaAgent;

public class LoadDB {
	final static String filePath = "D:/simplity/github/examples/troubleTicketDemo/troubleTicketAPI/troubleTicketService/src/main/resources/db/troubleTicket.json";

	public static void main(String[] args) throws Exception {
		JSONArray js = loadArray();
		File jarPath = new File(TTServiceMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String folder = jarPath.getParent() + File.separator + "comp" + File.separator;
		Application.bootStrap(folder);

		int counter = 0;
		for (Object rec : js) {
			if (counter >= 100) {
				break;
			}
			JavaAgent.getAgent("100", null).serve("troubleTicketLoad", rec.toString());
			counter++;
		}
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
