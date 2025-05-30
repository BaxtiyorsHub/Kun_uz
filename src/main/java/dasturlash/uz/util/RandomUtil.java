package dasturlash.uz.util;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static int getRandomInt5(){
        return random.nextInt(90000) + 10000;
    }

}
