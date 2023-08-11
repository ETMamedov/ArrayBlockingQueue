package org.example;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    protected static ArrayBlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
    protected static ArrayBlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
    protected static ArrayBlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {

        Random random = new Random();

        new Thread(() -> {

            for (int i = 0; i < 10_000; i++) {
                try {
                    queue1.put(generateText("abc", 3 + random.nextInt(100_000)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    queue2.put(generateText("abc", 3 + random.nextInt(100_000)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    queue3.put(generateText("abc", 3 + random.nextInt(100_000)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


        new Thread(() -> {
            int aMax = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    int a = 0;
                    char[] charArray = queue1.take().toCharArray();
                    for (int j = 0; j < charArray.length; j++) {
                        if (charArray[j] == 'a') a++;
                    }
                    if (a > aMax) aMax = a;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("max a: "+aMax);
        }).start();

        new Thread(() -> {
            int bMax = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    int b = 0;
                    char[] charArray = queue2.take().toCharArray();
                    for (int j = 0; j < charArray.length; j++) {
                        if (charArray[j] == 'b') b++;
                    }
                    if (b > bMax) bMax = b;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("max b: "+bMax);
        }).start();

        new Thread(() -> {
            int cMax = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    int c = 0;
                    char[] charArray = queue3.take().toCharArray();
                    for (int j = 0; j < charArray.length; j++) {
                        if (charArray[j] == 'b') c++;
                    }
                    if (c > cMax) cMax = c;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("max c: "+cMax);
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}