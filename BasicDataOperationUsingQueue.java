import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Клас BasicDataOperationUsingQueue надає методи для виконання основних операцій з даними типу LocalDate.
 * Цей клас зчитує дані з файлу, сортує їх та виконує пошук значення в черзі.
 */
public class BasicDataOperationUsingQueue {

    private static PriorityQueue<LocalDate> dateTimeQueue; // Основна колекція
    static final String PATH_TO_DATA_FILE = "list/LocalDate.data"; // Шлях до файлу даних

    LocalDate dateTimeValueToSearch;

    /**
     * Конструктор, який ініціалізує об'єкт з значенням для пошуку.
     * @param args Значення для пошуку.
     */
    BasicDataOperationUsingQueue(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Відсутнє значення для пошуку");
        }

        this.dateTimeValueToSearch = LocalDate.parse(args[0]); // Конвертація у LocalDate

        // Зчитування черги з файлу
        dateTimeQueue = new PriorityQueue<>(
                Arrays.asList(Utils.readDateArrayFromFile(PATH_TO_DATA_FILE))
        );
    }

    public static void main(String[] args) {
        BasicDataOperationUsingQueue basicDataOperationUsingQueue = new BasicDataOperationUsingQueue(args);
        basicDataOperationUsingQueue.doDataOperation();
    }

    private void doDataOperation() {
        searchQueue();
        findMinAndMaxInQueue();
    }

    private void searchQueue() {
        long startTime = System.nanoTime();

        boolean isFound = dateTimeQueue.stream()
                           .anyMatch(dateTime -> dateTime.equals(dateTimeValueToSearch)); // Пошук значення в черзі

        Utils.printOperationDuration(startTime, "пошук значення в черзі");

        System.out.println("Значення " + dateTimeValueToSearch + (isFound ? " знайдено" : " не знайдено") + " в черзі.");
    }

    private void findMinAndMaxInQueue() {
        long startTime = System.nanoTime();

        LocalDate min = dateTimeQueue.stream()
                         .min(LocalDate::compareTo) // Пошук мінімального значення
                         .orElse(null); // Якщо черга порожня, повертаємо null

        LocalDate max = dateTimeQueue.stream()
                         .max(LocalDate::compareTo) // Пошук максимального значення
                         .orElse(null); // Якщо черга порожня, повертаємо null

        Utils.printOperationDuration(startTime, "пошук мінімуму та максимуму в черзі");

        System.out.println("Мінімальне значення: " + min);
        System.out.println("Максимальне значення: " + max);
    }
}

class Utils {

    /**
     * Зчитує масив LocalDate з файлу за допомогою Stream API.
     * @param pathToFile Шлях до файлу.
     * @return Масив значень типу LocalDate.
     */
    static LocalDate[] readDateArrayFromFile(String pathToFile) {
        try {
            return Files.lines(Paths.get(pathToFile))
                        .map(LocalDate::parse)
                        .toArray(LocalDate[]::new); // Збираємо результат в масив LocalDate[]
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання даних з файлу: " + pathToFile, e);
        }
    }

    public static void printOperationDuration(long startTime, String operation) {
        long duration = System.nanoTime() - startTime;
        System.out.println(">>>>>>>> Час виконання операції '" + operation + "': " + duration + " наносекунд");
    }
}
