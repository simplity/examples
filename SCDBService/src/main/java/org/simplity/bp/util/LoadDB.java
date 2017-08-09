package org.simplity.bp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.simplity.bp.App;
import org.simplity.json.JSONArray;
import org.simplity.kernel.Application;
import org.simplity.service.JavaAgent;

public class LoadDB {

	public static void main(String[] args) throws Exception {
		
		File jarPath = new File(App.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String folder = jarPath.getParent() + File.separator + "comp" + File.separator;
		Application.bootStrap(folder);

		JSONArray js = loadArray(jarPath + File.separator + "db/sampledata.json");
		int counter = 0;
		for (Object rec : js) {
			if (counter >= 100) {
				break;
			}
			JavaAgent.getAgent("100", null).serve("contractCreate", rec.toString());
			counter++;
		}
	}

	private static JSONArray loadArray(String filePath) throws IOException {
		File fl = new File(filePath);
		BufferedReader bf = new BufferedReader(new FileReader(fl));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		bf.close();
		return new JSONArray(sb.toString());
	}
}
