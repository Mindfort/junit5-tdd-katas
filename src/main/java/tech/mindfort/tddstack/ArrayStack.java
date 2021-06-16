package tech.mindfort.tddstack;

import java.util.Arrays;

public class ArrayStack<T> {

	private static final int DEFAULT_INITIAL_SIZE = 16;
	private static final int DEFAULT_MAX_SIZE = Integer.MAX_VALUE;
	private T[] itemsArr;
	private int top = 0;
	private int maxSize;
	private int initialSize;

	public static <T> ArrayStack<T> create() {
		return new ArrayStack<T>(DEFAULT_INITIAL_SIZE);
	}

	public static <T> ArrayStack<T> withInitialSize(final int initialSize) {
		return new ArrayStack<T>(initialSize);
	}

	public static <T> ArrayStack<T> withInitialAndMaxSize(
			final int initialSize,
			final int maxSize) {
		// TODO add a test case to validate input parameters else throw
		// exception.
		return new ArrayStack<T>(initialSize, maxSize);
	}

	private ArrayStack(int initialSize) {
		this(initialSize, DEFAULT_MAX_SIZE);
	}

	@SuppressWarnings("unchecked")
	private ArrayStack(int initialSize, int maxSize) {
		this.initialSize = initialSize;
		this.maxSize = maxSize;
		itemsArr = (T[]) new Object[initialSize];
	}

	public void push(T item) {
		if (top == maxSize) {
			throw new StackIsFullException();
		}
		growStackArraySizeIfRequired();
		itemsArr[top] = item;
		top++;
	}

	private void growStackArraySizeIfRequired() {
		if (top == getStackArraySize()) {
			int newArrLength = getStackArraySize() * 2;
			newArrLength = newArrLength > maxSize ? maxSize : newArrLength;
			itemsArr = Arrays.copyOf(itemsArr, newArrLength);
		}
	}

	public T pop() {
		T itemOnTop = peek();
		top--;
		itemsArr[top] = null; // Dereferencing for GC
		shrinkArraySizeAsRequired();
		return itemOnTop;
	}

	private void shrinkArraySizeAsRequired() {
		int newReducedArrLength = getStackArraySize() / 2;
		if (top <= newReducedArrLength && newReducedArrLength >= initialSize) {
			itemsArr = Arrays.copyOf(itemsArr, newReducedArrLength);
		}
	}

	public T peek() {
		if (isEmpty()) {
			throw new StackIsEmptyException();
		}
		return itemsArr[top - 1];
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return top;
	}

	// This method is for testing purpose
	// Hence, making it package private
	int getStackArraySize() {
		return itemsArr.length;
	}

	public static class StackIsEmptyException extends RuntimeException {

		private static final long serialVersionUID = 1L;

	}

	public static class StackIsFullException extends RuntimeException {

		private static final long serialVersionUID = 1L;

	}


}
