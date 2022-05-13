package main;

import java.math.BigInteger;
import java.util.*;

public class Main {

    public static Map<Long, Long> zMap = new HashMap<>();

    public static void main(String[] args) {

        //===============================
        //CONSTANTS
        //===============================

        long earthPop = 7_753_000_000L;

        double const1 = 5.82842712474619;

        long sum = 21;

        List<String> sameFolks = new ArrayList<>();

        Long nano1 = System.currentTimeMillis();

        boolean check = true;

        //===============================
        //SOLUTION
        //===============================

        for (long i = 1; check; i++) {
            long desiredSum = (long)(sum * const1);
            long wide1 = desiredSum - i;
            long wide2 = desiredSum + i;

            for (long j = wide1; j < wide2; j++) {
                long z = j*(j-1);
                zMap.put(z, j);
            }

            long desiredBoys = (long)(desiredSum/Math.sqrt(2));
            wide1 = desiredBoys - i;
            wide2 = desiredBoys + i;

            for (long j = wide1; j < wide2; j++) {
                long z = j*(j-1);
                if(zMap.containsKey(z*2)){
                    long res = zMap.get(z*2);
                    sameFolks.add("Total: " + res + "; Total boys:  " + j + ";");
                    sum = res;
                    break;
                }
            }
            zMap.clear();
            if(sum > earthPop * 1000000000){
                check = false;
            }
            System.out.println(i);
        }

        Long nano2 = System.currentTimeMillis();

        //===============================
        //DISPLAY
        //===============================

        sameFolks.forEach(System.out::println);
        System.out.println(nano2 - nano1);
    }

}
