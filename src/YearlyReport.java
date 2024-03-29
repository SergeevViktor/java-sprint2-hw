import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
public class YearlyReport {
    int year;
    HashMap<Integer, YearlyReportMonth> monthsData = new HashMap<>();

    public YearlyReport(int year) {
        this.year = year;
    }
    public void saveReport(String path) {
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
    public int monthlyProfit(int month) {        // прибыль за месяц
        int monthlyProfit = 0;
        YearlyReportMonth oneMonthData = monthsData.get(month);
        monthlyProfit = oneMonthData.income - oneMonthData.expenses;
        return monthlyProfit;
    }
    public double averageExpense() {     // средний расход за год
        double averageExpense;
        int sumExpense = 0;
        for (YearlyReportMonth oneMonthData : monthsData.values()) {
            sumExpense += oneMonthData.expenses;
        }
        averageExpense = sumExpense / monthsData.size();
        return averageExpense;
    }
    public double averageIncome() {      // средний доход за год
        double averageIncome;
        int sumIncome = 0;
        for (YearlyReportMonth oneMonthData : monthsData.values()) {
            sumIncome += oneMonthData.income;
        }
        averageIncome = sumIncome / monthsData.size();
        return averageIncome;
    }
    public int expense(int month) {
        int expense;
        YearlyReportMonth oneMonthData = monthsData.get(month);
        expense = oneMonthData.expenses;
        return expense;
    }
    public int income(int month) {
        int income;
        YearlyReportMonth oneMonthData = monthsData.get(month);
        income = oneMonthData.income;
        return income;
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