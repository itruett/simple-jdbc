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
 * Produces a single {@link Double} value from a {@link ResultSet}.
 */
public class SingleDoubleProcessor extends AbstractSingleRecordProcessor<Double> {
	private final String columnName;

	/**
	 * Creates a new SingleDoubleProcessor.
	 * 
	 * @param columnNamePrefix
	 * @param columnName
	 */
	public SingleDoubleProcessor(final String columnNamePrefix, final String columnName) {
		super(columnNamePrefix);
		this.columnName = columnName;
	}

	/**
	 * Creates a new SingleDoubleProcessor.
	 * 
	 * @param columnName
	 */
	public SingleDoubleProcessor(final String columnName) {
		this("", columnName);
	}

	/**
	 * @see com.googlecode.simplejdbc.SingleRecordProcessor#processRow(java.sql.ResultSet)
	 */
	@Override
	public Double processRow(final ResultSet resultSet) throws SQLException {
		return getDouble(resultSet, columnName);
	}
}
