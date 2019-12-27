import java.util.Scanner;

public class Launch {
    public static void main(String[] args) {
       int value;
       int prior;
       int choiceGlob;
       Scanner scan = new Scanner(System.in);
       Treap<Integer> treap = new Treap<>();
       Visual a = new Visual(treap);
       a.pack();
       a.setVisible(false);

       do {
           System.out.println("Нажмите, чтобы:");
           System.out.println("1 - добавить элемент по значению (приоритет выберется рандомно);");
           System.out.println("2 - добавить элемена по значению и приоритету;");
           System.out.println("3 - удалить элемент по значению.");

           int choice = scan.nextInt();
           switch (choice) {
               case 1:
                   System.out.println("Введите целое число - значение. Приоритет будет подобран рандомно.");
                   value = scan.nextInt();
                   treap.add(value);
                   break;
               case 2:
                   System.out.println("Введите целое число - значение.");
                   value = scan.nextInt();
                   System.out.println("Введите целое число - приоритет.");
                   prior = scan.nextInt();
                   treap.add(value, prior);
                   break;
               case 3:
                   System.out.println("Введите целое число - значение. Это значение будет удалено из дерева.");
                   value = scan.nextInt();
                   treap.remove(value);
                   break;
               default:
                   System.out.println("Такой операции не существует.");
                   break;
           }

           if (choice == 1 || choice == 2 || choice ==3) {
               a.update(treap);
               System.out.println("Дерево обновлено.");
           }

           System.out.println("Продолжить? 1 - да; 2 - нет.");
           choiceGlob = scan.nextInt();
           } while (choiceGlob == 1);
       }
    }
