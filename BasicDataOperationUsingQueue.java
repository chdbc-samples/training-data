// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.PriorityQueue;
// import java.util.stream.Collectors;

// /**
//  * Клас BasicDataOperationUsingQueue надає методи для виконання основних операцій з даними типу LocalDate.
//  * Цей клас зчитує дані з файлу, сортує їх та виконує пошук значення в черзі.
//  */
// public class BasicDataOperationUsingQueue {

//     private static PriorityQueue<LocalDate> dateTimeQueue; // Основна колекція
//     static final String PATH_TO_DATA_FILE = "list/LocalDate.data"; // Шлях до файлу даних

//     LocalDate dateTimeValueToSearch;

//     /**
//      * Конструктор, який ініціалізує об'єкт з значенням для пошуку.
//      * @param args Значення для пошуку.
//      */
//     BasicDataOperationUsingQueue(String[] args) {
//         if (args.length == 0) {
//             throw new RuntimeException("Відсутнє значення для пошуку");
//         }

//         this.dateTimeValueToSearch = LocalDate.parse(args[0]); // Конвертація у LocalDate

//         // Зчитування черги з файлу
//         dateTimeQueue = new PriorityQueue<>(
//                 Arrays.asList(Utils.readDateArrayFromFile(PATH_TO_DATA_FILE))
//         );
//     }

//     public static void main(String[] args) {
//         BasicDataOperationUsingQueue basicDataOperationUsingQueue = new BasicDataOperationUsingQueue(args);
//         basicDataOperationUsingQueue.doDataOperation();
//     }

//     private void doDataOperation() {
//         searchQueue();
//         findMinAndMaxInQueue();
//     }

//     private void searchQueue() {
//         long startTime = System.nanoTime();

//         boolean isFound = dateTimeQueue.stream()
//                            .anyMatch(dateTime -> dateTime.equals(dateTimeValueToSearch)); // Пошук значення в черзі

//         Utils.printOperationDuration(startTime, "пошук значення в черзі");

//         System.out.println("Значення " + dateTimeValueToSearch + (isFound ? " знайдено" : " не знайдено") + " в черзі.");
//     }

//     private void findMinAndMaxInQueue() {
//         long startTime = System.nanoTime();

//         LocalDate min = dateTimeQueue.stream()
//                          .min(LocalDate::compareTo) // Пошук мінімального значення
//                          .orElse(null); // Якщо черга порожня, повертаємо null

//         LocalDate max = dateTimeQueue.stream()
//                          .max(LocalDate::compareTo) // Пошук максимального значення
//                          .orElse(null); // Якщо черга порожня, повертаємо null

//         Utils.printOperationDuration(startTime, "пошук мінімуму та максимуму в черзі");

//         System.out.println("Мінімальне значення: " + min);
//         System.out.println("Максимальне значення: " + max);
//     }
// }

// class Utils {

//     /**
//      * Зчитує масив LocalDate з файлу за допомогою Stream API.
//      * @param pathToFile Шлях до файлу.
//      * @return Масив значень типу LocalDate.
//      */
//     static LocalDate[] readDateArrayFromFile(String pathToFile) {
//         try {
//             return Files.lines(Paths.get(pathToFile))
//                         .map(LocalDate::parse)
//                         .toArray(LocalDate[]::new); // Збираємо результат в масив LocalDate[]
//         } catch (IOException e) {
//             throw new RuntimeException("Помилка читання даних з файлу: " + pathToFile, e);
//         }
//     }

//     public static void printOperationDuration(long startTime, String operation) {
//         long duration = System.nanoTime() - startTime;
//         System.out.println(">>>>>>>> Час виконання операції '" + operation + "': " + duration + " наносекунд");
//     }
// }


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue для операцій з даними типу LocalDate.
 */
public class BasicDataOperationUsingQueue {

    private static LocalDate[] dateArray;  // Масив LocalDate
    private static PriorityQueue<LocalDate> dateQueue;  // Черга для LocalDate

    static final String PATH_TO_DATA_FILE = "list/LocalDateData.data"; // Шлях до файлу даних
    private LocalDate dateValueToSearch;

    /**
     * Конструктор ініціалізує значення для пошуку та завантажує дані з файлу.
     * @param args Значення для пошуку.
     */
    public BasicDataOperationUsingQueue(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Відсутнє значення для пошуку");
        }

        this.dateValueToSearch = LocalDate.parse(args[0]);  // Конвертація аргументу в LocalDate
        dateArray = Utils.readDateArrayFromFile(PATH_TO_DATA_FILE); // Зчитування масиву
        dateQueue = new PriorityQueue<>(Arrays.asList(dateArray));  // Ініціалізація черги
    }

    public static void main(String[] args) {
        BasicDataOperationUsingQueue operation = new BasicDataOperationUsingQueue(args);
        operation.doDataOperation();
    }

    private void doDataOperation() {
        sortArray();
        searchArray();
        findMinAndMaxInArray();
        searchQueue();
        findMinAndMaxInQueue();
    }

    /**
     * Сортування масиву LocalDate за допомогою Stream API.
     */
    private void sortArray() {
        long startTime = System.nanoTime();
        
        dateArray = Arrays.stream(dateArray)
                          .sorted()
                          .toArray(LocalDate[]::new); // Сортуємо та перетворюємо назад в масив
        
        Utils.printOperationDuration(startTime, "сортування масиву LocalDate");
    }

    /**
     * Пошук конкретного значення в масиві LocalDate за допомогою Stream API.
     */
    private void searchArray() {
        long startTime = System.nanoTime();
        
        boolean found = Arrays.stream(dateArray)
                              .anyMatch(date -> date.equals(dateValueToSearch)); // Порівнюємо значення
        
        Utils.printOperationDuration(startTime, "пошук в масиві LocalDate");
        System.out.println("Значення " + dateValueToSearch + (found ? " знайдено" : " не знайдено") + " в масиві.");
    }

    /**
     * Пошук мінімального та максимального значення в масиві LocalDate за допомогою Stream API.
     */
/**
 * Пошук мінімального і максимального значення в масиві LocalDate за допомогою Stream API.
 */
    private void findMinAndMaxInArray() {
        long startTime = System.nanoTime();

        // Використання Stream API для пошуку мінімального значення
        LocalDate min = Arrays.stream(dateArray)
                            .min(LocalDate::compareTo)
                            .orElse(null);

        // Використання Stream API для пошуку максимального значення
        LocalDate max = Arrays.stream(dateArray)
                            .max(LocalDate::compareTo)
                            .orElse(null);

        Utils.printOperationDuration(startTime, "пошук мінімуму та максимуму в масиві LocalDate");
        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);
    }


    /**
     * Пошук конкретного значення в черзі LocalDate.
     */
    private void searchQueue() {
        long startTime = System.nanoTime();
        
        boolean isFound = dateQueue.stream()
                                   .anyMatch(date -> date.equals(dateValueToSearch)); // Пошук за допомогою stream
        
        Utils.printOperationDuration(startTime, "пошук значення в черзі LocalDate");
        System.out.println("Значення " + dateValueToSearch + (isFound ? " знайдено" : " не знайдено") + " в черзі.");
    }

    /**
     * Пошук мінімального та максимального значення в черзі LocalDate.
     */
    private void findMinAndMaxInQueue() {
        long startTime = System.nanoTime();
        
        LocalDate min = dateQueue.stream()
                                 .min(LocalDate::compareTo)
                                 .orElse(null); // Мінімальне значення
        
        LocalDate max = dateQueue.stream()
                                 .max(LocalDate::compareTo)
                                 .orElse(null); // Максимальне значення
        
        Utils.printOperationDuration(startTime, "пошук мінімуму та максимуму в черзі LocalDate");
        System.out.println("Мінімальне значення в черзі: " + min);
        System.out.println("Максимальне значення в черзі: " + max);
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
                        .map(LocalDate::parse) // Конвертація рядка у LocalDate
                        .toArray(LocalDate[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання даних з файлу: " + pathToFile, e);
        }
    }

    /**
     * Виводить час виконання операції.
     * @param startTime Час початку операції.
     * @param operation Назва операції.
     */
    public static void printOperationDuration(long startTime, String operation) {
        long duration = System.nanoTime() - startTime;
        System.out.println(">>>>>>>>> Час виконання операції '" + operation + "': " + duration + " наносекунд");
    }
}
