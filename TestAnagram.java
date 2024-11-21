import java.util.*;

public class TestAnagram {
    public static void main(String args[]) {
        System.out.println(isAnagram("silent", "listen"));  // true
        System.out.println(isAnagram("William Shakespeare", "I am a weakish speller")); // true
        System.out.println(isAnagram("Madam Curie", "Radium came")); // true
        System.out.println(isAnagram("Tom Marvolo Riddle", "I am Lord Voldemort")); // true

        System.out.println(preProcess("What? No way!!!"));

        System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");

        String str = "1234567";
        Boolean pass = true;
        for (int i = 0; i < 10; i++) {
            String randomAnagram = randomAnagram(str);
            System.out.println(randomAnagram);
            pass = pass && isAnagram(str, randomAnagram);
            if (!pass) break;
        }
        System.out.println(pass ? "test passed" : "test Failed");
    }

    public static boolean isAnagram(String str1, String str2) {
        str1 = preProcess(str1);
        str2 = preProcess(str2);
        if (str1.length() != str2.length()) return false;
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

    private static String preProcess(String str) {
        return str.toLowerCase().replaceAll("[^a-z]", "");
    }

    public static String randomAnagram(String str) {
        str = preProcess(str);
        List<Character> chars = new ArrayList<>();
        for (char c : str.toCharArray()) chars.add(c);
        Collections.shuffle(chars);
        StringBuilder result = new StringBuilder();
        for (char c : chars) result.append(c);
        return result.toString();
    }
}