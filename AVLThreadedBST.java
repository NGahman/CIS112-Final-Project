import ch07.trees.BinarySearchTree;
import ch02.stacks.LinkedStack;
import support.BSTNode; 
public class AVLThreadedBST<T> extends BinarySearchTree<T>
{
   protected boolean leftNode;
   protected boolean leftSubTree;
   public void Balance()
   {
      //TODO: MAKE THIS
   }
   
   protected void RotateLeft()
   {
      //TODO: MAKE THIS
   }
   protected void RotateRight()
   {
      //TODO: MAKE THIS
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
      LinkedStack<BSTNode<T>> stack = new LinkedStack<>();
      BSTNode<T> node = root;
      BSTNode<T> previousnode = root;
      while (node != null)
      {
         previousnode = node;
         if (comp.compare(element, node.getInfo()) <= 0)
         {
            node = node.getLeft();
            leftNode = true;
            if (previousnode == root)
            {
               leftSubTree = true;
            }
         }
         else
         {
            node = node.getRight();  
            leftNode = false;
            stack.push(previousnode);
            if (previousnode == root)
            {
               leftSubTree = false;
            }
         }
      }
      if (leftNode)
      {
         previousnode.setLeft(node);
         node.setRight(previousnode);
         if (!stack.isEmpty())
         node.setLeft(stack.top());
      }
      else
      {
         previousnode.setRight(node);
         node.setLeft(previousnode);
         if (leftSubTree)
         {
            node.setRight(root);
         }
      }
      return true;
   }
}