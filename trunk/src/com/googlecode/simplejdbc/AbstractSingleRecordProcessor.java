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

/**
 * Base class for {@link SingleRecordProcessor} implementations.
 * 
 * @param <T> the data type produced by the processor
 */
public abstract class AbstractSingleRecordProcessor<T> extends AbstractResultProcessor<T> implements
        SingleRecordProcessor<T> {
	private final String columnNamePrefix;

	/**
	 * Creates a new AbstractSingleRecordProcessor.
	 * 
	 * @param columnNamePrefix
	 */
	public AbstractSingleRecordProcessor(final String columnNamePrefix) {
		super();
		this.columnNamePrefix = columnNamePrefix;
	}

	/**
	 * @see com.googlecode.simplejdbc.ResultProcessor#processResultSet(java.sql.ResultSet)
	 */
	@Override
	public QueryResult<T> processResultSet(final ResultSet resultSet) throws SQLException {
		final T resultValue;
		int rowCount = 0;

		if (resultSet.next()) {
			rowCount++;
			resultValue = processRow(resultSet);
		} else {
			resultValue = null;
		}

		return createQueryResults(resultValue, rowCount);
	}

	/**
	 * Returns the integer value of the specified column in the {@link ResultSet}.
	 * 
	 * @param resultSet the result set being processed
	 * @param columnName the name of the column to retrieve
	 * @return the integer value of the specified column
	 * @throws SQLException
	 */
	protected Integer getInteger(final ResultSet resultSet, final String columnName)
	        throws SQLException {
		return Integer.valueOf(resultSet.getInt(columnNamePrefix + columnName));
	}

	/**
	 * Returns the {@link String} value of the specified column in the {@link ResultSet}.
	 * 
	 * @param resultSet the result set being processed
	 * @param columnName the name of the column to retrieve
	 * @return the integer value of the specified column
	 * @throws SQLException
	 */
	protected String getString(final ResultSet resultSet, final String columnName)
	        throws SQLException {
		return resultSet.getString(columnNamePrefix + columnName);
	}

	/**
	 * Returns the double value of the specified column in the {@link ResultSet}.
	 * 
	 * @param resultSet the result set being processed
	 * @param columnName the name of the column to retrieve
	 * @return the double value of the specified column
	 * @throws SQLException
	 */
	protected Double getDouble(final ResultSet resultSet, final String columnName)
	        throws SQLException {
		return Double.valueOf(resultSet.getDouble(columnNamePrefix + columnName));
	}

	/**
	 * Returns the boolean value of the specified column in the {@link ResultSet}.
	 * 
	 * @param resultSet the result set being processed
	 * @param columnName the name of the column to retrieve
	 * @return the boolean value of the specified column
	 * @throws SQLException
	 */
	protected Boolean getBoolean(final ResultSet resultSet, final String columnName)
	        throws SQLException {
		return Boolean.valueOf(resultSet.getBoolean(columnNamePrefix + columnName));
	}
}
