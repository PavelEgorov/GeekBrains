package pegorov.lesson2;

public class MyArrayDataException extends NumberFormatException {
    public MyArrayDataException(int i, int j){
        super("Не удалось преобразовать элемент массива к числу (" + i + "," + j + ")");
    }
}
