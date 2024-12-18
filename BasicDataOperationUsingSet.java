import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцій з даними типу int.
 */
public class BasicDataOperationUsingSet {

    static final String PATH_TO_DATA_FILE = "list/int.data";  // Шлях до файлу з даними
    private int intValueToSearch;  // Значення для пошуку
    private int[] intArray;  // Масив для зберігання значень типу int
    private Set<Integer> intSet = new HashSet<>();  // Колекція HashSet для значень типу int

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
        this.intArray = readArrayFromFile(PATH_TO_DATA_FILE);

        // Записуємо елементи в HashSet
        for (int value : intArray) {
            intSet.add(value);
        }
    }

    /**
     * Виконує основні операції з даними.
     */
    private void doDataOperation() {
        // Операції з масивом int
        searchArray();
        findMinAndMaxInArray();
        sortArray();
        searchArray();
        findMinAndMaxInArray();

        // Операції з HashSet int
        searchSet();
        findMinAndMaxInSet();
        compareArrayAndSet();

        // Запис відсортованого масиву в окремий файл
        writeArrayToFile(intArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив int та виводить час, витрачений на сортування.
     */
    private void sortArray() {
        long startTime = System.nanoTime();
        Arrays.sort(intArray);  // Сортуємо масив
        printOperationDuration(startTime, "сортування масиву int");
    }

    /**
     * Пошук значення в масиві int.
     */
    private void searchArray() {
        long startTime = System.nanoTime();
        int index = Arrays.binarySearch(intArray, intValueToSearch);
        printOperationDuration(startTime, "пошук в масивi int");

        if (index >= 0) {
            System.out.println("Значення '" + intValueToSearch + "' знайдено в масивi за індексом: " + index);
        } else {
            System.out.println("Значення '" + intValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мінімальне та максимальне значення в масиві int.
     */
    private void findMinAndMaxInArray() {
        if (intArray == null || intArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();
        int min = Arrays.stream(intArray).min().orElseThrow();
        int max = Arrays.stream(intArray).max().orElseThrow();
        printOperationDuration(startTime, "пошук мінімального і максимального значення в масиві");

        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);
    }

    /**
     * Пошук значення в HashSet int.
     */
    private void searchSet() {
        long startTime = System.nanoTime();
        boolean isFound = intSet.contains(intValueToSearch);
        printOperationDuration(startTime, "пошук в HashSet int");

        if (isFound) {
            System.out.println("Значення '" + intValueToSearch + "' знайдено в HashSet.");
        } else {
            System.out.println("Значення '" + intValueToSearch + "' в HashSet не знайдено.");
        }
    }

    /**
     * Знаходить мінімальне та максимальне значення в HashSet int.
     */
    private void findMinAndMaxInSet() {
        if (intSet.isEmpty()) {
            System.out.println("HashSet порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();
        int min = intSet.stream().min(Integer::compare).orElseThrow();
        int max = intSet.stream().max(Integer::compare).orElseThrow();
        printOperationDuration(startTime, "пошук мінімального і максимального значення в HashSet");

        System.out.println("Мінімальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);
    }

    /**
     * Порівнює елементи масиву та множини.
     */
    private void compareArrayAndSet() {
        System.out.println("Кількість елементів в масиві: " + intArray.length);
        System.out.println("Кількість елементів в HashSet: " + intSet.size());

        boolean allElementsMatch = true;
        for (int value : intArray) {
            if (!intSet.contains(value)) {
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
     * Зчитує масив int з файлу.
     */
    private int[] readArrayFromFile(String pathToFile) {
        int[] tempArray = new int[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    tempArray[index++] = Integer.parseInt(line);  // Перетворення рядка в int
                } catch (NumberFormatException e) {
                    System.out.println("Попередження: не вдалося перетворити '" + line + "' на int.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Arrays.copyOf(tempArray, index);  // Повертаємо масив
    }

    /**
     * Записує масив int в файл.
     */
    private void writeArrayToFile(int[] intArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (int value : intArray) {
                writer.write(Integer.toString(value));
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
