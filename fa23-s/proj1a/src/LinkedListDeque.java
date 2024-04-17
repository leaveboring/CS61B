import java.util.NoSuchElementException;

public class LinkedListDeque<T> {
    //节点定义
    public static class ListNode<TT> {
        TT item;
        ListNode<TT> prev;
        ListNode<TT> next;

        public ListNode() {}
        public ListNode(TT item) { this.item = item;}
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
    public LinkedListDeque() {}
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

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        ListNode<T> temp = sentFront.next;
        while (temp != null) {
            System.out.print(temp.item + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        T item = sentFront.next.item;
        ListNode<T> temp = sentFront.next;
        sentFront.next = temp.next;
        temp.next.prev = sentFront;
        temp = null;
        return item;
    }

    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        T item = sentLast.prev.item;
        ListNode<T> temp = sentLast.prev;
        sentLast.prev = temp.prev;
        temp.prev.next = sentLast;
        temp = null;
        return item;
    }
}
