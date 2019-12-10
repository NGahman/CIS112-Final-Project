//This project was done by Nicholas Gahman and Corey Shive.
package FinalProject;

import java.util.*;

import ch07.trees.BSTInterface;
import ch02.stacks.LinkedStack;

public class AVLThreadedBST<T> implements BSTInterface<T>
{
   protected ThreadedBSTNode<T> root;      // reference to the root of this BST
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

   protected ThreadedBSTNode<T> getPredecessor(ThreadedBSTNode<T> node)
   //Gets the immediate predecessor of the node
   {
      if (isEmpty()) {return null;}
      ThreadedBSTNode<T> search = root;
      if (node == search) {return null;}
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
         ThreadedBSTNode<T> node = new ThreadedBSTNode<T>(element);
         root = node;
         numElements++;
         node.hasThread = false;
         return true;
      }
      LinkedStack<ThreadedBSTNode<T>> stackRight = new LinkedStack<>();
      ThreadedBSTNode<T> node = root;
      ThreadedBSTNode<T> previousnode = root;
      //Goes through the tree until a suitable place for the newNode is found
      while (node != null)
      {
         previousnode = node;
         if (comp.compare(element, node.getInfo()) <= 0)
         {
            node = node.getLeft();
            leftNode = true;
            stackRight.push(previousnode);

         }
         else
         {
            if (node.hasThread)
            {
               node = null;
               leftNode = false;
            }
            else
            {
               node = node.getRight();
               leftNode = false;
            }
         }
      }
      node = new ThreadedBSTNode<T>(element);
      //Adds the node to the tree
      if (leftNode) {previousnode.setLeft(node);}
      else
      {
         previousnode.setRight(node);
         previousnode.hasThread = false;
      }
      node.hasThread = false;
      //Adds the thread to the node
      if (!stackRight.isEmpty())
      {
         node.setRight(stackRight.top());
         node.hasThread = true;
      }
      numElements++;
      reBalance();
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

   protected ThreadedBSTNode<T> getNode(T element)
   //Gets the node that has the information element
   {
      if (isEmpty()) {return null;}
      ThreadedBSTNode<T> search = root;
      do
      {
         if (search.getInfo() == element) {return search;}
         if (comp.compare(element, search.getInfo()) <= 0) {search = search.getLeft();}
         else if (!search.hasThread) {search = search.getRight();}
         else {search = null;}
      }
      while (search != null);
      return null;
   }

   public T get(T element)
   {
      ThreadedBSTNode<T> node = getNode(element);
      if (node != null) {return node.getInfo();}
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
         ThreadedBSTNode<T> node = root;
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
         ThreadedBSTNode<T> node = root;
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
      ThreadedBSTNode<T> removeNode = getNode(target);
      ThreadedBSTNode<T> preNode = getPredecessor(removeNode);
      ThreadedBSTNode<T> editNode = removeNode;
      //If the target has only a right link, removing it is easy, because threading isn't involved
      if (removeNode.getLeft() == null)
      {
         if (removeNode != root)
         {
            if (preNode.getRight() == removeNode) {preNode.setRight(removeNode.getRight());}
            else if (removeNode.getRight() != preNode && removeNode.getRight() != null)
            {
               preNode.setLeft(removeNode.getRight());
               if (removeNode.getRight().getRight() == null)
               {
                  removeNode.getRight().hasThread = true;
                  removeNode.getRight().setRight(preNode);
               }
            }
            else {preNode.setLeft(null);}
         }
         else
         {
            root = removeNode.getRight();
            root.hasThread = false;
         }
      }
      else
      {
         //If the target has a left link, the threading needs to be removed before the node.
         //This is found at the max node of the left subtree.
         editNode = removeNode.getLeft();
         while (editNode.getRight() != removeNode  && editNode.getRight() != null)
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
            else 
            {
               root = removeNode.getLeft();
               if (root.hasThread == true)
               {
                  root.hasThread = false;
                  root.setRight(null);
               }
            }
         }
         else
         {
            //If removeNode has both a left and a right link, we'll need to find a node to replace it.
            //The best canidates are the max node of the left subtree, and the min node of the right subtree.
            //We already have the max node of the left subtree, but if we take from that too often we'll imbalance the subtree
            //Thus, we'll search for both, and replace removeNode with whichever one is found first.
            ThreadedBSTNode<T> removeLeftNode = removeNode.getLeft();
            ThreadedBSTNode<T> removeRightNode = removeNode.getRight();
            ThreadedBSTNode<T> preRemoveLeftNode = removeNode.getLeft();
            ThreadedBSTNode<T> preRemoveRightNode = removeNode.getRight();

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
                  editNode.hasThread = true;
               }
               else
               {
                  preRemoveLeftNode.hasThread = true;
               }

               //This code is what replaces removeNode with the replacement.
               if (preNode != null)
               {
                  if (preNode.getRight() == removeNode) {preNode.setRight(removeLeftNode);}
                  else {preNode.setLeft(removeLeftNode);}
               }
               if (removeNode.getLeft() != removeLeftNode) {removeLeftNode.setLeft(removeNode.getLeft());}
               else {removeLeftNode.setLeft(null);}
               removeLeftNode.setRight(removeNode.getRight());
               removeLeftNode.hasThread = removeNode.hasThread;
               
               if (removeNode == root) {root = removeLeftNode;}
            }
            else
            {
               //If it's the min node that was found first, it replaces removeNode in the tree.
               //However, we need to generate new threading.
               //Since editNode is not the replacement, we can simply change editNode's threading to the replacement node.
               editNode.setRight(removeRightNode);
               preRemoveRightNode.setRight(removeRightNode.getRight());
               editNode.hasThread = true;

               //This code is what replaces removeNode with replacement.
               if (preNode != null)
               {
                  if (preNode.getRight() == removeNode) {preNode.setRight(removeRightNode);}
                  else {preNode.setLeft(removeRightNode);}
               }
               removeRightNode.setLeft(removeNode.getLeft());
               if (removeNode.getRight() != removeRightNode) {removeRightNode.setRight(removeNode.getRight());}
               else {removeRightNode.setRight(null);}
               removeRightNode.hasThread = removeNode.hasThread;
               if (removeNode == root) {root = removeRightNode;}
            }
         }
      }
      numElements--;
      reBalance();
      return true;
   }

   //iterator

   public Iterator<T> iterator() {
        return getIterator(Traversal.Inorder);
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
            if (!node.hasThread) {preOrder(node.getRight(), q);}
        }
    }

    private void postOrder(ThreadedBSTNode<T> node, LinkedList<T> q) {
        if (node != null) {
            postOrder(node.getLeft(), q);
            if (!node.hasThread) {postOrder(node.getRight(), q);}
            q.add(node.getInfo());
        }
    }


    //reBalance

    private boolean isBalanced = true;

    protected void reBalance() {
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
            if (balanceFactor(node.getLeft()) > 0) {
                rotateRight(node);
            } else {
                rotateLeft(node.getLeft());
                rotateRight(node);
            }
            isBalanced = true;
        } else if (balance > 1) { // too right heavy
            if (balanceFactor(node.getRight()) > 0) {
                rotateLeft(node);
            } else {
                rotateRight(node.getRight());
                rotateLeft(node);
            }
            isBalanced = true;
        }
        // check if subtrees are balanced
        if (node.getLeft() != null) {recReBalance(node.getLeft());}
        if (node.getRight() != null  && !node.hasThread) {recReBalance(node.getRight());}

    }

    private void rotateLeft(ThreadedBSTNode<T> node) {
        ThreadedBSTNode<T> rightChild = node.getRight();
        ThreadedBSTNode<T> nodeCopy = new ThreadedBSTNode<>(node.getInfo());
        nodeCopy.setLeft(node.getLeft());
        if (rightChild.getLeft() != null) {
            nodeCopy.setRight(rightChild.getLeft());
        } else {
            nodeCopy.setRight(node);
            nodeCopy.hasThread = true;
        }
        node.setLeft(nodeCopy);
        node.setInfo(rightChild.getInfo());
        node.setRight(rightChild.getRight());
    }

    private void rotateRight(ThreadedBSTNode<T> node) {
        ThreadedBSTNode<T> leftChild = node.getLeft();
        ThreadedBSTNode<T> nodeCopy = new ThreadedBSTNode<>(node.getInfo());
        nodeCopy.setRight(node.getRight());
        if (!leftChild.hasThread) {nodeCopy.setLeft(leftChild.getRight());}
        node.setRight(nodeCopy);
        node.setInfo(leftChild.getInfo());
        node.setLeft(leftChild.getLeft());
    }

    protected int balanceFactor(ThreadedBSTNode<T> node) {
        if (node.hasThread) {
            return -height(node.getLeft());
        }
        return height(node.getRight()) - height(node.getLeft());
    }

    private int height(ThreadedBSTNode<T> node) {
        if (node == null) {
            return 0;
        }
        ThreadedBSTNode<T> left = node.getLeft();
        ThreadedBSTNode<T> right;
        if (node.hasThread) {
            right = null;
        } else {
            right = node.getRight();
        }
        if (left == null && right == null) {
            return 1;
        }
        if (left == null) {
            return height(right)+1;
        }
        if (right == null) {
            return height(left)+1;
        }
        int lh = height(left);
        int rh = height(right);
        if (lh > rh) {
            return lh+1;
        } else {
            return rh+1;
        }
    }

}
