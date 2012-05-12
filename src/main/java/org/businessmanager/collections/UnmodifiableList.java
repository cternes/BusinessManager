package org.businessmanager.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UnmodifiableList<E> extends ArrayList<E> {

	public UnmodifiableList(List<E> list) {
		super(list);
	}

	@Override
	public ListIterator<E> listIterator(int paramInt) {
		return new UnmodifiableListIterator<E>(super.listIterator(paramInt));
	}

	@Override
	public ListIterator<E> listIterator() {
		return new UnmodifiableListIterator<E>(super.listIterator());
	}

	@Override
	public Iterator<E> iterator() {
		return new UnmodifiableIterator<E>(super.iterator());
	}

	@Override
	public E set(int paramInt, E paramE) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E paramE) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int paramInt, E paramE) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int paramInt) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object paramObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> paramCollection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void removeRange(int paramInt1, int paramInt2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> paramCollection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> paramCollection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(int paramInt1, int paramInt2) {
		List<E> subList = super.subList(paramInt1, paramInt2);
		return new UnmodifiableList<E>(subList);
	}
}
