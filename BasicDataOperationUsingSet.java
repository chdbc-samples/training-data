import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцій з даними типу long.
 */
public class BasicDataOperationUsingSet {

    static final String PATH_TO_DATA_FILE = "list/int.data";  // Шлях до файлу з даними
    private int intValueToSearch;  // Значення для пошуку
    private long[] longArray;  // Масив для зберігання значень типу long
    private Set<Long> longSet = new HashSet<>();  // Колекція HashSet для значень типу long

    public static void main(String[] args) {
        BasicDataOperationUsingSet operation = new BasicDataOperationUsingSet();
        operation.parseArgs(args);
        operation.doDataOperation();
    }

    /**
     * Метод для розбору аргументів командного рядка.
     *
     * @param args Аргументи командного рядка.
     */
    private void parseArgs(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку.");
        }

        try {
            this.intValueToSearch = Integer.parseInt(args[0]);  // Перетворення першого аргументу в int
        } catch (NumberFormatException e) {
            throw new RuntimeException("Невірне значення для пошуку: " + args[0]);
        }

        // Зчитуємо масив з файлу
        this.longArray = readArrayFromFile(PATH_TO_DATA_FILE);

        // Записуємо елементи в HashSet
        for (long value : longArray) {
            longSet.add(value);
        }
    }

    /**
     * Виконує основні операції з даними.
     */
    private void doDataOperation() {
        // Операції з масивом long
        searchArray();
        findMinAndMaxInArray();
        sortArray();
        searchArray();
        findMinAndMaxInArray();

        // Операції з HashSet long
        searchSet();
        findMinAndMaxInSet();
        compareArrayAndSet();

        // Запис відсортованого масиву в окремий файл
        writeArrayToFile(longArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив long та виводить час, витрачений на сортування.
     */
    private void sortArray() {
        long startTime = System.nanoTime();
        Arrays.sort(longArray);  // Сортуємо масив
        printOperationDuration(startTime, "сортування масиву long");
    }

    /**
     * Пошук значення в масиві long.
     */
    private void searchArray() {
        long startTime = System.nanoTime();
        int index = Arrays.binarySearch(longArray, intValueToSearch);  // Пошук за допомогою binarySearch
        printOperationDuration(startTime, "пошук в масивi long");

        if (index >= 0) {
            System.out.println("Значення '" + intValueToSearch + "' знайдено в масивi за індексом: " + index);
        } else {
            System.out.println("Значення '" + intValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мінімальне та максимальне значення в масиві long.
     */
    private void findMinAndMaxInArray() {
        if (longArray == null || longArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();
        long min = Arrays.stream(longArray).min().orElseThrow();
        long max = Arrays.stream(longArray).max().orElseThrow();
        printOperationDuration(startTime, "пошук мінімального і максимального значення в масиві");

        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);
    }

    /**
     * Пошук значення в HashSet long.
     */
    private void searchSet() {
        long startTime = System.nanoTime();
        boolean isFound = longSet.contains((long) intValueToSearch);
        printOperationDuration(startTime, "пошук в HashSet long");

        if (isFound) {
            System.out.println("Значення '" + intValueToSearch + "' знайдено в HashSet.");
        } else {
            System.out.println("Значення '" + intValueToSearch + "' в HashSet не знайдено.");
        }
    }

    /**
     * Знаходить мінімальне та максимальне значення в HashSet long.
     */
    private void findMinAndMaxInSet() {
        if (longSet.isEmpty()) {
            System.out.println("HashSet порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();
        long min = longSet.stream().min(Long::compare).orElseThrow();
        long max = longSet.stream().max(Long::compare).orElseThrow();
        printOperationDuration(startTime, "пошук мінімального і максимального значення в HashSet");

        System.out.println("Мінімальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);
    }

    /**
     * Порівнює елементи масиву та множини.
     */
    private void compareArrayAndSet() {
        System.out.println("Кількість елементів в масиві: " + longArray.length);
        System.out.println("Кількість елементів в HashSet: " + longSet.size());

        boolean allElementsMatch = true;
        for (long value : longArray) {
            if (!longSet.contains(value)) {
                allElementsMatch = false;
                break;
            }
        }

        if (allElementsMatch) {
            System.out.println("Всі елементи масиву присутні в HashSet.");
        } else {
            System.out.println("Не всі елементи масиву присутні в HashSet.");
        }
    }

    /**
     * Зчитує масив long з файлу.
     */
    private long[] readArrayFromFile(String pathToFile) {
        long[] tempArray = new long[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    tempArray[index++] = Long.parseLong(line);  // Перетворення рядка в long
                } catch (NumberFormatException e) {
                    System.out.println("Попередження: не вдалося перетворити '" + line + "' на long.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Arrays.copyOf(tempArray, index);  // Повертаємо масив
    }

    /**
     * Записує масив long в файл.
     */
    private void writeArrayToFile(long[] longArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (long value : longArray) {
                writer.write(Long.toString(value));
                writer.newLine();
            }
            System.out.println("Відсортовані дані записані у файл: " + pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Виводить час виконання операції в наносекундах.
     *
     * @param startTime Час початку операції в наносекундах.
     * @param operationName Назва операції.
     */
    private void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>>> Час виконання операції '" + operationName + "': " + duration + " наносекунд");
    }
}
