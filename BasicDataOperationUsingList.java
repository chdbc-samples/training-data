import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцiй з даними типу String.
 * 
 * <p>Цей клас зчитує данi з файлу "String.data", сортує їх та виконує пошук значення в масивi та списку.</p>
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/String.data";

    String stringValueToSearch;
    String[] stringArray;
    List<String> stringList;

    public static void main(String[] args) {  
        BasicDataOperationUsingList basicDataOperationUsingList = new BasicDataOperationUsingList(args);
        basicDataOperationUsingList.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingList(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        stringValueToSearch = args[0];
        stringArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        stringList = new ArrayList<>(Arrays.asList(stringArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     */
    void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();

        sortArray();
        
        searchArray();
        findMinAndMaxInArray();

        // операцiї з ArrayList
        searchList();
        findMinAndMaxInList();

        sortList();

        findMinAndMaxInList();


        // записати вiдсортований масив у файл
        Utils.writeArrayToFile(stringArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв String та вимiрює час сортування.
     */
    void sortArray() {
        long startTime = System.nanoTime();
        Arrays.sort(stringArray);
        Utils.printOperationDuration(startTime, "сортування масиву String");
    }

    /**
     * Метод для пошуку значення в масивi String.
     */
    void searchArray() {
        long startTime = System.nanoTime();
        int index = Arrays.asList(stringArray).indexOf(stringValueToSearch);
        Utils.printOperationDuration(startTime, "пошук в масивi String");

        if (index >= 0) {
            System.out.println("Значення '" + stringValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + stringValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi String.
     */
    void findMinAndMaxInArray() {
        if (stringArray == null || stringArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        String min = stringArray[0];
        String max = stringArray[0];

        for (String str : stringArray) {
            if (str.compareTo(min) < 0) {
                min = str;
            }
            if (str.compareTo(max) > 0) {
                max = str;
            }
        }

        Utils.printOperationDuration(startTime, "пошук мiнiмального i максимального значення в масивi");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Шукає задане значення в ArrayList.
     */
    void searchList() {
        long startTime = System.nanoTime();
        int index = stringList.indexOf(stringValueToSearch);
        Utils.printOperationDuration(startTime, "пошук в ArrayList String");        

        if (index >= 0) {
            System.out.println("Значення '" + stringValueToSearch + "' знайдено в ArrayList за iндексом: " + index);
        } else {
            System.out.println("Значення '" + stringValueToSearch + "' в ArrayList не знайдено.");
        }
    }

 /**
     * Знаходить мiнiмальне та максимальне значення в ArrayList дати i часу.
     */
    void findMinAndMaxInList() {
        if (stringList == null || stringList.isEmpty()) {
            System.out.println("ArrayList порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        String min = Collections.min(stringList);
        String max = Collections.max(stringList);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної  в ArrayList");

        System.out.println("Мiнiмальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);
    }

    /**
     * Сортує ArrayList об'єктiв String та вимiрює час сортування.
     */
    void sortList() {
        long startTime = System.nanoTime();
        Collections.sort(stringList);
        Utils.printOperationDuration(startTime, "сортування ArrayList String");
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу String.
 */
class Utils {
    /**
     * Виводить час виконання операцiї в наносекундах.
     * 
     * @param startTime Час початку операцiї в наносекундах.
     * @param operationName Назва операцiї.
     */
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв String з файлу.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв String.
     */
    static String[] readArrayFromFile(String pathToFile) {
        ArrayList<String> tempList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                tempList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempList.toArray(new String[0]);
    }

    /**
     * Записує масив об'єктiв String у файл.
     * 
     * @param stringArray Масив об'єктiв String.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(String[] stringArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (String line : stringArray) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
