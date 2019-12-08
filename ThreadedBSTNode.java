package FinalProject;

import bookFiles.support.BSTNode;

public class ThreadedBSTNode<T> extends BSTNode<T> {
    public boolean hasThread;

    public ThreadedBSTNode(T info) {
        super(info);
    }
}
