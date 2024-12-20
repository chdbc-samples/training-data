import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцiй з даними типу LocalTime.
 * 
 * <p>Цей клас зчитує данi з файлу "list/LocalTime.data", сортує їх та виконує пошук значення в масивi та множинi.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив LocalTime.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi LocalTime.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi LocalTime.</li>
 *   <li>{@link #searchSet()} - Виконує пошук значення в множинi LocalTime.</li>
 *   <li>{@link #findMinAndMaxInSet()} - Знаходить мiнiмальне та максимальне значення в множинi LocalTime.</li>
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
 *   <li>{@link #localTimeValueToSearch} - Значення LocalTime для пошуку.</li>
 *   <li>{@link #localTimeArray} - Масив LocalTime.</li>
 *   <li>{@link #localTimeSet} - Множина LocalTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingSet "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingSet {
    static final String PATH_TO_DATA_FILE = "list/LocalTime.data";

    LocalTime localTimeValueToSearch;
    LocalTime[] localTimeArray;
    Set<LocalTime> localTimeSet = new HashSet<>();

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

        String valueToSearch = args[0];
        this.localTimeValueToSearch = LocalTime.parse(valueToSearch, DateTimeFormatter.ISO_TIME);

        localTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        localTimeSet = new HashSet<>(Arrays.asList(localTimeArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     * 
     * Метод зчитує масив та множину об'єктiв LocalTime з файлу, сортує їх та виконує пошук значення.
     */
    private void doDataOperation() {
        // операцiї з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        // операцiї з HashSet дати та часу
        searchSet();
        findMinAndMaxInSet();
        compareArrayAndSet();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(localTimeArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв LocalTime та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(localTimeArray);

        Utils.printOperationDuration(startTime, "сортування масиву часу");
    }

    /**
     * Метод для пошуку значення в масивi часу.
     */
    private void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.localTimeArray, localTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масивi часу");

        if (index >= 0) {
            System.out.println("Значення '" + localTimeValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + localTimeValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi LocalTime.
     */
    private void findMinAndMaxInArray() {
        if (localTimeArray == null || localTimeArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalTime min = localTimeArray[0];
        LocalTime max = localTimeArray[0];

        for (LocalTime localTimeValue : localTimeArray) {
            if (localTimeValue.isBefore(min)) {
                min = localTimeValue;
            }
            if (localTimeValue.isAfter(max)) {
                max = localTimeValue;
            }
        }

        Utils.printOperationDuration(startTime, "пошук мiнiмального i максимального часу в масивi");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Метод для пошуку значення в множинi часу.
     */
    private void searchSet() {
        long startTime = System.nanoTime();

        boolean isFound = this.localTimeSet.contains(localTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в HashSet часу");

        if (isFound) {
            System.out.println("Значення '" + localTimeValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Значення '" + localTimeValueToSearch + "' в HashSet не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в множинi LocalTime.
     */
    private void findMinAndMaxInSet() {
        if (localTimeSet == null || localTimeSet.isEmpty()) {
            System.out.println("HashSet порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalTime min = Collections.min(localTimeSet);
        LocalTime max = Collections.max(localTimeSet);

        Utils.printOperationDuration(startTime, "пошук мiнiмального i максимального часу в HashSet");

        System.out.println("Мiнiмальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);
    }

    private void compareArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + localTimeArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + localTimeSet.size());

        boolean allElementsMatch = true;
        for (LocalTime localTimeValue : localTimeArray) {
            if (!localTimeSet.contains(localTimeValue)) {
                allElementsMatch = false;
                break;
            }
        }

        if (allElementsMatch) {
            System.out.println("Всi елементи масиву присутнi в HashSet.");
        } else {
            System.out.println("Не всi елементи масиву присутнi в HashSet.");
        }
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу LocalTime.
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
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв LocalTime з файлу.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв LocalTime.
     */
    static LocalTime[] readArrayFromFile(String pathToFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;
        LocalTime[] tempArray = new LocalTime[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                LocalTime localTimeValue = LocalTime.parse(line, formatter);
                tempArray[index++] = localTimeValue;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalTime[] finalArray = new LocalTime[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
    }

    /**
     * Записує масив об'єктiв LocalTime у файл.
     * 
     * @param localTimeArray Масив об'єктiв LocalTime.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(LocalTime[] localTimeArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (LocalTime localTimeValue : localTimeArray) {
                writer.write(localTimeValue.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}