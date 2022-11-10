import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static final int MONTHS_COUNT = 3;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport;
        YearlyReport yearlyReport;
        ArrayList<MonthlyReport> allMonthlyReports = new ArrayList<>();

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                for (int i = 1; i <= MONTHS_COUNT; i++) {
                    monthlyReport = new MonthlyReport(i);
                    String path = "resources/m.20210" + i + ".csv";
                    monthlyReport.saveReport(path);
                    allMonthlyReports.add(monthlyReport);
                }
                System.out.println("Все месячные отчеты считаны.");
            }
            else if (command == 2) {
                yearlyReport = new YearlyReport(2021, "resources/y.2021.csv");
                System.out.println("Годовой отчет считан.");
            }
            /*else if (command == 3) {
                s
            }*/
            else if (command == 4) {
                for (int i = 1; i <= MONTHS_COUNT; i++) {
                    System.out.println(Month.of(i - 1).getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")));
                    System.out.println("Самый прибыльный товар: " + allMonthlyReports.get(i).mostProfitableProduct());
                    System.out.println("Самая большая трата: " + allMonthlyReports.get(i).maxExpense());
                }
            }
            else if (command == 5) {
                System.out.println("Рассматриваемый год: " + yearlyReport.year);
                System.out.println("Прибыль по каждому месяцу: ");
                for (int i = 1; i <= MONTHS_COUNT; i++) {
                    System.out.println("Месяц " + (i) + ": " + yearlyReport.monthlyProfit());
                }
                System.out.println("Средний расход за все месяцы в году: " + yearlyReport.averageExpense());
                System.out.println("Средний доход за все месяцы в году: " + yearlyReport.averageIncome());
            }
            else if (command == 6) {
                System.out.println("Программа завершена.");
                break;
            }
        }
    }
    private static void printMenu() {
        System.out.println("Введите команду:");
        System.out.println("1. Считать все месячные отчеты.");
        System.out.println("2. Считать годовой отчёт.");
        System.out.println("3. Сверить отчёты.");
        System.out.println("4. Вывести информацию о всех месячных отчётах.");
        System.out.println("5. Вывести информацию о годовом отчёте.");
        System.out.println("6. Завершить программу.");
    }
}

//Month.of(1).getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"))
