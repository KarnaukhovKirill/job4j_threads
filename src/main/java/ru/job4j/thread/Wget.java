package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String out;

    public Wget(String url, int speed, String out) {
        this.url = url;
        this.speed = speed;
        this.out = out;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(out)) {
            byte[] dataBuffer = new byte[1024];
            int byteRead;
            var startTime = System.currentTimeMillis();
            while ((byteRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, byteRead);
                var result = System.currentTimeMillis() - startTime;
                System.out.println(result);
                if (result < speed) {
                    Thread.sleep(speed - result);
                }
                startTime = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Write arguments: url speed targetPlace");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        var path = Path.of(args[2]);
        if (!path.toFile().isFile() || !path.isAbsolute()) {
            throw new IllegalArgumentException("Write out path is absolute: C:\\projects\\out.html");
        }
        String out = args[2];
        Thread wget = new Thread(new Wget(url, speed, out));
        wget.start();
        wget.join();
    }
}
