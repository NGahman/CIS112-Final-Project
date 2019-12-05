public class AVLTest
{
   public static void main(String[] args)
   {
      AVLThreadedBST<String> avl = new AVLThreadedBST();
      System.out.println(avl.add("Hello!"));
      System.out.println(avl.add("H"));
      System.out.println(avl.get("H"));
      System.out.println(avl.get("Hello!"));
      System.out.println(avl.min());
      System.out.println(avl.max());
      System.out.println(avl.size());
      System.out.println(avl.remove("H"));
      System.out.println(avl.get("H"));
      System.out.println(avl.get("Hello!"));
      System.out.println(avl.size());
      System.out.println(avl.isEmpty());
      System.out.println(avl.remove("Hello!"));
   }
}