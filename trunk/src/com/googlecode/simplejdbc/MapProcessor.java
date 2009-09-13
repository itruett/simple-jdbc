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
import java.util.HashMap;
import java.util.Map;

/**
 * Produces a {@link Map} using a {@link SingleRecordProcessor} to produce a key and value from each
 * row in the {@link ResultSet}.
 * 
 * @param <K>
 * @param <V>
 */
public class MapProcessor<K, V> extends AbstractResultProcessor<Map<K, V>> {
	private final SingleRecordProcessor<K> keyProcessor;
	private final SingleRecordProcessor<V> valueProcessor;

	/**
	 * Creates a new MapResultProcessor.
	 * 
	 * @param keyProcessor
	 * @param valueProcessor
	 */
	public MapProcessor(final SingleRecordProcessor<K> keyProcessor,
	        final SingleRecordProcessor<V> valueProcessor) {
		super();
		this.keyProcessor = keyProcessor;
		this.valueProcessor = valueProcessor;
	}

	/**
	 * @see com.googlecode.simplejdbc.ResultProcessor#processResultSet(java.sql.ResultSet)
	 */
	@Override
	public QueryResult<Map<K, V>> processResultSet(final ResultSet resultSet) throws SQLException {
		final Map<K, V> resultMap = new HashMap<K, V>();
		int rowCount = 0;

		while (resultSet.next()) {
			rowCount++;
			resultMap.put(keyProcessor.processRow(resultSet), valueProcessor.processRow(resultSet));
		}

		return createQueryResults(resultMap, rowCount);
	}
}
