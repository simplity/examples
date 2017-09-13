package org.simplity.examples;

import org.simplity.utils.SimpliTestCase;

public class ScdbTest extends SimpliTestCase {

	@Override
	protected void setUp() throws Exception {
		applicationRoot = Thread.currentThread().getContextClassLoader().getResource("comp").getPath();
		testuser = "100";
		testpwd = "pwd";
		skipValidation=true;
		super.setUp();
	}
	public void testcontractFind() {
		servicetest = "contractFindTest";
	}
	public void testcontractFilter() {
		servicetest = "contractFilterTest";
	}
	public void testcontractGet() {
		servicetest = "contractGetTest";
	}
	public void testcontractCreate() {
		servicetest = "contractCreateTest";
	}
	public void testcontractUpdate() {
		servicetest = "contractUpdateTest";
	}
	

}
