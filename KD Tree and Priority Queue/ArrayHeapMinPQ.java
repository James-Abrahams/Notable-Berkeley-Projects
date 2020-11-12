package bearmaps;
import java.util.*;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> minHeap;
    private HashMap<T, Integer> heapHash;
    private int size;
    private Node sentinal;

    public ArrayHeapMinPQ() {
        size = 0;
        minHeap = new ArrayList<>();
        heapHash = new HashMap<>();
        sentinal = new Node(null, 0, 0);
        minHeap.add(sentinal);
        heapHash.put(null, 0);
    }
    private class Node {
        private T value;
        private double priority;
        private int index;
        Node(T v, double p, int i) {
            value = v;
            priority = p;
            index = i;
        }
    }
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("This item is already in the heap.");
        }
        size++;
        Node newItem = new Node(item, priority, size());
        minHeap.add(newItem);
        heapHash.put(item, size() + 1);
        swim(newItem);
    }
    @Override
    public boolean contains(T item) {
        return (heapHash.containsKey(item));
    }
    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new IllegalArgumentException("no items");
        }
        return minHeap.get(1).value;
    }
    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new IllegalArgumentException("no items");
        }
        T highestPriority = minHeap.get(1).value;
        int last = size(); //last index of heap
        if (size() > 1) {
            swap(minHeap.get(1), minHeap.get(last)); //swap first and last index
            heapHash.remove(minHeap.get(last).value);
            minHeap.remove(minHeap.get(last)); //removes prior first item
            size--;
            sink(minHeap.get(1)); //sink replaced item to rebalance heap
        } else {
            heapHash.remove(minHeap.get(1).value);
            minHeap.remove(minHeap.get(1)); //removes prior first item
            size--;
        }
        return highestPriority;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new IllegalArgumentException("this item doesn't exist.");
        }
        int index = heapHash.get(item);
        minHeap.get(index).priority = priority;
        swim(minHeap.get(index)); //if applicaple swim
        sink(minHeap.get(index)); //if applicable sink
    }

    private void swim(Node n) {
        while (parent(n).priority > n.priority && n.index > 1) {
            swap(parent(n), n);
        }
    }
    private void sink(Node k) {
        while (2 * k.index <= size()) {
            int j = 2 * k.index;
            if (j < size() && (minHeap.get(j).priority > minHeap.get(j + 1).priority)) {
                j++;
            }
            if (!(k.priority > minHeap.get(j).priority)) {
                break;
            }
            swap(k, minHeap.get(j));
        }
    }
    private Node parent(Node n) {
        return minHeap.get(n.index / 2);
    }
    private void swap(Node a, Node b) {
        int indexB = b.index;
        int indexA = a.index;
        minHeap.set(indexA, b);
        minHeap.set(indexB, a);
        heapHash.replace(b.value, indexA);
        heapHash.replace(a.value, indexB);
        a.index = indexB;
        b.index = indexA;
    }

}
