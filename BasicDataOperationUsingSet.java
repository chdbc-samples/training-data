import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцiй з даними типу int.
 */
public class BasicDataOperationUsingSet {
    static final String PATH_TO_DATA_FILE = "list/int.data";  // Шлях до файлу з даними

    int intValueToSearch;  // Значення для пошуку
    int[] intArray;  // Масив для зберігання значень типу int
    Set<Integer> intSet = new HashSet<>();  // Колекція HashSet для значень типу int

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
        this.intValueToSearch = Integer.parseInt(valueToSearch);  // Перетворення тексту в int

        intArray = readArrayFromFile(PATH_TO_DATA_FILE);  // Зчитування масиву з файлу
        intSet = new HashSet<>();
        for (int value : intArray) {
            intSet.add(value);  // Запис в HashSet
        }
    }

    /**
     * Виконує основнi операцiї з даними.
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
     * Метод для пошуку значення в масивi int.
     */
    private void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.intArray, intValueToSearch);  // Пошук в масиві

        printOperationDuration(startTime, "пошук в масивi int");

        if (index >= 0) {
            System.out.println("Значення '" + intValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + intValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi int.
     */
    private void findMinAndMaxInArray() {
        if (intArray == null || intArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        int min = intArray[0];
        int max = intArray[0];

        for (int value : intArray) {
            if (value < min) min = value;
            if (value > max) max = value;
        }

        printOperationDuration(startTime, "пошук мiнiмального i максимального значення в масивi");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Метод для пошуку значення в HashSet int.
     */
    private void searchSet() {
        long startTime = System.nanoTime();

        boolean isFound = this.intSet.contains(intValueToSearch);  // Пошук в HashSet

        printOperationDuration(startTime, "пошук в HashSet int");

        if (isFound) {
            System.out.println("Значення '" + intValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Значення '" + intValueToSearch + "' в HashSet не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в HashSet int.
     */
    private void findMinAndMaxInSet() {
        if (intSet == null || intSet.isEmpty()) {
            System.out.println("HashSet порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        int min = intSet.stream().min(Integer::compare).orElseThrow();
        int max = intSet.stream().max(Integer::compare).orElseThrow();

        printOperationDuration(startTime, "пошук мiнiмального i максимального значення в HashSet");

        System.out.println("Мiнiмальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);
    }

    /**
     * Порiвнює елементи масиву та множини.
     */
    private void compareArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + intArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + intSet.size());

        boolean allElementsMatch = true;
        for (int value : intArray) {
            if (!intSet.contains(value)) {
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
                writer.write(Integer.toString(value));  // Запис в файл
                writer.newLine();
            }
            System.out.println("Відсортовані дані записані у файл: " + pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Виводить час виконання операцiї в наносекундах.
     *
     * @param startTime Час початку операцiї в наносекундах.
     * @param operationName Назва операцiї.
     */
    private void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }
}
