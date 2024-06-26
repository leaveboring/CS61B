import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Deque<T> {
    private int front;
    private int tail;
    private int len = 8;
    private T[] items = (T []) new Object[8];
    private int size = 0;

    public ArrayDeque(){}

    public ArrayDeque(ArrayDeque<T> other) {
        this.front = other.front;
        this.tail = other.tail;
        this.len = other.len;
        this.size = other.size;
        items = (T []) new Object[len];
        for (int i = 0; i < size; i++) {
            this.items[(front + i) % len] = other.items[(front + i) % len];
        }
    }

    @Override
    public void addFirst(T x) {
        if (len - 1 == size) getLagerArray();
        front = (front - 1 + len) % len;
        items[front] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (len - 1 == size) getLagerArray();
        items[tail] = x;
        tail = (tail + 1 + len) % len;
        size++;
    }

    public void getLagerArray() {
        T[] temps = (T []) new Object[2 * len];
        for (int i = 0; i < size; i++) {
            temps[i] = items[(front + i) % len];
        }
        front = 0;
        tail = size;
        len = 2 * len;
        items = null;
        items = temps;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = front; i != tail; i = (i + 1) % len) {
            returnList.add(items[i]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public int len() {
        return len;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        if (len > 8 && size < len / 4) getSmallerArray();
        T x = items[front];
        items[front] = null;
        front = (front + 1 + len) % len;
        size--;
        return x;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        if (len > 8 && size < len / 4) getSmallerArray();
        tail = (tail - 1) % len;
        T x = items[tail];
        items[tail] = null;
        size--;
        return x;
    }

    public void getSmallerArray() {
        T[] temps = (T []) new Object[len / 2];
        for (int i = 0; i < size; i++) {
            temps[i] = items[(front + i) % len];
        }
        front = 0;
        tail = size;
        len = len / 2;
        items = null;
        items = temps;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new NoSuchElementException("index is illegal");
        }
        return items[(index + front) % len];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
