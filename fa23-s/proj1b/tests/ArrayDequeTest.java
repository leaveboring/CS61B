import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.assertThrows;


public class ArrayDequeTest {

    @Test
    @DisplayName("ArrayDeque has no fields besides backing array and primitives")
    void noNonTrivialFields() {
     List<Field> badFields = Reflection.getFields(ArrayDeque.class)
             .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
             .toList();

     assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        //“add_first_from_empty”
        lld1.addFirst(8);
        assertThat(lld1.toList()).containsExactly(8).inOrder();
        //“add_first_nonempty”
        lld1.addFirst(7);
        assertThat(lld1.toList()).containsExactly(7, 8).inOrder();
        //“add_last_trigger_resize”
        for (int i = 6; i >= 0; i--) {
            lld1.addFirst(i);
        }
        assertThat(lld1.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();
    }

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addLastTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        //“add_first_from_empty”
        lld1.addLast(0);
        assertThat(lld1.toList()).containsExactly(0).inOrder();
        //“add_first_nonempty”
        lld1.addLast(1);
        assertThat(lld1.toList()).containsExactly(0, 1).inOrder();
        //“add_last_trigger_resize”
        for (int i = 2; i <= 8; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();
    }

    @Test
    public void removeFirstTest() {
        Deque<Integer> deque = new ArrayDeque<>();
        // 从空的双端队列中移除元素应该抛出NoSuchElementException
        assertThrows(NoSuchElementException.class, deque::removeFirst);

        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);

        //Is_empty_false
        assertThat(deque.isEmpty()).isEqualTo(false);

        // 移除最后一个元素并检查它是否正确
        assertThat(deque.removeFirst()).isEqualTo(1);
        // 检查双端队列的状态是否正确
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.toList()).containsExactly(2, 3).inOrder();

        // “remove_first_to_one”
        assertThat(deque.removeFirst()).isEqualTo(2);
        // 检查双端队列的状态是否正确
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(3).inOrder();

        //“remove_first_to_empty”
        assertThat(deque.removeFirst()).isEqualTo(3);
        // 检查双端队列的状态是否正确
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.toList()).containsExactly().inOrder();

        //Is_empty_true
        assertThat(deque.isEmpty()).isEqualTo(true);

        // 从空的双端队列中移除元素应该抛出NoSuchElementException
        assertThrows(NoSuchElementException.class, deque::removeLast);
        //“add_last_after_remove_to_empty”
        deque.addFirst(1);
        assertThat(deque.toList()).containsExactly(1).inOrder();

        //Is_empty_false
        assertThat(deque.isEmpty()).isEqualTo(false);
    }

    @Test
    public void removeLastTest() {
        Deque<Integer> deque = new ArrayDeque<>();
        // 从空的双端队列中移除元素应该抛出NoSuchElementException
        assertThrows(NoSuchElementException.class, deque::removeLast);

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        //Is_empty_false
        assertThat(deque.isEmpty()).isEqualTo(false);

        // 移除最后一个元素并检查它是否正确
        assertThat(deque.removeLast()).isEqualTo(3);
        // 检查双端队列的状态是否正确
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.toList()).containsExactly(1, 2).inOrder();

        // “remove_first_to_one”
        assertThat(deque.removeLast()).isEqualTo(2);
        // 检查双端队列的状态是否正确
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(1).inOrder();

        //“remove_first_to_empty”
        assertThat(deque.removeLast()).isEqualTo(1);
        // 检查双端队列的状态是否正确
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.toList()).containsExactly().inOrder();

        //Is_empty_true
        assertThat(deque.isEmpty()).isEqualTo(true);

        // 从空的双端队列中移除元素应该抛出NoSuchElementException
        assertThrows(NoSuchElementException.class, deque::removeLast);
        //“add_last_after_remove_to_empty”
        deque.addLast(1);
        assertThat(deque.toList()).containsExactly(1).inOrder();

        //Is_empty_false
        assertThat(deque.isEmpty()).isEqualTo(false);
    }

    @Test
    public void removeFirstResizeTest() {
        Deque<Integer> deque = new ArrayDeque<>();
        assertThat(deque.len()).isEqualTo(8);

        for (int i = 0; i < 16; i++) {
            deque.addFirst(i);
        }

        assertThat(deque.len()).isEqualTo(32);

        for (int i = 0; i < 14; i++) {
            deque.removeFirst();
        }
        assertThat(deque.toList()).containsExactly(1, 0).inOrder();
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.len()).isEqualTo(8);
    }

    @Test
    public void removeLastResizeTest() {
        Deque<Integer> deque = new ArrayDeque<>();
        assertThat(deque.len()).isEqualTo(8);

        for (int i = 0; i < 16; i++) {
            deque.addLast(i);
        }

        assertThat(deque.len()).isEqualTo(32);

        for (int i = 0; i < 14; i++) {
            deque.removeLast();
        }
        assertThat(deque.toList()).containsExactly(0, 1).inOrder();
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.len()).isEqualTo(8);
    }


//    @Test
//    public void LinkedListDequeTest() {
//        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
//
//        lld1.addFirst("back");
//        lld1.addFirst("middle");
//        lld1.addFirst("front");// after this call we expect: ["front", "middle", "back"]
//
//        Deque<String> lld2 = new LinkedListDeque<>(lld1);
//        assertThat(lld2.toList()).containsExactly("front", "middle", "back").inOrder();
//    }
//
//    @Test
//    public void getTest() {
//        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
//
//        lld1.addFirst("back");
//        lld1.addFirst("middle");
//        lld1.addFirst("front");// after this call we expect: ["front", "middle", "back"]
//
//        assertThat(lld1.get(0)).isEqualTo("front");
//        assertThat(lld1.getRecursive(0)).isEqualTo("front");
//        assertThat(lld1.get(1)).isEqualTo("middle");
//        assertThat(lld1.getRecursive(1)).isEqualTo("middle");
//        assertThat(lld1.get(2)).isEqualTo("back");
//        assertThat(lld1.getRecursive(2)).isEqualTo("back");
//    }
//
//    @Test
//    public void isEmptyAndSizeTest() {
//        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
//        assertThat(lld1.isEmpty()).isEqualTo(true);
//
//        lld1.addFirst("back");
//        lld1.addFirst("middle");
//        lld1.addFirst("front");
//        assertThat(lld1.isEmpty()).isEqualTo(false);
//        assertThat(lld1.size()).isEqualTo(3);
//
//        lld1.removeFirst();// after this call we expect: ["front", "middle", "back"]
//        lld1.removeLast();
//        assertThat(lld1.isEmpty()).isEqualTo(false);
//        assertThat(lld1.size()).isEqualTo(1);
//    }
//
//    @Test
//    public void printDequeTest() {
//        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
//
//        lld1.addFirst("back");
//        lld1.addFirst("middle");
//        lld1.addFirst("front");
//        assertThat(lld1.isEmpty()).isEqualTo(false);
//        assertThat(lld1.size()).isEqualTo(3);
//
//        lld1.removeFirst();// after this call we expect: ["front", "middle", "back"]
//        lld1.removeLast();
//        assertThat(lld1.isEmpty()).isEqualTo(false);
//        assertThat(lld1.size()).isEqualTo(1);
//    }
}
