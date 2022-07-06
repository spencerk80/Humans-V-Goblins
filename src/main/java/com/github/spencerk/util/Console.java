package com.github.spencerk.util;

import java.io.IOException;

public class Console {

    public static void clearScreen() {
        String          os = System.getProperty("os.name");
        ProcessBuilder  pb;
        Process         process;

        if(os.contains("Windows"))
            pb = new ProcessBuilder("cmd", "/c", "cls");

        else
            pb = new ProcessBuilder("clear");

        try {
            process = pb.inheritIO().start();
            process.waitFor();
        } catch(InterruptedException | IOException e) {
            System.err.println("Error: Unable to clear screen");
            System.err.println(e.getMessage());
        }
    }

}
