package main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static main.Main.testMap;

class mapWriter implements Runnable{

    int from;
    int to;
    private static final Object lock = new Object();

    public mapWriter(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            testMap.put("key" + i, i);

//            put("key" + i, i);//synchronized & lock test
            System.out.println(testMap);//ConcurrentModificationException
        }
    }

//    private static void put(String key, Integer value){//lock test
//        synchronized (lock)
//        {
//            testMap.put(key, value);
//        }
//
//    }

//    private static synchronized void put(String key, Integer value){//synchronized keyWord test
//        testMap.put(key, value);
//    }
}

public class Main {

//    public static Map<String, Integer> testMap = new HashMap<>();

    public static Map<String, Integer> testMap = new ConcurrentHashMap<>();//ConcurrentHashMap

//    public static Map<String, Integer> blankMap = new HashMap<>();//Maps.synchronizedMap
//    public static Map<String, Integer> testMap = Collections.synchronizedMap(blankMap);//Maps.synchronizedMap



    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(new mapWriter(100*i,100*(i + 1))));
        }

        threadList.forEach(Thread::start);
        threadList.forEach(a ->{
            try {
                a.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        System.out.println(testMap.size());
    }
}
