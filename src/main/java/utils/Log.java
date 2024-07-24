package main.java.utils;

public class Log {
    public static void e(String msg) {
        System.err.println("ERROR: " + msg);
    }

    public static void w(String msg) {
        System.out.println("WARNING: " + msg);
    }
}