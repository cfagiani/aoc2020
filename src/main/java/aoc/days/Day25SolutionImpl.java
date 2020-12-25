package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds the Encryption Key by finding the secret loop sizes used by either end of the cryptographic handshake. This
 * takes the public key from the input and uses trial/error to find the loop size. Once we have the loop size for both
 * parties, we use the value for one party along with the public key of the other party to find the encryption key.
 */
public class Day25SolutionImpl implements DaySolution {

    private static final long DIVISOR = 20201227L;
    private static final long SUBJECT_NUM = 7L;

    @Override
    public void part1(String input) {
        List<String> keys = StringUtil.splitOnLines(input);
        List<Long> loopSizes = new ArrayList<>();
        for (String key : keys) {
            loopSizes.add(findLoopSize(Long.parseLong(key)));
        }
        System.out.println("The encryption key is " + getEncryptionKey(Long.parseLong(keys.get(1)), loopSizes.get(0)));
    }

    @Override
    public void part2(String input) {
        //no part 2 today
    }

    private long findLoopSize(long publicKey) {
        long curVal = SUBJECT_NUM;
        long count = 0L;
        while (curVal != publicKey) {
            curVal = (curVal * SUBJECT_NUM) % DIVISOR;
            count++;
        }
        return count;
    }

    private long getEncryptionKey(long publicKey, long loopSize) {
        long val = publicKey;
        for (long i = 0L; i < loopSize; i++) {
            val = (val * publicKey) % DIVISOR;
        }
        return val;
    }
}
