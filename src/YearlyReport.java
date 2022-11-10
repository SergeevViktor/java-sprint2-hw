import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
public class YearlyReport {
    int year;
    HashMap<Integer, YearlyReportMonth> monthsData = new HashMap<>();

    public YearlyReport(int year, String path) {
        this.year = year;
        String content = readFileContentsOrNull(path); // содержимое файла
        String[] lines = content.split("\r?\n"); // массив строк
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];                    // "01,1593150,false"
            String[] parts = line.split(",");    // "01,1593150,false" -> ["01", "1593150", "false"]
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);

            if (!monthsData.containsKey(month)) {      // если нет месяца, создает его пустую статистику
                monthsData.put(month, new YearlyReportMonth(month));
            }
            YearlyReportMonth oneMonthData = monthsData.get(month); // сохраняет данные
            if (isExpense) {
                oneMonthData.expenses = amount;
            } else {
                oneMonthData.income = amount;
            }
        }
    }
    public int monthlyProfit() {        // прибыль за месяц
        int monthlyProfit = 0;
        for (Integer month : monthsData.keySet()) {
            YearlyReportMonth oneMonthData = monthsData.get(month);
            monthlyProfit += oneMonthData.income - oneMonthData.expenses;
        }
        return monthlyProfit;
    }
    public double averageExpense() {     // средний расход за год
        double averageExpense;
        int sumExpense = 0;
        for (YearlyReportMonth oneMonthData : monthsData.values()) {
            sumExpense += oneMonthData.expenses;
        }
        averageExpense = sumExpense / 3;
        return averageExpense;
    }
    public double averageIncome() {      // средний доход за год
        double averageIncome;
        int sumIncome = 0;
        for (YearlyReportMonth oneMonthData : monthsData.values()) {
            sumIncome += oneMonthData.income;
        }
        averageIncome = sumIncome / 3;
        return averageIncome;
    }
    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}