package pegorov.lesson2;

import java.util.Scanner;

public class Lesson2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите число строк: ");
        int i = scanner.nextInt();
        System.out.println("Введите число колонок: ");
        int j = scanner.nextInt();

        String[][] str = new String[i][j];
        for (int i_ = 0; i_ < i; i_++) {
            for (int j_ = 0; j_ < j; j_++) {
                System.out.println("Введите строку[" + i_ + "," + j_ + "]: ");
                str[i_][j_] = scanner.next();
            }
        }
        getArraySumm(str);
    }

    private static void getArraySumm(String[][] str) {
        if (str.length != 4) {
            throw new MyArraySizeException();
        }
        for (int i = 0; i < str.length; i++) {
            if (str[i].length != 4) {
                throw new MyArraySizeException();
            }
        }

        int sum = 0;
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str[i].length; j++) {
                inputDate(str[i][j], i, j);
                sum += Integer.parseInt(str[i][j]);
            }
        }
        System.out.println("Результат: ");
        System.out.println(sum);
    }

    private static void inputDate(String str, int x, int y) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                throw new MyArrayDataException(x, y);
            }
        }
    }
}
