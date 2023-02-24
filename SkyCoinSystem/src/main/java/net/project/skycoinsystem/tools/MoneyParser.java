package net.project.skycoinsystem.tools;

public class MoneyParser {
    public static double doubleParser(double amount) {
        return Math.round(amount * 100d) / 100d;
    }

    public static String formatNumber(double amount) {
        return String.format("%.2f", amount);
    }
}
