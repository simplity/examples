package org.simplity.test;

import org.simplity.utils.SimpliTestCase;

public class ContractTest extends SimpliTestCase{
	@Override
	protected void setUp() throws Exception {
		applicationRoot=Thread.currentThread().getContextClassLoader().getResource("comp").getPath();
		testuser="100";
		testpwd="pwd";		
		skipValidation=true;
		super.setUp();
	}
	
	public void testContract(){
		servicetest="filterTest";
	}
}
