package utils.tools;

public class UtilsTools {

    public static int GetRandomNumberBetween(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static boolean RandomBoolean() {
        return Math.random() < 0.5;
    }
}
