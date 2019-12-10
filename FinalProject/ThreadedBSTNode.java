<<<<<<< Updated upstream:ThreadedBSTNode.java
=======
package FinalProject;

>>>>>>> Stashed changes:FinalProject/ThreadedBSTNode.java
import support.BSTNode;

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

    public ThreadedBSTNode<T> getLeft() {
        return left;
    }

    public ThreadedBSTNode<T> getRight() {
        return right;
    }
}
