package org.support.project.web.common;

import org.junit.Test;
import org.support.project.web.common.InvokeSearch;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.config.HttpMethod;

public class InvokeSearchTest {


	@Test
	public void testGetController() {
		InvokeSearch search = new InvokeSearch();
		search.addTarget("org.support.project.web.control", "Control");
		
		InvokeTarget target = search.getController(HttpMethod.get, "Test/test");
		target.invoke(null);
		
		target = search.getController(HttpMethod.post, "sub.TestSub/testMethod");
		target.invoke(null);
	}

}
