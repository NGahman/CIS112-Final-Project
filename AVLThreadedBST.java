import java.util.*;   // Iterator, Comparator
import ch07.trees.BSTInterface;
import ch05.collections.LinkedCollection;
import ch02.stacks.LinkedStack;
import support.BSTNode; 

public class AVLThreadedBST<T> implements BSTInterface<T>
{
   protected BSTNode<T> root;      // reference to the root of this BST
   protected Comparator<T> comp;   // used for all comparisons
   protected boolean leftNode;
   protected boolean found;   // used by remove

   public AVLThreadedBST() 
   // Precondition: T implements Comparable
   // Creates an empty BST object - uses the natural order of elements.
   {
      root = null;
      comp = new Comparator<T>()
      {
         public int compare(T element1, T element2)
         {
            return ((Comparable)element1).compareTo(element2);
         }
      };
   }
   
   public AVLThreadedBST(Comparator<T> comp) 
  // Creates an empty BST object - uses Comparator comp for order
  // of elements.
  {
    root = null;
    this.comp = comp;
  }
  
   public void Balance()
   {
      //TODO: MAKE THIS
   }
   
   protected void RotateLeft(BSTNode<T> node)
   {
      BSTNode<T> prenode = getPredecessor(node);
      if (prenode.getLeft() == node) {prenode.setLeft(node.getLeft());}
      else {prenode.setRight(node.getLeft());}
      node.setLeft(null);
   }
   protected void RotateRight(BSTNode<T> node)
   {
      BSTNode<T> prenode = getPredecessor(node);
      if (prenode.getLeft() == node) {prenode.setLeft(node.getLeft());}
      else {prenode.setRight(node.getLeft());}
      node.setRight(null);
   }
   
   protected BSTNode<T> getPredecessor(BSTNode<T> node)
   {
      if (isEmpty()) {return null;}
      BSTNode<T> search = root;
      do
      {
         if (search.getLeft() == node || search.getRight() == node) {return search;}
         if (comp.compare(node.getInfo(), search.getInfo()) <= 0) {search = search.getLeft();}
         else {search = search.getRight();}
      }
      while (search.getLeft() != null || search.getRight() != null);
      //If this is triggered, something went horribly wrong
      return null;
      
   }
   
   public boolean add(T element)
   {
      if (isFull()) {return false;}
      if (root == null)
      {
         BSTNode<T> node = new BSTNode<T>(element);
         root = node;
         return true;
      }
      LinkedStack<BSTNode<T>> stackRight = new LinkedStack<>();
      LinkedStack<BSTNode<T>> stackLeft = new LinkedStack<>();
      BSTNode<T> node = root;
      BSTNode<T> previousnode = root;
      while (node != null)
      {
         previousnode = node;
         if (comp.compare(element, node.getInfo()) <= 0)
         {
            node = node.getLeft();
            leftNode = true;
            stackLeft.push(previousnode);

         }
         else
         {
            node = node.getRight();  
            leftNode = false;
            stackRight.push(previousnode);
         }
      }
      if (leftNode)
      {
         previousnode.setLeft(node);
         node.setRight(previousnode);
         if (!stackRight.isEmpty()) {node.setLeft(stackRight.top());}
      }
      else
      {
         previousnode.setRight(node);
         node.setLeft(previousnode);
         if (!stackLeft.isEmpty()) {node.setRight(stackLeft.top());}
      }
      return true;
   }
   
   
   public boolean isFull()
   // Returns false; this link-based BST is never full.
   {
      return false;
   }

   public boolean isEmpty()
   // Returns true if this BST is empty; otherwise, returns false.
   {
      return (root == null);
   }
  
   public int size()
   // Returns the number of elements in this BST.
   {
      if (isEmpty()) {return 0;}
      int count = 0;
      BSTNode<T> node = root;
      LinkedCollection<BSTNode<T>> link = new LinkedCollection<>();
      while (node != null)
      {
         link.add(node);
         if (!link.contains(node)) {count++;}
         if (node.getLeft() != null && !link.contains(node.getLeft())) {node = node.getLeft();}
         else {node = node.getRight();}
      }
      return count;
   }
   
   public T get(T element)
   {
      if (isEmpty()) {return null;}
      BSTNode<T> search = root;
      do
      {
         if (search.getInfo() == element) {return search.getInfo();}
         if (comp.compare(element, search.getInfo()) <= 0) {search = search.getLeft();}
         else {search = search.getRight();}
      }
      while (search.getLeft() != null || search.getRight() != null);
      return null;
   }
}