package org.simplity.service.test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simplity.json.JSONObject;
import org.simplity.service.JavaAgent;
import org.simplity.service.PayloadType;
import org.simplity.service.ServiceData;
import org.simplity.utils.SimpliTestCase;

public class TodoViewServiceTest extends SimpliTestCase {

	@Override
	protected void setUp() throws Exception {
		applicationRoot=Thread.currentThread().getContextClassLoader().getResource("comp").getPath();
		testuser="100";
		testpwd="pwd";		
		skipValidation=true;
		super.setUp();
	}

	
	public void testTodoViewService() {
		ServiceData outData = JavaAgent.getAgent("100", null).serve("viewTodos", null, PayloadType.JSON);
		JSONObject obj = new JSONObject(outData.getPayLoad());
		assertNotNull(obj.get("todosDBTable"));
		System.out.println(obj.toString());
	}

}
