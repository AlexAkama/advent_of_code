package advent_of_code.event_2024.day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static advent_of_code.util.AdventUtils.parseStringToInts;
import static advent_of_code.util.AdventUtils.readAllFromFile;

public class Loader {

    private static final String DATA = "src/main/java/advent_of_code/event_2024/day07/data.txt";
    private static final String TEST = "src/main/java/advent_of_code/event_2024/day07/test.txt";

    private static List<String> strings;

    public static void main(String[] args) {
        init();
        System.out.println(part2());
    }

    private static long part1() {
        long sum = 0;
        for (String s : strings) {
            String[] split = s.split(":\\s+");
            long target = Long.parseLong(split[0]);
            int[] nums = parseStringToInts(split[1]);
            if (canFromNums(target, nums, 1, nums[0])) {
                sum += target;
            }
        }
        return sum;
    }

    private static long part2() {
        long sum = 0;
        for (String s : strings) {
            String[] split = s.split(":\\s+");
            long target = Long.parseLong(split[0]);
            int[] nums = parseStringToInts(split[1]);
            if (canFromMergedNums(target, nums, 1, nums[0])) {
                sum += target;
            }
        }
        return sum;
    }

    private static boolean canFromMergedNums(long target, int[] nums) {
        if (nums.length == 1) return target == nums[0];
        List<int[]> combinations = generateCombination(nums);
        for (int[] combination : combinations) {
            if (canFromNums(target, combination, 1, combination[0])) {
                System.out.println(Arrays.toString(combination));
                return true;
            }
        }
        return false;
    }

    private static List<int[]> generateCombination(int[] nums) {
        List<int[]> result = new ArrayList<>();
        generateCombinationsHelper(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void generateCombinationsHelper(int[] nums, int pos, List<Integer> current, List<int[]> result) {
        if (pos == nums.length) {
            result.add(current.stream().mapToInt(Integer::intValue).toArray());
            return;
        }
        List<Integer> newGroup = new ArrayList<>(current);
        newGroup.add(nums[pos]);
        generateCombinationsHelper(nums, pos + 1, newGroup, result);

        if (!current.isEmpty()) {
            int lastIndex = current.size() - 1;
            int lastNumber = current.get(lastIndex);
            current.set(lastIndex, Integer.parseInt(lastNumber + "" + nums[pos]));
            generateCombinationsHelper(nums, pos + 1, current, result);
            current.set(lastIndex, lastNumber);
        }
    }

    private static boolean canFromNums(long target, int[] nums, int pos, long result) {
        if (pos == nums.length || target < result) return result == target;
        if (canFromNums(target, nums, pos + 1, result + nums[pos])) return true;
        if (canFromNums(target, nums, pos + 1, result * nums[pos])) return true;
        return false;
    }

    private static boolean canFromMergedNums(long target, int[] nums, int pos, long result) {
        if (pos == nums.length || target < result) return result == target;
        if (canFromMergedNums(target, nums, pos + 1, result + nums[pos])) return true;
        if (canFromMergedNums(target, nums, pos + 1, result * nums[pos])) return true;
        if (canFromMergedNums(target, nums, pos + 1, concatenateNumbers(result, nums[pos]))) return true;
        return false;
    }

    public static long concatenateNumbers(long num1, int num2) {
        String concatenatedString = Long.toString(num1) + Integer.toString(num2);
        return Long.parseLong(concatenatedString);
    }

    private static void init() {
        strings = readAllFromFile(DATA);
    }

}
