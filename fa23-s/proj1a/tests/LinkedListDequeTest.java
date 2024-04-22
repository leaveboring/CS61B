import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
//输出测试
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque.
    @Test
    public void removeFirstTest() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");// after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        String tmp = lld1.removeFirst(); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
        assertThat(tmp).isEqualTo("front");

        tmp = lld1.removeFirst(); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly( "back").inOrder();
        assertThat(tmp).isEqualTo("middle");

        tmp = lld1.removeFirst(); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly().inOrder();
        assertThat(tmp).isEqualTo("back");
    }

    @Test
    public void removeLastTest() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");// after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        String tmp = lld1.removeLast(); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();
        assertThat(tmp).isEqualTo("back");

        tmp = lld1.removeLast(); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly( "front").inOrder();
        assertThat(tmp).isEqualTo("middle");

        tmp = lld1.removeLast(); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly().inOrder();
        assertThat(tmp).isEqualTo("front");
    }

    @Test
    public void LinkedListDequeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");// after this call we expect: ["front", "middle", "back"]

        Deque<String> lld2 = new LinkedListDeque<>(lld1);
        assertThat(lld2.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void getTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");// after this call we expect: ["front", "middle", "back"]

        assertThat(lld1.get(0)).isEqualTo("front");
        assertThat(lld1.getRecursive(0)).isEqualTo("front");
        assertThat(lld1.get(1)).isEqualTo("middle");
        assertThat(lld1.getRecursive(1)).isEqualTo("middle");
        assertThat(lld1.get(2)).isEqualTo("back");
        assertThat(lld1.getRecursive(2)).isEqualTo("back");
    }

    @Test
    public void isEmptyAndSizeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        assertThat(lld1.isEmpty()).isEqualTo(true);

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");
        assertThat(lld1.isEmpty()).isEqualTo(false);
        assertThat(lld1.size()).isEqualTo(3);

        lld1.removeFirst();// after this call we expect: ["front", "middle", "back"]
        lld1.removeLast();
        assertThat(lld1.isEmpty()).isEqualTo(false);
        assertThat(lld1.size()).isEqualTo(1);
    }

    @Test
    public void printDequeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");
        assertThat(lld1.isEmpty()).isEqualTo(false);
        assertThat(lld1.size()).isEqualTo(3);

        lld1.removeFirst();// after this call we expect: ["front", "middle", "back"]
        lld1.removeLast();
        assertThat(lld1.isEmpty()).isEqualTo(false);
        assertThat(lld1.size()).isEqualTo(1);
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testPrintDeque() {
        // 假设Deque是一个双端队列的实现，ListNode是其中的节点类
        // 创建一个Deque实例，并添加一些元素
        Deque<String> deque = new LinkedListDeque<>();
        deque.addFirst("World");
        deque.addFirst("Hello");

        // 调用printDeque方法
        ((LinkedListDeque<String>) deque).printDeque();

        // 使用Truth库断言输出
        assertThat(outContent.toString().trim()).isEqualTo("Hello World");
    }
}