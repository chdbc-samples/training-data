import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцiй з даними типу Byte.
 * 
 * <p>Цей клас зчитує данi з файлу "list/byte.data", сортує їх та виконує пошук значення в масивi та множинi.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив Byte.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi Byte.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi Byte.</li>
 *   <li>{@link #searchSet()} - Виконує пошук значення в множинi Byte.</li>
 *   <li>{@link #findMinAndMaxInSet()} - Знаходить мiнiмальне та максимальне значення в множинi Byte.</li>
 *   <li>{@link #compareArrayAndSet()} - Порiвнює елементи масиву та множини.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingSet(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #byteToSearch} - Значення Byte для пошуку.</li>
 *   <li>{@link #byteArray} - Масив Byte.</li>
 *   <li>{@link #byteSet} - Множина Byte.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingSet "78"
 * }
 * </pre>
 */
public class BasicDataOperationUsingSet {
    static final String PATH_TO_DATA_FILE = "list/byte.data";

    Byte byteToSearch;
    Byte[] byteArray;
    Set<Byte> byteSet = new HashSet<>();

    public static void main(String[] args) {
        BasicDataOperationUsingSet basicDataOperationUsingSet = new BasicDataOperationUsingSet(args);
        basicDataOperationUsingSet.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingSet(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        this.byteToSearch = Byte.parseByte(args[0]);

        byteArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        byteSet = new HashSet<>(Arrays.asList(byteArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     * 
     * Метод зчитує масив та множину об'єктiв Byte з файлу, сортує їх та виконує пошук значення.
     */
    private void doDataOperation() {
        // операцiї з масивом чисе
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        // операцiї з HashSet чисе
        searchSet();
        findMinAndMaxInSet();
        compareArrayAndSet();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(byteArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв Byte та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(byteArray);

        Utils.printOperationDuration(startTime, "сортування масиву чисел");
    }

    /**
     * Метод для пошуку значення в масивi чисел.
     */
    private void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.byteArray, byteToSearch);

        Utils.printOperationDuration(startTime, "пошук в масивi чисел");

        if (index >= 0) {
            System.out.println("Значення '" + byteToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + byteToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi Byte.
     */
    private void findMinAndMaxInArray() {
        if (byteArray == null || byteArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Byte min = byteArray[0];
        Byte max = byteArray[0];

        for (Byte dateTime : byteArray) {
            if (dateTime < min) {
                min = dateTime;
            }
            if (dateTime > max) {
                max = dateTime;
            }
        }

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної чисел в масивi");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Метод для пошуку значення в множинi чисел.
     */
    private void searchSet() {
        long startTime = System.nanoTime();

        boolean isFound = this.byteSet.contains(byteToSearch);

        Utils.printOperationDuration(startTime, "пошук в HashSet чисел");

        if (isFound) {
            System.out.println("Значення '" + byteToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Значення '" + byteToSearch + "' в HashSet не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в множинi Byte.
     */
    private void findMinAndMaxInSet() {
        if (byteSet == null || byteSet.isEmpty()) {
            System.out.println("HashSet порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Byte min = Collections.min(byteSet);
        Byte max = Collections.max(byteSet);

        Utils.printOperationDuration(startTime, "пошук мiнiмального i максимального числа в HashSet");

        System.out.println("Мiнiмальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);
    }

    /**
     * Порiвнює елементи масиву та множини.
     */
    private void compareArrayAndSet() {
        System.out.println("кількість елементів в масиві: " + byteArray.length);
        System.out.println("Кількість елементів в HashSet: " + byteSet.size());

        boolean allElementsMatch = true;
        for (Byte dateTime : byteArray) {
            if (!byteSet.contains(dateTime)) {
                allElementsMatch = false;
                break;
            }
        }

        if (allElementsMatch) {
            System.out.println("всі елементи масиву присутні в HashSet.");
        } else {
            System.out.println("не всі елементи масиву присутні в HashSet.");
        }
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу Byte.
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
        System.out.println("\n>>>>>>>>>> Час виконання операції'" + operationName + "': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв Byte з файлу.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв Byte.
     */
    static Byte[] readArrayFromFile(String pathToFile) {
        Byte[] tempArray = new Byte[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Byte dateTime = Byte.parseByte(line);
                tempArray[index++] = dateTime;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Byte[] finalArray = new Byte[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
    }

    /**
     * Записує масив об'єктiв Byte у файл.
     * 
     * @param dateTimeArray Масив об'єктiв Byte.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(Byte[] dateTimeArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Byte dateTime : dateTimeArray) {
                writer.write(dateTime.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}