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

/**
 * Base class for {@link ResultProcessor} implementations.
 * 
 * @param <T> the data type returned by the processor
 */
public abstract class AbstractResultProcessor<T> implements ResultProcessor<T> {
	/**
	 * Convenience method to create a {@link QueryResult}.
	 * 
	 * @param resultValue the value produced by the processor
	 * @param rowCount the number of rows processed in the result set
	 * @return the results of processing the query
	 */
	protected QueryResult<T> createQueryResults(final T resultValue, final int rowCount) {
		return new QueryResult<T>() {
			@Override
			public int getRowCount() {
				return rowCount;
			}

			@Override
			public T getValue() {
				return resultValue;
			}
		};
	}
}
