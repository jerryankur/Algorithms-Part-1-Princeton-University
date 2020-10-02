import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        final private Item item;
        private Node next, prev;

        public Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }
    }

    private class ListIterator implements Iterator<Item> {
        private Node current;

        public ListIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (hasNext()) {
                Item val = current.item;
                current = current.next;
                return val;
            }
            else throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        Node second = first;
        first = new Node(item);
        first.next = second;
        if (isEmpty())
            last = first;
        else second.prev = first;
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        if (isEmpty()) {
            first = new Node(item);
            last = first;
        }
        else {
            last.next = new Node(item);
            last.next.prev = last;
            last = last.next;
        }
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateList();
        Item val = first.item;
        first = first.next;
        --size;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateList();
        Item val = last.item;
        last = last.prev;
        --size;
        if (isEmpty())
            first = null;
        else last.next = null;
        return val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void validateList() {
        if (size == 0) throw new NoSuchElementException();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(deque.removeLast());
            else
                deque.addFirst(s);
        }
    }
}