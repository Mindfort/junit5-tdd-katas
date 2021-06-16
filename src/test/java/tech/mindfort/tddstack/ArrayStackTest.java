package tech.mindfort.tddstack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tech.mindfort.tddstack.ArrayStack.StackIsEmptyException;
import tech.mindfort.tddstack.ArrayStack.StackIsFullException;

class ArrayStackTest {

	ArrayStack<String> stack;

	@BeforeEach
	void setupBeforeEachTestCase() {
		stack = ArrayStack.create();
	}

	@Test
	void testStaticArrayCreationMethods() {
		stack = ArrayStack.withInitialSize(1);
		stack = ArrayStack.withInitialAndMaxSize(1, 2);
	}

	@Test
	void testArraySizeIsZeroOnCreation() {
		assertThat(stack.size()).isZero();
		assertThat(stack.isEmpty()).isTrue();
	}

	@Test
	void testPushSingleItemOnTheStackAndPeek() {
		final String itemPushed = "a";
		stack.push(itemPushed);
		assertThat(stack.size()).as("Stack Size").isEqualTo(1);
		assertThat(stack.peek()).isEqualTo(itemPushed);
	}

	@Test
	@DisplayName("Single Item Pushed and then Popped")
	void testPushSingleItemOnTheStackAndPop() {
		final String itemPushed = "a";
		stack.push(itemPushed);
		assertThat(stack.size()).as("Stack Size").isEqualTo(1);
		assertThat(stack.pop()).isEqualTo(itemPushed);
		assertThat(stack.size()).as("Stack Size").isZero();
	}

	@Test
	void testPeekShouldThrowAnExceptionOnEmptyStack() throws Exception {
		assertThatExceptionOfType(StackIsEmptyException.class)
				.isThrownBy(() -> stack.peek());
	}

	@Test
	void testPopShouldThrowAnExceptionOnEmptyStack() throws Exception {
		assertThatExceptionOfType(StackIsEmptyException.class)
				.isThrownBy(() -> stack.pop());
	}

	@Test
	void testPushMoreThanInitialSizeStackArrShouldGrow() {
		stack = ArrayStack.withInitialSize(1);
		stack.push("a");
		assertThat(stack.getStackArraySize()).isEqualTo(1);
		stack.push("b");
		assertThat(stack.getStackArraySize()).isEqualTo(2);
	}

	@Test
	void testPushMoreThanMaxCapacityShouldThrowAnException() {
		stack = ArrayStack.withInitialAndMaxSize(1, 1);
		stack.push("a");
		assertThat(stack.getStackArraySize()).isEqualTo(1);
		assertThatExceptionOfType(StackIsFullException.class)
				.isThrownBy(() -> stack.push("b"));
	}

	@Test
	void testPushingItemsShouldNotIncreaseTheArraySizeMoreThanMax() {
		stack = ArrayStack.withInitialAndMaxSize(1, 5);
		stack.push("a");
		assertStackArrSizeAndTopItemOfTheStack("a", 1);
		stack.push("b");
		assertStackArrSizeAndTopItemOfTheStack("b", 2);
		stack.push("c");
		assertStackArrSizeAndTopItemOfTheStack("c", 4);
		stack.push("d");
		assertStackArrSizeAndTopItemOfTheStack("d", 4);
		stack.push("e");
		assertStackArrSizeAndTopItemOfTheStack("e", 5);

	}

	@Test
	void testPoppingMultipleELementsShouldShrinkTheArraySize() {
		stack = ArrayStack.withInitialAndMaxSize(2, 5);
		stack.push("a");
		stack.push("b");
		stack.push("c");
		stack.push("d");
		assertStackArrSizeAndTopItemOfTheStack("d", 4);
		assertThat(stack.pop()).isEqualTo("d");
		assertThat(stack.size()).isEqualTo(3);
		assertThat(stack.getStackArraySize()).isEqualTo(4);
		assertThat(stack.pop()).isEqualTo("c");
		assertThat(stack.size()).isEqualTo(2);
		assertThat(stack.getStackArraySize()).isEqualTo(2);
		assertThat(stack.pop()).isEqualTo("b");
		assertThat(stack.size()).isEqualTo(1);
		assertThat(stack.getStackArraySize()).isEqualTo(2);

	}

	private void assertStackArrSizeAndTopItemOfTheStack(String expectedItem,
			int expectedStackArrSize) {
		assertThat(stack.getStackArraySize()).isEqualTo(expectedStackArrSize);
		assertThat(stack.peek()).isEqualTo(expectedItem);
	}
}
