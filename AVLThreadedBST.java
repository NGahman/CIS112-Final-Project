package FinalProject;

import java.util.*;

import bookFiles.ch07.trees.BSTInterface;

public class AVLThreadedBST<T> implements BSTInterface<T> {
    private ThreadedBSTNode<T> root = null;
    private Comparator comp;
    private int numElements;

    public AVLThreadedBST() {
        comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable)o1).compareTo(o2);
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
        ThreadedBSTNode<T> node = root;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getInfo();
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        ThreadedBSTNode<T> node = root;
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node.getInfo();
    }

    public Iterator<T> getIterator(Traversal orderType) {
        if (orderType == Traversal.Inorder) {
            return new Iterator<>() {
                private int numIterated = 0;
                private ThreadedBSTNode<T> node = root;
                private boolean nodeIsThread = false;

                public boolean hasNext() {
                    return numIterated != numElements;
                }

                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    T out;
                    if (node.getLeft() == null || nodeIsThread) {
                        out = node.getInfo();
                        nodeIsThread = node.hasThread;
                        node = node.getRight();
                        numIterated++;
                    } else {
                        node = node.getLeft();
                        out = next();
                    }
                    return out;
                }
            };
        } if (orderType == Traversal.Preorder) {
            LinkedList<T> q = new LinkedList<>();
            preOrder(root, q);
            return new Iterator<>() {
                public boolean hasNext() {
                    return !q.isEmpty();
                }

                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return q.removeFirst();
                }
            };
        } if (orderType == Traversal.Postorder) {
            LinkedList<T> q = new LinkedList<>();
            postOrder(root, q);
            return new Iterator<>() {
                public boolean hasNext() {
                    return !q.isEmpty();
                }

                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return q.removeFirst();
                }
            };
        }
        throw new UnsupportedOperationException(); // this should never trigger (hopefully)
    }

    private void preOrder(ThreadedBSTNode<T> node, LinkedList<T> q) {
        if (node != null) {
            q.add(node.getInfo());
            preOrder(node.getLeft(), q);
            preOrder(node.getRight(), q);
        }
    }

    private void postOrder(ThreadedBSTNode<T> node, LinkedList<T> q) {
        if (node != null) {
            postOrder(node.getLeft(), q);
            postOrder(node.getRight(), q);
            q.add(node.getInfo());
        }
    }

    public boolean add(T element) {//TODO
        numElements++;
        if (root == null) {
            root = new ThreadedBSTNode<>(element);
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

    //reBalance

    private boolean isBalanced = true;

    private void reBalance() {
        if (isEmpty()) {
            return;
        }
        isBalanced = false;
        recReBalance(root);
    }

    private void recReBalance(ThreadedBSTNode<T> node) {//TODO: make less inefficient
        if (isBalanced) {
            return; // imbalance has already been found in a different subtree
        }
        int balance = balanceFactor(node);
        if (balance < -1) { // too left heavy
            if (balanceFactor(node.getLeft()) <= 0) {
                rotateRight(node);
            } else {
                rotateLeft(node.getLeft());
                rotateRight(node);
            }
            isBalanced = true;
        } else if (balance > 1) { // too right heavy
            if (balanceFactor(node.getRight()) <= 0) {
                rotateLeft(node);
            } else {
                rotateRight(node.getRight());
                rotateLeft(node);
            }
            isBalanced = true;
        } else {
            // check if subtrees are balanced
            if (node.getLeft() != null) recReBalance(node.getLeft());
            if (node.getRight() != null) recReBalance(node.getRight());
        }
    }

    private void rotateLeft(ThreadedBSTNode<T> node) {
        ThreadedBSTNode<T> rightChild = node.getRight();
        ThreadedBSTNode<T> nodeCopy = new ThreadedBSTNode<>(node.getInfo());
        nodeCopy.setLeft(node.getLeft());
        nodeCopy.setRight(rightChild.getLeft());
        node.setLeft(nodeCopy);
        node.setInfo(rightChild.getInfo());
        node.setRight(rightChild.getRight());
    }

    private void rotateRight(ThreadedBSTNode<T> node) {
        ThreadedBSTNode<T> leftChild = node.getLeft();
        ThreadedBSTNode<T> nodeCopy = new ThreadedBSTNode<>(node.getInfo());
        nodeCopy.setRight(node.getRight());
        nodeCopy.setLeft(leftChild.getRight());
        node.setRight(nodeCopy);
        node.setInfo(leftChild.getInfo());
        node.setLeft(leftChild.getLeft());
    }

    private int balanceFactor(ThreadedBSTNode<T> node) {
        return height(node.getRight()) - height(node.getLeft());
    }

    private int height(ThreadedBSTNode<T> node) {
        int maxDepth = 0;
        Stack<ThreadedBSTNode<T>> stack = new Stack<>();
        stack.push(node);
        int depth = 1;
        int branchDepth = 1;
        while (!stack.empty()) {
            node = stack.pop();
            if (node.getLeft() == null && node.getRight() == null) {
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
                depth -= branchDepth;
                branchDepth = 1;
            } else {
                if (node.getLeft() == null || node.getRight() == null) {
                    branchDepth++;
                }
                if (node.getRight() != null){
                    stack.push(node.getRight());
                    depth++;
                }
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                    depth++;
                }
            }
        }
        return maxDepth;
    }
}
