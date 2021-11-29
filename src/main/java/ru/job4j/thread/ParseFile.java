package ru.job4j.thread;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        synchronized (this) {
            return content(file, data -> true);
        }
    }

    public String getContentWithoutUnicode() {
        synchronized (this) {
            return content(file, data -> data < 0x80);
        }
    }

    private String content(File file, Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
