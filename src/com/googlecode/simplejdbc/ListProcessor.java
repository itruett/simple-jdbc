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
import java.util.ArrayList;
import java.util.List;

/**
 * Produces a {@link List} using a {@link SingleRecordProcessor} applied to each row in the
 * {@link ResultSet}.
 * 
 * @param <T> the type of data in the list
 */
public class ListProcessor<T> extends AbstractResultProcessor<List<T>> {
	private final SingleRecordProcessor<T> rowProcessor;

	/**
	 * Creates a new ListProcessor.
	 * 
	 * @param rowProcessor the processor applied to each row
	 */
	public ListProcessor(final SingleRecordProcessor<T> rowProcessor) {
		super();
		this.rowProcessor = rowProcessor;
	}

	/**
	 * @see com.googlecode.simplejdbc.ResultProcessor#processResultSet(java.sql.ResultSet)
	 */
	@Override
	public QueryResult<List<T>> processResultSet(final ResultSet resultSet) throws SQLException {
		final List<T> list = new ArrayList<T>();
		int rowCount = 0;

		while (resultSet.next()) {
			rowCount++;
			list.add(rowProcessor.processRow(resultSet));
		}

		return createQueryResults(list, rowCount);
	}
}
