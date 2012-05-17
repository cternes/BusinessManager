/*******************************************************************************
 * Copyright 2012 Christian Ternes and Thorsten Volland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.businessmanager.collections;

import java.util.ListIterator;

public class UnmodifiableListIterator<E> implements ListIterator<E> {

	private ListIterator<E> iterator;
	
	public UnmodifiableListIterator(ListIterator<E> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public E next() {
		return this.iterator.next();
	}

	@Override
	public boolean hasPrevious() {
		return this.iterator.hasPrevious();
	}

	@Override
	public E previous() {
		return this.iterator.previous();
	}

	@Override
	public int nextIndex() {
		return this.iterator.nextIndex();
	}

	@Override
	public int previousIndex() {
		return this.iterator.previousIndex();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(E paramE) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(E paramE) {
		throw new UnsupportedOperationException();
	}	
}
