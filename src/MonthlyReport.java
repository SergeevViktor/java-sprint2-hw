import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
public class MonthlyReport {
    int month;
    HashMap<String, MonthlyDate> listData = new HashMap<>();

    public MonthlyReport(int month) {
        this.month = month;
    }
    public void saveReport(String path) {
        String content = readFileContentsOrNull(path); // содержимое файла
        String[] lines = content.split("\r?\n"); // массив строк
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];                    // "Коньки,TRUE,50,2000"
            String[] parts = line.split(",");    // "Коньки,TRUE,50,2000" -> ["Коньки", "TRUE", "50", "2000"]
            String name = parts[0];
            boolean is_expense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int sum_of_one = Integer.parseInt(parts[3]);

            if(!listData.containsKey(name)) {
                listData.put(name, new MonthlyDate(name));
            }
            MonthlyDate monthlyDate = listData.get(name);
            if(is_expense) {
                monthlyDate.expense = quantity * sum_of_one;
            } else {
                monthlyDate.income = quantity * sum_of_one;
            }
        }
    }
    public String mostProfitableProduct() {
        int maxProfit = 0;
        String profitableProduct = " ";
        for (String name : listData.keySet()) {
            MonthlyDate monthlyDate = listData.get(name);
            if (maxProfit < monthlyDate.income) {
                maxProfit = monthlyDate.income;
                profitableProduct = monthlyDate.name;
            }
        }
        return profitableProduct + " - " + maxProfit;
    }
    public String maxExpense() {
        int maxExpense = 0;
        String expenseProduct = "";
        for (String name : listData.keySet()) {
            MonthlyDate monthlyDate = listData.get(name);
            if (maxExpense < monthlyDate.expense) {
                maxExpense = monthlyDate.expense;
                expenseProduct = monthlyDate.name;
            }
        }
        return expenseProduct + " - " + maxExpense;
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