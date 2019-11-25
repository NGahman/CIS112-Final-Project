import ch07.trees.BinarySearchTree;
import ch02.stacks.LinkedStack;
import support.BSTNode; 
public class AVLThreadedBST<T> extends BinarySearchTree<T>
{
   protected boolean leftNode;
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
   @Override
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
}