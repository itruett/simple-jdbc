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

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests {@link QueryRunner}.
 */
public class QueryRunnerTest extends SimpleJdbcTest {
	/**
	 * Tests binding parameters.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testBindParameters() throws SQLException {
		final String expectedParameter = "foo";
		final ResultProcessor<String> testProcessor = new ResultProcessor<String>() {
			@Override
			public QueryResult<String> processResultSet(final ResultSet resultSet) {
				return new QueryResult<String>() {
					@Override
					public String getValue() {
						return null;
					}

					@Override
					public int getRowCount() {
						return 0;
					}
				};
			}
		};

		runner.query("", new Object[] { expectedParameter }, testProcessor);

		Assert.assertEquals(expectedParameter, runner.getBoundParameter(1));
	}

	/**
	 * Tests committing a transaction.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testCommitTransaction() throws SQLException {
		runner.executeTransaction(new Transaction() {
			@Override
			public void execute() throws SQLException {
				runner.update("foo", new Object[0]);
				runner.update("bar", new Object[0]);
			}
		});

		Assert.assertTrue(testDataSource.isAutoCommit());
		Assert.assertTrue(testDataSource.isCommitCalled());
		Assert.assertEquals(true, runner.isConnectionClosed());
	}

	/**
	 * Tests that creating a {@link QueryRunner} with a <code>NULL</code> {@link DataSource} causes
	 * an AssertionError. Assertions must be enabled for this test to pass.
	 */
	@Test(expected = AssertionError.class)
	public void testNullDataSourceAssertion() {
		new QueryRunner(null);
	}

	/**
	 * Tests running a query.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testQuery() throws SQLException {
		final String expectedResult = "foo";
		final ResultProcessor<String> testProcessor = new ResultProcessor<String>() {
			@Override
			public QueryResult<String> processResultSet(final ResultSet resultSet) {
				return new QueryResult<String>() {
					@Override
					public String getValue() {
						return expectedResult;
					}

					@Override
					public int getRowCount() {
						return 1;
					}
				};
			}
		};

		final QueryResult<String> result = runner.query("", new Object[0], testProcessor);

		Assert.assertEquals(expectedResult, result.getValue());
		Assert.assertEquals(true, runner.isConnectionClosed());
	}

	/**
	 * Tests rolling back a transaction.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testRollbackTransaction() throws SQLException {
		final SQLException expectedException = new SQLException();
		final Throwable thrownException = runner.executeTransaction(new Transaction() {
			@Override
			public void execute() throws SQLException {
				throw expectedException;
			}
		});

		Assert.assertSame(expectedException, thrownException);
		Assert.assertTrue(testDataSource.isAutoCommit());
		Assert.assertTrue(testDataSource.isRollbackCalled());
		Assert.assertEquals(true, runner.isConnectionClosed());
	}

	/**
	 * Tests running an update.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testUpdate() throws SQLException {
		runner.update("", new Object[0]);

		Assert.assertTrue(testDataSource.isExecuteUpdateCalled());
		Assert.assertEquals(true, runner.isConnectionClosed());
	}

	/**
	 * Tests that the connection is closed if a SQLException is thrown.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testQueryException() throws SQLException {
		final ResultProcessor<String> testProcessor = new ResultProcessor<String>() {
			@Override
			public QueryResult<String> processResultSet(final ResultSet resultSet)
			        throws SQLException {
				throw new SQLException();
			}
		};

		try {
			runner.query("", new Object[0], testProcessor);
			Assert.fail("Expected SQLException.");
		} catch (SQLException e) {
			// Expected exception.
		}

		Assert.assertEquals(true, runner.isConnectionClosed());
	}

	/**
	 * Tests that the connection is closed if a SQLException is thrown.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testUpdateException() throws SQLException {
		try {
			runner.update("exception", new Object[0]);
			Assert.fail("Expected SQLException.");
		} catch (SQLException e) {
			// Expected exception.
		}

		Assert.assertEquals(true, runner.isConnectionClosed());
	}
}
