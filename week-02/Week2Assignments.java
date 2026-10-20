import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Week2Assignments {
    private static final Set<String> STOP_WORDS = new HashSet<String>();

    static {
        STOP_WORDS.add("the");
        STOP_WORDS.add("was");
        STOP_WORDS.add("and");
        STOP_WORDS.add("a");
        STOP_WORDS.add("is");
        STOP_WORDS.add("of");
        STOP_WORDS.add("in");
    }

    public static void checkPinLength(String pin) {
        if (pin.length() != 4) {
            System.out.println("Invalid PIN - must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static String reverseEachWord(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            result.append(new StringBuilder(words[i]).reverse());
        }
        return result.toString();
    }

    public static void parseInventoryRecord(String csvLine) {
        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.println("Product: " + fields[0].trim()
                + " | SKU: " + fields[1].trim()
                + " | Qty: " + fields[2].trim());
    }

    public static String normalizeCode(String raw) {
        String code = raw.trim();
        if (code.length() < 3) {
            return code;
        }
        return code.substring(0, 3).toUpperCase(Locale.ROOT) + code.substring(3);
    }

    public static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: code must be exactly 13 characters";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }
        for (int i = 3; i < code.length(); i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: year and catalog must be digits";
            }
        }

        StringBuilder formatted = new StringBuilder();
        formatted.append('[')
                .append(code.substring(0, 3))
                .append("] YEAR: ")
                .append(code.substring(3, 7))
                .append(" | CATALOG: ")
                .append(code.substring(7));
        return formatted.toString();
    }

    public static void printFilteredWordFrequency(String feedback) {
        String cleaned = feedback.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s]", " ")
                .trim();
        Map<String, Integer> frequencies = new HashMap<String, Integer>();

        if (!cleaned.isEmpty()) {
            String[] words = cleaned.split("\\s+");
            for (String word : words) {
                if (!STOP_WORDS.contains(word)) {
                    Integer count = frequencies.get(word);
                    frequencies.put(word, count == null ? 1 : count + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> entries =
                new ArrayList<Map.Entry<String, Integer>>(frequencies.entrySet());
        entries.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> left,
                               Map.Entry<String, Integer> right) {
                return right.getValue().compareTo(left.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        System.out.println("1. ATM PIN Length Validator");
        checkPinLength("482");
        checkPinLength("4820");

        System.out.println("\n2. Word Reversal Encoder");
        System.out.println(reverseEachWord("hello club"));

        System.out.println("\n3. Product Inventory CSV Parser");
        parseInventoryRecord("Wireless Mouse,WM-2201,150");
        parseInventoryRecord("Wireless Mouse,150");

        System.out.println("\n4. Library ISBN Normalizer & Validator");
        String normalized = normalizeCode(" pen2026004251 ");
        System.out.println(validateAndFormat(normalized));
        normalized = normalizeCode("12N2026004251");
        System.out.println(validateAndFormat(normalized));

        System.out.println("\n5. Stop-Word-Filtered Word Frequency Report");
        printFilteredWordFrequency(
                "The mentor was great, the session was great and clear.");
    }
}
