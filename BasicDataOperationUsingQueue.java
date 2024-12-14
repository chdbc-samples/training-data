import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue надає методи для виконання основних операцiй з даними типу LocalDate.
 * <p>Цей клас зчитує данi з файлу "list/LocalDate.data", сортує їх та виконує пошук значення в масивi та черзi.</p>
 */
public class BasicDataOperationUsingQueue {
    private static LocalDate[] dateArray; // Оголошення масиву
    private static PriorityQueue<LocalDate> dateQueue; // Оголошення черги
    static final String PATH_TO_DATA_FILE = "list/LocalDate.data";

    LocalDate dateValueToSearch;
    LocalDate[] dateValueArray;
    Queue<LocalDate> dateValueQueue;

    /**
     * Конструктор, який iницiалiзує об'єкт з значенням для пошуку.
     * @param args Значення для пошуку.
     */
    BasicDataOperationUsingQueue(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.dateValueToSearch = LocalDate.parse(valueToSearch, DateTimeFormatter.ISO_DATE); // Конвертація у LocalDate

        dateArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE); // Зчитування масиву
        dateQueue = new PriorityQueue<>(Arrays.asList(dateArray)); // Ініціалізація черги
    }

    public static void main(String[] args) {
        BasicDataOperationUsingQueue basicDataOperationUsingQueue = new BasicDataOperationUsingQueue(args);
        basicDataOperationUsingQueue.doDataOperation();
    }

    private void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();
        sortArray();
        searchArray();
        findMinAndMaxInArray();
        searchQueue();
        findMinAndMaxInQueue();
        peekAndPollQueue();
        Utils.writeArrayToFile(dateArray, PATH_TO_DATA_FILE + ".sorted");
    }

    private void sortArray() {
        long startTime = System.nanoTime();
        Arrays.sort(dateArray);
        Utils.printOperationDuration(startTime, "сортування масиву LocalDate");
    }

    private void searchArray() {
        long startTime = System.nanoTime();
        int index = Arrays.binarySearch(dateArray, dateValueToSearch);
        Utils.printOperationDuration(startTime, "пошук в масивi LocalDate");
        if (index >= 0) {
            System.out.println("\u0417\u043d\u0430\u0447\u0435\u043d\u043d\u044f '" + dateValueToSearch + "' \u0437\u043d\u0430\u0439\u0434\u0435\u043d\u043e \u0437\u0430 i\u043d\u0434\u0435\u043a\u0441\u043e\u043c: " + index);
        } else {
            System.out.println("\u0417\u043d\u0430\u0447\u0435\u043d\u043d\u044f '" + dateValueToSearch + "' \u043d\u0435 \u0437\u043d\u0430\u0439\u0434\u0435\u043d\u043e.");
        }
    }

    private void findMinAndMaxInArray() {
        long startTime = System.nanoTime();
        LocalDate min = Collections.min(Arrays.asList(dateArray));
        LocalDate max = Collections.max(Arrays.asList(dateArray));
        Utils.printOperationDuration(startTime, "\u043f\u043e\u0448\u0443\u043a \u043c\u0456\u043d\u0456\u043c\u0443 \u0442\u0430 \u043c\u0430\u043a\u0441\u0438\u043c\u0443\u043c\u0443 ");
        System.out.println("\u041c\u0456\u043d\u0456\u043c\u0430\u043b\u044c\u043d\u0435: " + min);
        System.out.println("\u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u0435: " + max);
    }

    private void searchQueue() {
        long startTime = System.nanoTime();
        boolean isFound = dateQueue.contains(dateValueToSearch);
        Utils.printOperationDuration(startTime, "пошук в Queue LocalDate");
        System.out.println(isFound ? "\u0417\u043d\u0430\u0439\u0434\u0435\u043d\u043e." : "\u041d\u0435 \u0437\u043d\u0430\u0439\u0434\u0435\u043d\u043e.");
    }

    private void findMinAndMaxInQueue() {
        LocalDate min = Collections.min(dateQueue);
        LocalDate max = Collections.max(dateQueue);
        System.out.println("\u041c\u0456\u043d: " + min + ", \u041c\u0430\u043a\u0441: " + max);
    }

    private void peekAndPollQueue() {
        System.out.println("Peek: " + dateQueue.peek());
        System.out.println("Poll: " + dateQueue.poll());
        System.out.println("Peek after poll: " + dateQueue.peek());
    }
}

class Utils {
    /**
     * Виводить час виконання операцiї в наносекундах.
     * @param startTime Час початку операцiї в наносекундах.
     * @param operationName Назва операцiї.
     */
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "'': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв LocalDateTime з файлу.
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв LocalDateTime.
     */
    static LocalDate[] readArrayFromFile(String pathToFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE; // Формат для LocalDate
        LocalDate[] tempArray = new LocalDate[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                LocalDate date = LocalDate.parse(line, formatter); // Конвертація тексту у LocalDate
                tempArray[index++] = date;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalDate[] finalArray = new LocalDate[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
    }

    /**
     * Записує масив об'єктiв LocalDateTime у файл.
     * @param dateArray Масив об'єктiв LocalDateTime.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(LocalDate[] dateArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (LocalDate date : dateArray) {
                writer.write(date.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}