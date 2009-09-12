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

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests {@link SingleBooleanProcessor}.
 */
public class SingleBooleanProcessorTest extends SimpleJdbcTest {
	/**
	 * Tests processing an empty result set.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testProcessEmptyResultSet() throws SQLException {
		final QueryResult<Boolean> result = runner.query("", new Object[0],
		        new SingleBooleanProcessor("value"));

		Assert.assertNotNull(result);
		Assert.assertNull(result.getValue());
		Assert.assertEquals(0, result.getRowCount());
	}

	/**
	 * Tests processing a result set.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testProcessResultSet() throws SQLException {
		final Boolean expectedValue = Boolean.TRUE;
		testDataSource.setResultValue(0, "value", expectedValue);

		final QueryResult<Boolean> result = runner.query("", new Object[0],
		        new SingleBooleanProcessor("value"));

		Assert.assertNotNull(result);
		Assert.assertEquals(expectedValue, result.getValue());
		Assert.assertEquals(1, result.getRowCount());
	}
}
