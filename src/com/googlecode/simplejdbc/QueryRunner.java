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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * <p>
 * Runs SQL queries against a {@link DataSource} while guaranteeing that the connection will be
 * closed afterwards. A {@link ResultProcessor} is used to process results of a query.
 * </p>
 * <p>
 * A group of queries may be run within a {@link Transaction} to perform atomic operations. The
 * transaction will be committed automatically unless an uncaught exception occurs, in which case a
 * rollback will be performed automatically.
 * </p>
 */
public class QueryRunner {
	private final DataSource dataSource;
	private Connection connection = null;

	/**
	 * Creates a new QueryRunner for the specified {@link DataSource}.
	 * 
	 * @param dataSource
	 */
	public QueryRunner(final DataSource dataSource) {
		assert dataSource != null : "dataSource cannot be null";
		this.dataSource = dataSource;
	}

	/**
	 * Executes the specified {@link Transaction}. The transaction will be committed automatically
	 * unless an uncaught exception occurs, in which case a rollback will be performed
	 * automatically. The uncaught exception, if any, will be returned.
	 * 
	 * @param transaction the {@link Transaction} to be executed
	 * @return an uncaught exception thrown during the transaction or <code>NULL</code> if the
	 *         transaction completes successfully
	 * @throws SQLException
	 */
	public Throwable executeTransaction(final Transaction transaction) throws SQLException {
		final Connection transactionConnection = getOpenConnection();
		Throwable exception = null;

		try {
			transactionConnection.setAutoCommit(false);
			transaction.execute();
			transactionConnection.commit();
		} catch (Throwable t) {
			exception = t;
			transactionConnection.rollback();
		} finally {
			transactionConnection.setAutoCommit(true);
			transactionConnection.close();
		}

		return exception;
	}

	/**
	 * Runs the specified query with binding the specified parameters and processing the results
	 * with the specified {@link ResultProcessor}.
	 * 
	 * @param <T> the type returned by the processor
	 * @param query the SQL query to be executed
	 * @param queryParameters parameters to be bound to the SQL query
	 * @param processor the processor to be used to process the query results
	 * @return the output of the processor processing the query results
	 * @throws SQLException
	 */
	public <T> QueryResult<T> query(final String query, final Object[] queryParameters,
	        final ResultProcessor<T> processor) throws SQLException {
		final Connection queryConnection = getOpenConnection();
		final PreparedStatement statement = queryConnection.prepareStatement(query);

		bindParameters(queryParameters, statement);

		final QueryResult<T> queryResult = processor.processResultSet(statement.executeQuery());
		queryConnection.close();

		return queryResult;
	}

	/**
	 * Runs the specified update with the specified parameters.
	 * 
	 * @param query the SQL update to be executed
	 * @param queryParameters parameters to be bound to the SQL query
	 * @return the number of rows changed by the update
	 * @throws SQLException
	 */
	public int update(final String query, Object[] queryParameters) throws SQLException {
		final Connection queryConnection = getOpenConnection();
		final PreparedStatement statement = queryConnection.prepareStatement(query);

		bindParameters(queryParameters, statement);

		final int returnValue = statement.executeUpdate();
		queryConnection.close();

		return returnValue;
	}

	/**
	 * Binds query parameters into a {@link PreparedStatement}.
	 * 
	 * @param queryParameters parameters to be bound
	 * @param statement statement into which parameters will be bound
	 * @throws SQLException
	 */
	protected void bindParameters(final Object[] queryParameters, final PreparedStatement statement)
	        throws SQLException {
		if (queryParameters != null) {
			final int arraySize = queryParameters.length;
			for (int a = 0; a < arraySize; a++) {
				statement.setObject(a + 1, queryParameters[a]);
			}
		}
	}

	/**
	 * Returns the current {@link Connection} which may be <code>NULL</code> or closed.
	 * 
	 * @return the current connection
	 */
	protected Connection getCurrentConnection() {
		return connection;
	}

	/**
	 * Returns the current connection if open or a new open connection.
	 * 
	 * @return an open connection to the data source
	 * @throws SQLException
	 */
	protected Connection getOpenConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = dataSource.getConnection();
		}

		return connection;
	}
}
