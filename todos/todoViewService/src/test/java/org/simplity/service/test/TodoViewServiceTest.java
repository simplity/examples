package org.simplity.service.test;

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
		servicetest = "todoviewtestrun";	
	}

}
