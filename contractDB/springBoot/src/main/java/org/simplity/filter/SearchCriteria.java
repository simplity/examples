package org.simplity.filter;

import java.util.ArrayList;
import java.util.List;

import org.simplity.kernel.FilterCondition;
import org.simplity.kernel.comp.ComponentManager;
import org.simplity.kernel.data.MultiRowsSheet;
import org.simplity.kernel.db.DbDriver;
import org.simplity.kernel.dm.Field;
import org.simplity.kernel.dm.Record;
import org.simplity.kernel.value.Value;
import org.simplity.service.ServiceContext;
import org.simplity.tp.ComplexLogicInterface;

public class SearchCriteria implements ComplexLogicInterface {

	@Override
	public Value execute(ServiceContext ctx,DbDriver driver) {

		/*
		 * we have to create where clause with ? and corresponding values[]
		 */
		SqlAndValues parent = getParentFilterSql(ctx, "cschdContractHeaders", "CSCHD_CONTRACT_HEADERS");
		StringBuilder parentSql = parent.sql;
		boolean hasNoFilter = parent.hasNoFilter;
		List<Value> filterValues = parent.values;
		SqlAndValues child1 = getFilterSql(ctx, "cscbsContractBenchs", "CSCBS_CONTRACT_BENCHS");
		SqlAndValues child2 = getFilterSql(ctx, "csclsContractLocations", "CSCLS_CONTRACT_LOCATIONS");
		SqlAndValues child3 = getFilterSql(ctx, "cscsgContractStorages", "CSCSG_CONTRACT_STORAGES");
		SqlAndValues[] children = {child1,child2,child3};
		for(SqlAndValues child : children){
			if(!child.hasNoFilter){
				if(hasNoFilter){
					hasNoFilter = false;
					parentSql.append(" WHERE ");
				}
				else{
					parentSql.append(" AND ");
				}
				parentSql.append("CSCHD_PK IN (").append(child.sql.toString()).append(") ");
				filterValues.addAll(filterValues.size(), child.values);
			}
		}
		
		Value[] values;
		if (hasNoFilter) {
			parentSql.append(" WHERE 1 = 1 ");
			values = new Value[0];
		} else {
			values = filterValues.toArray(new Value[0]);
		}
		Record inputRecord = ComponentManager.getRecordOrNull("cschdContractHeaders");
		MultiRowsSheet outSheet = new MultiRowsSheet(inputRecord.getFields());		
		driver.extractFromSql(parentSql.toString(), values,outSheet,false);
		ctx.putDataSheet("contractHeaders", outSheet);
		return null;
	}

	public SqlAndValues getFilterSql(ServiceContext ctx,String recordName,String tableName){
		String filterSql = "SELECT CONTRACT_CSCHD_FK FROM "+ tableName+" ";
		StringBuilder sql = new StringBuilder(filterSql);
		List<Value> filterValues = new ArrayList<Value>();
		boolean hasNoFilter = true;
		Record inputRecord = ComponentManager.getRecordOrNull(recordName);
		for (Field field : inputRecord.getFields()) {
			String fieldName = field.getName();
			Value value = ctx.getValue(fieldName);
			if (Value.isNull(value) || value.toString().isEmpty()) {
				continue;
			}
			if (hasNoFilter) {
				hasNoFilter = false;
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}
			FilterCondition condition = FilterCondition.Equal;
			sql.append(field.getColumnName()).append(condition.getSql()).append("?");
			filterValues.add(value);						
		}
		return new SqlAndValues(sql,filterValues,hasNoFilter);
	}

	public SqlAndValues getParentFilterSql(ServiceContext ctx,String recordName,String tableName){
		String filterSql = "SELECT * FROM "+ tableName+" ";
		StringBuilder sql = new StringBuilder(filterSql);
		List<Value> filterValues = new ArrayList<Value>();
		boolean hasNoFilter = true;
		Record inputRecord = ComponentManager.getRecordOrNull(recordName);
		for (Field field : inputRecord.getFields()) {
			String fieldName = field.getName();
			Value value = ctx.getValue(fieldName);
			if (Value.isNull(value) || value.toString().isEmpty()) {
				continue;
			}
			if (hasNoFilter) {
				hasNoFilter = false;
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}
			FilterCondition condition = FilterCondition.Equal;
			sql.append(field.getColumnName()).append(condition.getSql()).append("?");
			filterValues.add(value);						
		}
		return new SqlAndValues(sql,filterValues,hasNoFilter);
	}

}

class SqlAndValues {
	final StringBuilder sql;
	final List<Value> values;
	final boolean hasNoFilter;

	SqlAndValues(StringBuilder sql, List<Value> values,boolean hasNoFilter) {
		this.sql = sql;
		this.values = values;
		this.hasNoFilter = hasNoFilter;
	}
}
