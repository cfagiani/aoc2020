package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Computes the sum of all values in memory after applying a bit mask to inputs in the instruction set. In part 1, the
 * mask applies to the values being written. In part 2, the mask applies to the memory addresses.
 */
public class Day14SolutionImpl implements DaySolution {
    private static final String MASK_PREFIX = "mask = ";

    @Override
    public void part1(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        Map<Long, Long> memory = new HashMap<>();
        String curMask = null;
        for (String line : lines) {
            if (line.startsWith(MASK_PREFIX)) {
                curMask = line.substring(MASK_PREFIX.length());
            } else {
                String[] parts = line.split(" = ");
                Long address = Long.parseLong(parts[0].replaceAll("mem\\[", "").replaceAll("]", ""));
                Long val = Long.parseLong(parts[1].trim());
                memory.put(address, applyMask(val, curMask));
            }
        }
        long sum = memory.values().stream().reduce(0L, Long::sum);
        System.out.println("Sum of all memory is: " + sum);
    }

    @Override
    public void part2(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        Map<Long, Long> memory = new HashMap<>();
        String curMask = null;
        for (String line : lines) {
            if (line.startsWith(MASK_PREFIX)) {
                curMask = line.substring(MASK_PREFIX.length());
            } else {
                String[] parts = line.split(" = ");
                Long address = Long.parseLong(parts[0].replaceAll("mem\\[", "").replaceAll("]", ""));
                Long val = Long.parseLong(parts[1].trim());
                List<Long> addresses = applyAddressMask(address, curMask);
                for (Long addr : addresses) {
                    memory.put(addr, val);
                }
            }
        }
        long sum = memory.values().stream().reduce(0L, Long::sum);
        System.out.println("Sum of all memory is: " + sum);
    }

    /**
     * Applies the mask to the value using the following rules:
     * if the mask has an X, the bit is left unchanged. Otherwise, the value from the mask is used instead of the
     * original bit.
     *
     * @param val
     * @param mask
     * @return
     */
    private Long applyMask(Long val, String mask) {
        String binaryString = String.format("%1$36s", Long.toBinaryString(val)).replace(' ', '0');
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == 'X') {
                builder.append(binaryString.charAt(i));
            } else {
                builder.append(mask.charAt(i));
            }
        }
        return Long.valueOf(builder.toString(), 2);
    }

    /**
     * Applies the mask to the address passed in using the following rules:
     * if the mask bit is 0, then the address bit is left unchanged
     * if the mask bit is 1, the address bit is overwritten by 1
     * if the mask bit is X then it is a "floater"
     * Floaters take on all possible values so we will return a list of addresses.
     *
     * @param address
     * @param curMask
     * @return
     */
    private List<Long> applyAddressMask(Long address, String curMask) {
        List<Long> addresses = new ArrayList<>();
        // get the bit string for the address
        String binaryString = String.format("%1$36s", Long.toBinaryString(address)).replace(' ', '0');
        // count the number of "floaters"
        long floaterCount = curMask.chars().filter(c -> c == 'X').count();
        String countString = String.format("%1$" + floaterCount + "s", 1).replace(' ', '1');
        Long lastCount = Long.valueOf(countString, 2);
        Long cur = 0L;
        // repeat this for every possible value of the floater bit string
        while (cur <= lastCount) {
            countString = String.format("%1$" + floaterCount + "s", Long.toBinaryString(cur)).replace(' ', '0');
            int pos = countString.length() - 1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < binaryString.length(); i++) {
                if (curMask.charAt(i) == 'X') {
                    builder.append(countString.charAt(pos));
                    pos--;
                } else if (curMask.charAt(i) == '1') {
                    builder.append('1');
                } else {
                    builder.append(binaryString.charAt(i));
                }
            }
            addresses.add(Long.valueOf(builder.toString(), 2));
            cur++;
        }
        return addresses;
    }
}
