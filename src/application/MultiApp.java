package application;

import java.security.SecureRandom;

public class MultiApp {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Нам нужно подготовить исходные данные чтобы работать с массивами. Попробуем отпочковать это в отдельный поток");
        //Создание Главного потока
        System.out.println("Запускаем Главный поток...");
        Runnable r = () -> {
            System.out.printf("%s запустился... \n", Thread.currentThread().getName());
            try{
                //Thread.sleep(500);
                System.out.println("Начинаем подготовительные операции в главном потоке...");

                // сгенерируем случайное число в диапазоне от 50 до 100 для создания массивов разной длины
                int beginning = 50; // Начальное значение для диапазона случайных чисел
                int end = 100; // Конечное значение для диапазона случайных чисел

                // определим переменные для SecureRandom
                int max = 100;
                int min = 10;

                int array_length_1 = beginning + (int) (Math.random() * end); // Генерация длины массива 1
                System.out.println("Первый массив имеет длину: " + array_length_1);

                int array_length_2 = beginning + (int) (Math.random() * end); // Генерация длины массива 2
                System.out.println("Второй массив имеет длину: " + array_length_2);

                int array_length_3 = beginning + (int) (Math.random() * end); // Генерация длины массива 3
                System.out.println("Третий массив имеет длину: " + array_length_3);

                // 1. Создадим три целочисленных массива случайной длины
                System.out.println("Создаем массивы...");
                int[] arrayInt_1 = new int[array_length_1];
                int[] arrayInt_2 = new int[array_length_2];
                int[] arrayInt_3 = new int[array_length_3];

                // инициализируем элементы массива случайными числами используя класс SecureRandom
                // элементы массива 1
                System.out.println("начинаем инициализировать массивы...");
                for (int i=0; i<arrayInt_1.length;i++){
                    arrayInt_1[i] = new SecureRandom().nextInt(max - min) + min;
                }
                // элементы массива 2
                for (int i=0; i<arrayInt_2.length;i++){
                    arrayInt_2[i] = new SecureRandom().nextInt(max - min) + min;
                    //System.out.println(arrayInt_2[i]);
                }
                // элементы массива 3
                for (int i=0; i<arrayInt_3.length;i++){
                    arrayInt_3[i] = new SecureRandom().nextInt(max - min) + min;
                    //System.out.println(arrayInt_3[i]);
                }
                // Запись массивов в разные файлы в разных потоках
                // 2. Создать три экземпляра класса реализующего интерфейс Runnable
                Writer t1 = new Writer(arrayInt_1, "arrayInt_1.txt", "thread1");
                Writer t2 = new Writer(arrayInt_2, "arrayInt_2.txt", "thread2");
                Writer t3 = new Writer(arrayInt_3, "arrayInt_3.txt", "thread3");

                // 3. Запустить метод run() который запустит потоки на выполнение
                t1.start();
                t2.start();
                t3.start();
            }
            catch(Exception e){
                System.out.println("Возникла ошибка.");
            }
            System.out.printf("%s завершился... \n", Thread.currentThread().getName());
        };
        Thread myThread = new Thread(r,"MultiApp_Thread");
        System.err.println("Поток " + myThread.getName() + " находится в состоянии: " + myThread.getState());
        myThread.start();
        System.err.println("Поток " + myThread.getName() + " находится в состоянии: " + myThread.getState());

        while (myThread.getState() == Thread.State.RUNNABLE) {
            System.err.println("Ждем главный поток...");
            Thread.sleep(50);
        }
        System.err.println("Поток " + myThread.getName() + " находится в состоянии: " + myThread.getState());

        System.err.println("Главный процесс завершил свою работу");
    }
}
