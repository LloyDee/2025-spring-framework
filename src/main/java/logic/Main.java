package logic;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int totalCount = random.nextInt(0,300);

        System.out.println(totalCount);
        int batchSize = 50;
        int fullBatches = totalCount / batchSize;
        int remaining = totalCount % batchSize;

        int start = 0;
        for (int i = 0; i <= fullBatches; i++) {
            System.out.println(i);
            int end = (i == fullBatches) ? start + remaining : start + batchSize;
            for (int j = start; j < end; j++) {
                System.out.println("\t" + j);
            }

            if (i < fullBatches || remaining > 0) {
                System.out.println("copied all");
                System.out.println("enter ID");
                System.out.println("enter BusinessCase");
                System.out.println("enter Submit");
            }

            start += batchSize;
        }
    }

}

