package pegorov.lesson2;

public class MyArraySizeException extends IndexOutOfBoundsException {
    public MyArraySizeException(){
        super("Массив не формата 4х4");
    }
}
