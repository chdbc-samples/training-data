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
            System.out.println("Вiдсутнє значення для пошуку. Використано значення за замовчуванням.");
            stringValueToSearch = "Parks filled with joy";
        } else {
            stringValueToSearch = args[0];
        }
    
        stringArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        stringList = new ArrayList<>(Arrays.asList(stringArray));
    }
    

    /**
     * Виконує основнi операцiї з даними.
     */
    void doDataOperation() {
        searchArray();
        findMinAndMaxInSet();
        sortArray();
        findMinAndMaxInSet();
        searchArray();

        // операцiї з ArrayList
        searchList();
        findMinAndMaxInList();
        sortList();
        findMinAndMaxInList();
        compareArrayAndList();

        // записати вiдсортований масив у файл
        Utils.writeArrayToFile(stringArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв String та вимiрює час сортування.
     */
    void sortArray() {
        long startTime = System.nanoTime();
        stringArray = Arrays.stream(stringArray)
                            .sorted()
                            .toArray(String[]::new);
        Utils.printOperationDuration(startTime, "сортування масиву String");
    }
    
    /**
     * Метод для пошуку значення в масивi String.
     */
    void searchArray() {
        long startTime = System.nanoTime();
        boolean found = Arrays.stream(stringArray)
                            .anyMatch(value -> value.equals(stringValueToSearch));
        
        Utils.printOperationDuration(startTime, "пошук в масивi String");
    
        System.out.println("Значення '" + stringValueToSearch + "'" +
                        (found ? " знайдено" : " не знайдено") + " в масиві.");
    }
    

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi String.
     */
    void findMinAndMaxInSet() {
        if (stringList == null || stringList.isEmpty()) {
            System.out.println("Множина порожня або не ініціалізована.");
            return;
        }
    
        long startTime = System.nanoTime();
    
        String min = stringList.stream()
                            .min(String::compareTo)
                            .orElse("Мінімум не знайдено");
    
        String max = stringList.stream()
                            .max(String::compareTo)
                            .orElse("Максимум не знайдено");
    
        Utils.printOperationDuration(startTime, "пошук мінімального і максимального значення в множині");
    
        System.out.println("Мінімальне значення в множині: " + min);
        System.out.println("Максимальне значення в множині: " + max);
    }
    
    
    /**
     * Шукає задане значення в ArrayList.
     */
    void searchList() {
        long startTime = System.nanoTime();
        int index = stringList.stream()
                            .filter(value -> value.equals(stringValueToSearch))
                            .findFirst()
                            .map(stringList::indexOf)
                            .orElse(-1);
    
        Utils.printOperationDuration(startTime, "пошук в ArrayList String");
    
        if (index >= 0) {
            System.out.println("Значення '" + stringValueToSearch + "' знайдено в ArrayList за індексом: " + index);
        } else {
            System.out.println("Значення '" + stringValueToSearch + "' в ArrayList не знайдено.");
        }
    }
    

    /**
     * Знаходить мiнiмальне та максимальне значення в ArrayList.
     */
    void findMinAndMaxInList() {
        if (stringList == null || stringList.isEmpty()) {
            System.out.println("ArrayList порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        String min = Collections.min(stringList);
        String max = Collections.max(stringList);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної в ArrayList");

        System.out.println("Мiнiмальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);
    }

    /**
     * Сортує ArrayList об'єктiв String та вимiрює час сортування.
     */
    void sortList() {
        long startTime = System.nanoTime();
        stringList = stringList.stream()
                            .sorted()
                            .toList();
    
        Utils.printOperationDuration(startTime, "сортування ArrayList String");
    }
    
    void compareArrayAndList() {
        long startTime = System.nanoTime();
        boolean allElementsMatch = Arrays.stream(stringArray)
                                        .allMatch(stringList::contains);
    
        Utils.printOperationDuration(startTime, "порівняння масиву та списку");
    
        if (allElementsMatch) {
            System.out.println("Усі елементи масиву містяться в списку.");
        } else {
            System.out.println("Не всі елементи масиву містяться в списку.");
        }
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
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            return br.lines()
                    .toArray(String[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання даних з файлу: " + pathToFile, e);
        }
    }
    

    /**
     * Записує масив об'єктiв String у файл.
     * 
     * @param stringArray Масив об'єктiв String.
     * @param pathToFile Шлях до файлу для запису.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    static void writeArrayToFile(String[] stringArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (String line : stringArray) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e)            {
            e.printStackTrace();
        }
    }
}
