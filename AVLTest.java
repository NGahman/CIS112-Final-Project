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
      
      System.out.println(avl.get(3));
      System.out.println(avl.get(6));
      System.out.println(avl.min());
      System.out.println(avl.max());
      System.out.println(avl.size());
      System.out.println();
      
      for (Integer s : avl) {
         System.out.println(s);
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