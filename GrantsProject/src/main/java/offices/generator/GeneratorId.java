package offices.generator;

import java.util.Date;
import java.util.Random;

public class GeneratorId {
    private static final Random random;

    static{
        random = new Random();
    }

    private GeneratorId(){
        super();
    }

    public static String generateUniqId(){

        long times = new Date().getTime();
        int randNum = random.nextInt(10_000);

        String result = Long.toHexString(times + randNum).toUpperCase();

        return result;
    }



}
