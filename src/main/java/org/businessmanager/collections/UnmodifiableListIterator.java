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
