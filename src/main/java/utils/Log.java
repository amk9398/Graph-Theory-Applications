package main.java.utils;

public class Log {
    public static boolean DEBUG = false;

    public static void d(String msg) {
        if (DEBUG) {
            System.out.println("DEBUG: " + msg);
        }
    }

    public static void e(String msg) {
        System.err.println("ERROR: " + msg);
    }

    public static void w(String msg) {
        System.out.println("WARNING: " + msg);
    }
}