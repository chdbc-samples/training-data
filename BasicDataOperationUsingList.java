import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BasicDataOperationUsingList {
    private static final String PATH_TO_DATA_FILE = "list/String.data";
    private static final String TARGET_STRING = "Parks filled with joy";

    public static void main(String[] args) {
        // Масив і ArrayList для збереження даних
        ArrayList<String> arrayList = new ArrayList<>();
        String[] array = null;

        // Зчитування даних з файлу і заповнення масиву та ArrayList
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_DATA_FILE))) {
            ArrayList<String> tempList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                tempList.add(line);
            }
            array = tempList.toArray(new String[0]);
            arrayList.addAll(tempList);
        } catch (IOException e) {
            System.err.println("Помилка при зчитуванні файлу: " + e.getMessage());
            return;
        }

        // Пошук у масиві
        long startTime = System.nanoTime();
        boolean foundInArray = Arrays.asList(array).contains(TARGET_STRING);
        long durationArraySearch = System.nanoTime() - startTime;

        // Пошук у ArrayList
        startTime = System.nanoTime();
        boolean foundInList = arrayList.contains(TARGET_STRING);
        long durationListSearch = System.nanoTime() - startTime;

        // Сортування масиву
        startTime = System.nanoTime();
        Arrays.sort(array);
        long durationArraySort = System.nanoTime() - startTime;

        // Сортування ArrayList
        startTime = System.nanoTime();
        Collections.sort(arrayList);
        long durationListSort = System.nanoTime() - startTime;

        // Виведення результатів
        System.out.println("Пошук значення \"" + TARGET_STRING + "\" в масиві:");
        System.out.println("    Знайдено: " + foundInArray + ", Час виконання: " + durationArraySearch + " наносекунд");

        System.out.println("Пошук значення \"" + TARGET_STRING + "\" в ArrayList:");
        System.out.println("    Знайдено: " + foundInList + ", Час виконання: " + durationListSearch + " наносекунд");

        System.out.println("Сортування масиву: " + durationArraySort + " наносекунд");
        System.out.println("Сортування ArrayList: " + durationListSort + " наносекунд");
    }
}
