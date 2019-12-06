import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import ch07.trees.BSTInterface;
import ch05.collections.LinkedCollection;
import ch02.stacks.LinkedStack;
import support.BSTNode; 

public class AVLThreadedBST<T> implements BSTInterface<T>
{
   protected BSTNode<T> root;      // reference to the root of this BST
   protected Comparator<T> comp;   // used for all comparisons
   protected boolean found;   // used by remove
   protected int numElements;
   
   
   public AVLThreadedBST() 
   // Precondition: T implements Comparable
   // Creates an empty BST object - uses the natural order of elements.
   {
      root = null;
      numElements = 0;
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
    numElements = 0;
    this.comp = comp;
  }
   public void Balance()
   //Recursively goes through the tree and calls RotateLeft and RotateRight to turn the tree into a complete tree.
   {
      //TODO: MAKE THIS
   }
   
   protected void RotateLeft(BSTNode<T> node)
   //Given a node, rotates the subtree such that the node's right link becomes the root, and links to the node.
   {
      BSTNode<T> prenode = getPredecessor(node);
      if (prenode.getLeft() == node) {prenode.setLeft(node.getLeft());}
      else {prenode.setRight(node.getLeft());}
      node.setLeft(null);
      //No need to link the left link to the node, because threading already does that
   }
   
   protected void RotateRight(BSTNode<T> node)
   //Given a node, rotates the subtree such that the node's left link becomes the root, and links to the node.
   {
      BSTNode<T> prenode = getPredecessor(node);
      if (prenode.getLeft() == node) {prenode.setLeft(node.getRight());}
      else {prenode.setRight(node.getRight());}
      node.getRight().setLeft(node);
      node.setRight(null);
   }
   
   protected BSTNode<T> getPredecessor(BSTNode<T> node)
   //Gets the immediate predecessor of the node
   {
      if (isEmpty()) {return null;}
      BSTNode<T> search = root;
      do
      {
         if (search.getLeft() == node || search.getRight() == node) {return search;}
         if (comp.compare(node.getInfo(), search.getInfo()) <= 0) {search = search.getLeft();}
         else {search = search.getRight();}
      }
      while (search != null && (search.getLeft() != null || search.getRight() != null));
      return null;
      
   }
   
   
   public boolean add(T element)
   {
      boolean leftNode = true;
      if (isFull()) {return false;}
      if (root == null)
      {
         BSTNode<T> node = new BSTNode<T>(element);
         root = node;
         numElements++;
         return true;
      }
      LinkedCollection<BSTNode<T>> link = new LinkedCollection();
      LinkedStack<BSTNode<T>> stackRight = new LinkedStack<>();
      BSTNode<T> node = root;
      BSTNode<T> previousnode = root;
      //Goes through the tree until a suitable place for the newNode is found
      while (node != null)
      {
         link.add(node);
         previousnode = node;
         if (comp.compare(element, node.getInfo()) <= 0)
         {
            node = node.getLeft();
            leftNode = true;
            stackRight.push(previousnode);

         }
         else if (!link.contains(node.getRight()))
         {
            node = node.getRight();  
            leftNode = false;
         }
         else
         {
            node = null;  
            leftNode = false;
         }
      }
      node = new BSTNode<T>(element);
      //Adds the node to the tree
      if (leftNode) {previousnode.setLeft(node);}
      else {previousnode.setRight(node);}
      //Adds the thread to the node
      if (!stackRight.isEmpty()) {node.setRight(stackRight.top());}
      numElements++;
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
      return numElements;
   }
   
   protected BSTNode<T> getNode(T element)
   //Gets the node that has the information element
   {
      if (isEmpty()) {return null;}
      LinkedCollection<BSTNode<T>> link = new LinkedCollection();
      BSTNode<T> search = root;
      do
      {
         link.add(search);
         if (search.getInfo() == element) {return search;}
         if (comp.compare(element, search.getInfo()) <= 0) {search = search.getLeft();}
         else {search = search.getRight();}
      }
      while (search != null && (search.getLeft() != null || search.getRight() != null) && (!link.contains(search)));
      return null;
   }
   
   public T get(T element)
   {
      if (contains(element)) {return getNode(element).getInfo();}
      return null;
   }
   
   public boolean contains(T element)
   {
      return (getNode(element) != null);
   }
   
   public T min()
   // If this BST is empty, returns null;
   // otherwise returns the smallest element of the tree.
   {
      if (isEmpty()) {return null;}
      else
      {
         BSTNode<T> node = root;
         while (node.getLeft() != null) {node = node.getLeft();}
         return node.getInfo();
      }
   }
   
  public T max()
  // If this BST is empty, returns null;
  // otherwise returns the largest element of the tree.
  {
      if (isEmpty()) {return null;}
      else
      {
         BSTNode<T> node = root;
         while (node.getRight() != null) {node = node.getRight();}
         return node.getInfo();
      }
   }
   
   public boolean remove(T target)
   //If the node exists, removes it and places a similar node in the BST in its place
   {
      if (isEmpty()) {return false;}
      if (!contains(target)) {return false;}
      if (numElements == 1)
      {
         root = null;
         numElements--;
         return true;
      }
      BSTNode<T> removeNode = getNode(target);
      BSTNode<T> preNode = getPredecessor(removeNode);
      BSTNode<T> editNode = removeNode;
      //If the target has only a right link, removing it is easy, because threading isn't involved
      if (removeNode.getLeft() == null)
      {  
         if (removeNode != root)
         {
            if (preNode.getRight() == removeNode) {preNode.setRight(removeNode.getRight());}
            else if (removeNode.getRight() != preNode) {preNode.setLeft(removeNode.getRight());}
            else {preNode.setLeft(null);}
         }
         else {root = removeNode.getRight();}
      }
      else
      {
         //If the target has a left link, the threading needs to be removed before the node.
         //This is found at the max node of the left subtree.
         editNode = removeNode.getLeft();
         while (editNode.getRight() != removeNode)
         {
            editNode = editNode.getRight();
         }
         editNode.setRight(null);
         
         //if removeNode has a left link, but not a right, we can remove it easily, similarly to when it didn't have a left link
         if (removeNode.getRight() == null)
         {
            if (removeNode != root)
            {
               if (preNode.getRight() == removeNode) {preNode.setRight(removeNode.getRight());}
               else {preNode.setLeft(removeNode.getRight());}
            }
            else {root = removeNode.getLeft();}
         }
         else
         {
            //If removeNode has both a left and a right link, we'll need to find a node to replace it.
            //The best canidates are the max node of the left subtree, and the min node of the right subtree.
            //We already have the max node of the left subtree, but if we take from that too often we'll imbalance the subtree
            //Thus, we'll search for both, and replace removeNode with whichever one is found first.
            BSTNode<T> removeLeftNode = removeNode.getLeft();
            BSTNode<T> removeRightNode = removeNode.getRight();
            BSTNode<T> preRemoveLeftNode = removeNode.getLeft();
            BSTNode<T> preRemoveRightNode = removeNode.getRight();
            
            /*
            Iterates through both subtrees until either the max node of the left subtree
            or the min node of the right subtree is found
            */
            while (removeRightNode.getLeft() != null && removeLeftNode.getRight() != null)
            {
               preRemoveLeftNode = removeLeftNode;
               preRemoveRightNode = removeRightNode;
               removeLeftNode = removeLeftNode.getRight();
               removeRightNode = removeRightNode.getLeft();
            }
            //Once a max or min node is found, we have to determine which one was found.
            if (removeLeftNode.getRight() == null)
            {
               //If it's the max node that was found first, it replaces removeNode in the tree.
               //However, we need to generate new threading.
               //Since editNode is the replacement, we need to find the new max node in the subtree.
               if (removeLeftNode.getLeft() != null)
               {
                  preRemoveLeftNode.setRight(removeLeftNode.getLeft());
                  editNode = removeLeftNode.getLeft();
                  while (editNode.getRight() != null) {editNode = editNode.getRight();}
                  editNode.setRight(removeLeftNode);
               }
               
               //This code is what replaces removeNode with the replacement.
               if (preNode != null)
               {
                  if (preNode.getRight() == removeNode) {preNode.setRight(removeLeftNode);}
                  else {preNode.setLeft(removeLeftNode);}
               }
               removeLeftNode.setLeft(removeNode.getLeft());
               removeLeftNode.setRight(removeNode.getRight());
            }
            else
            {
               //If it's the min node that was found first, it replaces removeNode in the tree.
               //However, we need to generate new threading.
               //Since editNode is not the replacement, we can simply change editNode's threading to the replacement node.
               editNode.setRight(removeRightNode);
               preRemoveRightNode.setRight(removeRightNode.getRight());
               
               //This code is what replaces removeNode with replacement.
               if (preNode != null)
               {
                  if (preNode.getRight() == removeNode) {preNode.setRight(removeRightNode);}
                  else {preNode.setLeft(removeRightNode);}
               }
               removeRightNode.setLeft(removeNode.getLeft());
               removeRightNode.setRight(removeNode.getRight());
            }
         }
      }
      numElements--;
      return true;
   }
   
   public Iterator<T> iterator() {
        return getIterator(Traversal.Inorder);
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

}