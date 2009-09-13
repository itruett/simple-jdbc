/*
 *	Copyright 2009 Isaac Truett.
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.googlecode.simplejdbc;

import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests {@link ListProcessor}.
 */
public class ListProcessorTest extends SimpleJdbcTest {
	/**
	 * Tests processing a result set.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testProcessResults() throws SQLException {
		final String firstExpectedValue = "foo";
		final String secondExpectedValue = "and";
		final String thirdExpectedValue = "bar";

		testDataSource.setResultValue(0, "value", firstExpectedValue);
		testDataSource.setResultValue(1, "value", secondExpectedValue);
		testDataSource.setResultValue(2, "value", thirdExpectedValue);

		final ListProcessor<String> testProcessor = new StringProcessor("value").getListProcessor();

		final QueryResult<List<String>> results = runner.query("", new Object[0], testProcessor);

		Assert.assertNotNull(results);

		final List<String> list = results.getValue();

		Assert.assertNotNull(list);
		Assert.assertEquals(3, list.size());
		Assert.assertEquals(firstExpectedValue, list.get(0));
		Assert.assertEquals(secondExpectedValue, list.get(1));
		Assert.assertEquals(thirdExpectedValue, list.get(2));
	}
}
