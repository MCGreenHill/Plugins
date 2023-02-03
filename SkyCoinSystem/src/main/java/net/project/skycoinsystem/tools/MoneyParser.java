package net.project.skycoinsystem.tools;

public class MoneyParser {
    public static double doubleParser(double amount) {
        return Math.round(amount * 100d) / 100d;
    }
}
