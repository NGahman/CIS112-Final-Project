package FinalProject;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import bookFiles.ch07.trees.BSTInterface;
import bookFiles.support.BSTNode;

public class AVLThreadedBST<T> implements BSTInterface<T> {
    private BSTNode<T> root = null;
    private Comparator comp;
    private int numElements;

    public AVLThreadedBST() {
        comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) o1).compareTo(o2);
            }
        };
    }

    public AVLThreadedBST(Comparator comp) {
        this.comp = comp;
    }

    public T min() {
        if (isEmpty()) {
            return null;
        }
        BSTNode<T> node = root;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getInfo();
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        BSTNode<T> node = root;
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node.getInfo();
    }

    public Iterator<T> getIterator(Traversal orderType) {//TODO
        if (orderType == Traversal.Inorder) {
            return new Iterator<>() {
                private int numIterated = 0;
                private HashSet<BSTNode<T>> visited = new HashSet<>();
                private BSTNode<T> node = root;

                public boolean hasNext() {
                    return numIterated != numElements;
                }

                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    T out;
                    if (node.getLeft() == null || visited.contains(node.getLeft())) {
                        out = node.getInfo();
                        node = node.getRight();
                        numIterated++;
                    } else {
                        node = node.getLeft();
                        visited.add(node);
                        out = next();
                    }
                    return out;
                }
            };
        }
        return null;
    }

    public boolean add(T element) {//TODO
        numElements++;
        if (root == null) {
            root = new BSTNode<>(element);
            root.setRight(root);
        }
        return true;
    }

    public T get(T target) {//TODO
        return null;
    }

    public boolean contains(T target) {//TODO
        return false;
    }

    public boolean remove(T target) {//TODO
        numElements--;
        return false;
    }

    public boolean isFull() {
        return false;
    }

    public boolean isEmpty() {
        return numElements == 0;
    }

    public int size() {
        return numElements;
    }

    public Iterator<T> iterator() {
        return getIterator(Traversal.Inorder);
    }

    private void reBalance() {//TODO
        ;
    }
}