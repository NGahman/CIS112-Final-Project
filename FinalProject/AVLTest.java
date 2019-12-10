import java.util.*;
import ch07.trees.BSTInterface.Traversal;
<<<<<<< Updated upstream:AVLTest.java
=======

public class AVLTest {
    public static void main(String[] args) {
        AVLThreadedBST<Integer> avl = new AVLThreadedBST<>();
        avl.add(1);
        avl.add(2);
        avl.add(3);
        printTree(avl);
        avl.add(4);
        printTree(avl);
        avl.add(5);
        printTree(avl);
        avl.add(6);
        avl.add(7);
        avl.add(8);
        avl.add(9);
        avl.add(10);
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
>>>>>>> Stashed changes:FinalProject/AVLTest.java

public class AVLTest
{
   public static void main(String[] args)
   {
      AVLThreadedBST<Integer> avl = new AVLThreadedBST();
      System.out.println(avl.add(1));
      System.out.println(avl.add(2));
      System.out.println(avl.add(3));
      System.out.println(avl.add(4));
      System.out.println(avl.add(5));
      System.out.println(avl.add(6));
      System.out.println(avl.add(7));
      System.out.println(avl.add(8));
      System.out.println(avl.add(9));
      System.out.println(avl.add(10));
      System.out.println();
      for (int i = 1; i < 11; i++)
      {
         System.out.println(avl.get(i));
      }
      System.out.println();
      //System.out.println(avl.min());
      //System.out.println(avl.max());
      //System.out.println(avl.size());
      //System.out.println();
      //avl.reBalance();
      System.out.println();
      Iterator<Integer> i = avl.getIterator(Traversal.Inorder);
      
      while (i.hasNext())
      {
         System.out.println(i.next());
      }
      
      System.out.println();
      i = avl.getIterator(Traversal.Postorder);
      
      while (i.hasNext())
      {
         System.out.println(i.next());
      }
      
      System.out.println();
      i = avl.getIterator(Traversal.Preorder);
      
      while (i.hasNext())
      {
         System.out.println(i.next());
      }
      
      System.out.println();
      System.out.println(avl.get(4));
      System.out.println(avl.remove(4));
      System.out.println(avl.get(4));
      System.out.println(avl.get(3));
      System.out.println(avl.size());
      System.out.println(avl.isEmpty());
      System.out.println(avl.remove(6));
      
   }
}