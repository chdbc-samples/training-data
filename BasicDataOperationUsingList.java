import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцiй з даними типу Short.
 * 
 * <p>Цей клас зчитує данi з файлу "list/Short.data", сортує їх та виконує пошук значення в масивi та списку.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив Short.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi Short.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi Short.</li>
 *   <li>{@link #sortList()} - Сортує список Short.</li>
 *   <li>{@link #searchList()} - Виконує пошук значення в списку Short.</li>
 *   <li>{@link #findMinAndMaxInList()} - Знаходить мiнiмальне та максимальне значення в списку Short.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingList(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #shortValueToSearch} - Значення Short для пошуку.</li>
 *   <li>{@link #shortArray} - Масив Short.</li>
 *   <li>{@link #shortList} - Список Short.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingList "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/short.data";

    Short shortValueToSearch;
    Short[] shortArray;
    List<Short> shortList;

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

        String searchValue = args[0];

        shortValueToSearch = Short.parseShort(searchValue);

        shortArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        shortList = new LinkedList<>(Arrays.asList(shortArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     * 
     * Метод зчитує масив та список об'єктiв Short з файлу, сортує їх та виконує пошук значення.
     */
    void doDataOperation() {
        // операцiї з масивом чисел
        searchArray();
        findMinAndMaxInArray();

        sortArray();
        
        searchArray();
        findMinAndMaxInArray();

        // операцiї з LinkedList
        searchList();
        findMinAndMaxInList();

        sortList();

        searchList();
        findMinAndMaxInList();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(shortArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв Short та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(shortArray);

        Utils.printOperationDuration(startTime, "сортування масиву чисел");
    }

    /**
     * Метод для пошуку значення в масивi чисел.
     */
    void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.shortArray, shortValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масивi чисел");

        if (index >= 0) {
            System.out.println("Значення '" + shortValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + shortValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi чисел.
     */
    void findMinAndMaxInArray() {
        if (shortArray == null || shortArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Short min = shortArray[0];
        Short max = shortArray[0];

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної чисел в масивi");

        for (Short shortValue : shortArray) {
            if (shortValue < min) {
                min = shortValue;
            }
            if (shortValue > max) {
                max = shortValue;
            }
        }

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Шукає задане значення чисел в LinkedList чисел.
     */
    void searchList() {
        long startTime = System.nanoTime();

        int index = Collections.binarySearch(this.shortList, shortValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в LinkedList чисел");        

        if (index >= 0) {
            System.out.println("Значення '" + shortValueToSearch + "' знайдено в LinkedList за iндексом: " + index);
        } else {
            System.out.println("Значення '" + shortValueToSearch + "' в LinkedList не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в LinkedList чисел.
     */
    void findMinAndMaxInList() {
        if (shortList == null || shortList.isEmpty()) {
            System.out.println("LinkedList порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Short min = Collections.min(shortList);
        Short max = Collections.max(shortList);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної чисел в LinkedList");

        System.out.println("Мiнiмальне значення в LinkedList: " + min);
        System.out.println("Максимальне значення в LinkedList: " + max);
    }

    /**
     * Сортує LinkedList об'єктiв Short та виводить початковий i вiдсортований списки.
     * Вимiрює та виводить час, витрачений на сортування списку в наносекундах.
     */
    void sortList() {
        long startTime = System.nanoTime();

        Collections.sort(shortList);

        Utils.printOperationDuration(startTime, "сортування LinkedList чисел");
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу Short.
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
     * Зчитує масив об'єктiв Short з файлу.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв Short.
     */
    static Short[] readArrayFromFile(String pathToFile) {
       
        Short[] tempArray = new Short[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Short shortValue = Short.parseShort(line);
                tempArray[index++] = shortValue;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Short[] finalArray = new Short[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
    }

    /**
     * Записує масив об'єктiв Short у файл.
     * 
     * @param shortArray Масив об'єктiв Short.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(Short[] shortArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Short shortValueShort : shortArray) {
                writer.write(shortValueShort.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}