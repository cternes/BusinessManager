package org.businessmanager.collections;

import java.util.Iterator;

public class UnmodifiableIterator<E> implements Iterator<E> {

	private Iterator<E> iterator;

	public UnmodifiableIterator(Iterator<E> iterator) {
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
	public void remove() {
		throw new UnsupportedOperationException("remove() is not supported");
	}

}
