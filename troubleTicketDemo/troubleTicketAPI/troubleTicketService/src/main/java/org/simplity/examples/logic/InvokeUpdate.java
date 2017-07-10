package org.simplity.examples.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.simplity.json.JSONObject;
import org.simplity.kernel.comp.ComponentManager;
import org.simplity.kernel.data.DataSheet;
import org.simplity.kernel.data.MultiRowsSheet;
import org.simplity.kernel.data.OutputData;
import org.simplity.kernel.value.Value;
import org.simplity.service.JavaAgent;
import org.simplity.service.PayloadType;
import org.simplity.service.ServiceAgent;
import org.simplity.service.ServiceContext;
import org.simplity.tp.LogicInterface;

public class InvokeUpdate implements LogicInterface{

	@Override
	public Value execute(ServiceContext ctx) {
		JSONObject payload = new JSONObject();
		for(Entry<String, Value> field:ctx.getAllFields()){
			payload.put(field.getKey(), field.getValue());
		}
		for(Entry<String, DataSheet> sheet:ctx.getAllSheets()){
			payload.put(sheet.getKey(), toJson(sheet.getValue()));
		}
		JavaAgent.getAgent("100", null).serve("troubleTicketUpdate", payload.toString());
		return null;
	}

	private Collection<?> toJson(DataSheet ds) {
		List<Map<String,String>> collection = new ArrayList<Map<String,String>>();
		String[] colNames = ds.getColumnNames();
		for(Value[] row:ds.getAllRows()){
			Map<String,String> eachRow = new HashMap<String,String>();
			for(int i = 0 ; i<row.length ; i++){
				eachRow.put(colNames[i], row[i].toText());
			}
			collection.add(eachRow);
		};
		return collection;
	}

	
}
