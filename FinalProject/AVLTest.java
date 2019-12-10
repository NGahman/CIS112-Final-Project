package FinalProject;

import java.util.*;
import ch07.trees.BSTInterface.Traversal;


public class AVLTest {
    public static void main(String[] args) {
        AVLThreadedBST<Integer> avl = new AVLThreadedBST<>();
        avl.add(1);
        avl.add(2);
        avl.add(3);
        avl.add(4);
        avl.add(5);
        avl.add(6);
        avl.add(7);
        avl.add(8);
        avl.add(9);
        avl.add(10);
        System.out.println(avl.get(11));
        System.out.println();
        printTree(avl);
        Iterator<Integer> iterator = avl.getIterator(Traversal.Preorder);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(avl.balanceFactor(avl.root));
    }

    private static void printTree(AVLThreadedBST avl) {
        printNode(avl.root);
        System.out.println();
        printNode(avl.root.getLeft());
        System.out.print(" ");
        printNode(avl.root.getRight());
        System.out.println();
        printNode(avl.root.getLeft().getLeft());
        System.out.print(" ");
        printNode(avl.root.getLeft().getRight());
        System.out.print(" | ");
        printNode(avl.root.getRight().getLeft());
        System.out.print(" ");
        printNode(avl.root.getRight().getRight());
        System.out.println();
    }


    private static void printNode(ThreadedBSTNode node) {
        if (node == null) {
            System.out.print("null");
            return;
        }
        System.out.print(node.getInfo());
        System.out.print(node.hasThread);
    }
}
