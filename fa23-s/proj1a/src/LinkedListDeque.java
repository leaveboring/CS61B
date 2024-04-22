import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T> {
    //节点定义
    public static class ListNode<TT> {
        TT item;
        ListNode<TT> prev;
        ListNode<TT> next;

        public ListNode() {
            item = null;
        }
        public ListNode(TT item) {
            this.item = item;
        }
        public ListNode(TT item, ListNode<TT> prev, ListNode<TT> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    private int size;

    private final ListNode<T> sentFront = new ListNode<T>();
    private final ListNode<T> sentLast = new ListNode<T>();
    //无参构造
    public LinkedListDeque(){
        sentFront.next = sentLast;
        sentLast.prev = sentFront;
    }

    //有参构造
    public LinkedListDeque(LinkedListDeque<T> other) {
        if (other == null) throw new NullPointerException();

        this.size = other.size;
        ListNode<T> head = this.sentFront;
        ListNode<T> otherItem = other.sentFront.next;
        for (int i = 0; i < this.size; i++) {
            ListNode<T> temp = new ListNode<T>(otherItem.item);
            temp.prev = head;
            head.next = temp;
            head = temp;
            otherItem = otherItem.next;
        }
        head.next = sentLast;
        sentLast.prev = head;
    }

    public void addFirst(T item) {
        ListNode<T> temp = new ListNode<T>(item, sentFront, sentFront.next);
        sentFront.next.prev = temp;
        sentFront.next = temp;
        size++;
    }

    public void addLast(T item) {
        ListNode<T> temp = new ListNode<T>(item, sentLast.prev, sentLast);
        sentLast.prev.next = temp;
        sentLast.prev = temp;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        ListNode<T> tmp = sentFront.next;
        for (int i = 0; i < size; i++) {
            T var = get(i);
            returnList.add(i, var);
            tmp = tmp.next;
        }
        return returnList;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        ListNode<T> temp = sentFront.next;
        while (temp != sentLast) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        ListNode<T> temp = sentFront.next;
        T item = temp.item;
        sentFront.next = temp.next;
        temp.next.prev = sentFront;
        size--;
        return item;
    }

    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        T item = sentLast.prev.item;
        ListNode<T> temp = sentLast.prev;
        sentLast.prev = temp.prev;
        temp.prev.next = sentLast;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (size == 0) throw new NoSuchElementException();
        ListNode<T> temp = sentFront.next;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size()) return null;

        return getRecursiveHelper(sentFront, index);
    }

    public T getRecursiveHelper(ListNode<T> node, int index) {
        if (index == 0) return node.next.item;
        return getRecursiveHelper(node.next, index - 1);
    }
}
