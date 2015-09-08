package kr.co.d2net.commons.utils;

import java.util.LinkedList;

public class SwappingFifoQueue<E> {
	
	private volatile LinkedList<E> instances = new LinkedList<E>();

	public void put(E o) {
		instances.addLast(o);
	}

	public E get() {
		if (instances.isEmpty()) {
			return null;
		}
		return instances.removeFirst();
	}

	public E peek() {
		return instances.getFirst();
	}

	public boolean isEmpty() {
		return instances.isEmpty();
	}

	public int size() {
		return instances.size();
	}

}
