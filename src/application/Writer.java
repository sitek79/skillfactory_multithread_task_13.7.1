package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

// Класс, представляющий поток записи массива целых чисел в файл
public class Writer implements Runnable{
    private int[] AI; // записываемый массив
    private String filename; // внутреннее поле - имя файла
    private String threadName; // Имя потока
    private Thread t; // ссылка на текущий поток

    // Конструктор - получает 3 параметра:
    // - AI - массив, который нужно записать в файл;
    // - filename - имя файла, в который записывается массив AI;
    // - threadName - имя потока.
    public Writer(int[] AI, String filename, String threadName) {
        // Запомнить ссылку на массив
        this.AI = AI;

        // Запомнить имя файла
        this.filename = filename;

        // Запомнить имя потока
        this.threadName = threadName;

        // Создать поток с именем "SaveThread"
        t = new Thread(this, "SaveThread");
    }

    // метод, запускающий текущий поток
    public void start() {
        t.start(); // вызвать метод run()
    }

    // В методе run() указывается код записи в файл
    public void run() {
        // Сообщить о начале выполнения потока
        System.out.println("Begin thread: " + threadName);

        try {
            // Создать связь с текстовым файлом filename: fOut -> filename
            FileOutputStream fOut = new FileOutputStream(filename);

            // Создать связь fOut: ps -> fOut -> filename
            PrintStream pS = new PrintStream(fOut);

            // Записать массив AI в файл
            pS.println(AI.length);
            for (int i=0; i<AI.length; i++) {
                pS.println(AI[i]);
            }

            // Закрыть потоки
            pS.close();
            fOut.close();
        }
        catch (IOException e) {
            // Вывести сообщение об ошибке
            System.out.println("Error: " + e.getMessage());
        }

        // Сообщить о завершении потока
        System.out.println("End thread: " + threadName);
    }
}
