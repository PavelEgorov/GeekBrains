package pegorov.lesson3;

import java.util.TreeMap;

public class Lesson3 {
    public static void main(String[] args) {

        String[] world = new String[10];
        world[0] = "Word 1";
        world[1] = "123";
        world[2] = "qwerty";
        world[3] = "zxcre";
        world[4] = "zxcre";
        world[5] = "124";
        world[6] = "123";
        world[7] = "qwerty";
        world[8] = "Word 1";
        world[9] = "qwerti";

        task1(world);

        task2();
    }

    public static void task1(String[] world){
        /*
        1	Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
        Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
        Посчитать, сколько раз встречается каждое слово
         */

        TreeMap<String, Integer> treeMap = new TreeMap<>();

        for (int i = 0; i< world.length; i++){
            if (treeMap.containsKey(world[i])){
                treeMap.put(world[i], treeMap.get(world[i])+1);
            }else{
                treeMap.put(world[i], 1);
            }
        }

        System.out.println(treeMap);
    }

    public static void task2(){
        /*
        2	Написать простой класс Телефонный Справочник, который хранит в себе список фамилий и телефонных номеров.
        В этот телефонный справочник с помощью метода add() можно добавлять записи, а с помощью метода get() искать номер телефона по фамилии.
        Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
        тогда при запросе такой фамилии должны выводиться все телефоны
         */

        PhoneList phoneList = new PhoneList();

        phoneList.add("Vasya", "79181992345");
        phoneList.add("Vasya", "79181991245");
        phoneList.add("Vasya", "79181292345");
        phoneList.add("Pasha", "79181992345");
        phoneList.add("Vova", "79181592314");
        phoneList.add("Pasha", "79181992317");
        phoneList.add("Katya", "79181163084");

        System.out.println(phoneList.get("Vasya"));
        System.out.println(phoneList.get("Kolya"));
        System.out.println(phoneList.get("Pasha"));
        System.out.println(phoneList.get("Vova"));
        System.out.println(phoneList.get("Katya"));
    }
}
