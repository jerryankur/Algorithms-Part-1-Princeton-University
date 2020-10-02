import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rQueue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rQueue = (Item[]) new Object[1];      // 1 length is needed, 0 will fail the logic
        size = 0;
    }

    private class RQueueIterator implements Iterator<Item> {
        private Item[] rQueueCopy;
        private int sizeCopy;

        public RQueueIterator() {
            rQueueCopy = (Item[]) new Object[size];
            for (int x = 0; x < size; ++x)
                rQueueCopy[x] = rQueue[x];
            sizeCopy = size;
        }

        public boolean hasNext() {
            return sizeCopy != 0;
        }

        public Item next() {
            if (hasNext()) {
                int i = StdRandom.uniform(sizeCopy);
                Item val = rQueueCopy[i];
                rQueueCopy[i] = rQueueCopy[--sizeCopy];
                return val;
            }
            else throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        validateItem(item);
        if (size >= rQueue.length) resize(rQueue.length * 2);
        rQueue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        validateQueue();
        if (size - 1 <= rQueue.length / 4 && rQueue.length > 1) resize(rQueue.length / 2);
        int i = StdRandom.uniform(size);
        Item val = rQueue[i];
        rQueue[i] = rQueue[--size];
        return val;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateQueue();
        int i = StdRandom.uniform(size);
        return rQueue[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQueueIterator();
    }

    private void resize(int newSize) {
        Item[] temp = rQueue;
        rQueue = (Item[]) new Object[newSize];
        for (int x = 0; x < size; ++x)
            rQueue[x] = temp[x];
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void validateQueue() {
        if (size == 0) throw new NoSuchElementException();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.println(rQueue.dequeue());
            else
                rQueue.enqueue(s);
        }
    }

}