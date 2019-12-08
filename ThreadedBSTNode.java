package FinalProject;

import bookFiles.support.BSTNode;

public class ThreadedBSTNode<T> extends BSTNode<T> {
    public boolean hasThread;

    private ThreadedBSTNode<T> left;
    private ThreadedBSTNode<T> right;

    public ThreadedBSTNode(T info) {
        super(info);
    }

    public void setLeft(ThreadedBSTNode<T> link) {
        left = link;
    }

    public void setRight(ThreadedBSTNode<T> link) {
        right = link;
    }
}
