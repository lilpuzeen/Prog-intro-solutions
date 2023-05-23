import java.io.*;
import java.util.*;


public class Wspp {

    public static void main(String[] args) {


        List<Entry> entries = new ArrayList<>();

        int currentIndex = 1;

        try {
            try (MyScanner reader = new MyScanner(new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8")))) {
                while (reader.hasNextLine()) {
                    String currentLine = reader.readLine();
                    while (reader.hasNextWord()) {
                        incorporateSbResultToEntries(entries, reader.nextWord().toLowerCase(), currentIndex++);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"))) {
                for (Entry entry : entries) {
                    writer.append(entry.toString()).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void incorporateSbResultToEntries(List<Entry> entries, String word, int currentIndex) {
        if (word.isBlank()) {
            return;
        }

        Entry entryWithTheSameWord = null;
        for (Entry entry : entries) {
            if (entry.getWord().equals(word)) {
                entryWithTheSameWord = entry;
                break;
            }
        }
        if (entryWithTheSameWord != null) {
            entryWithTheSameWord.incrementFrequency();
            entryWithTheSameWord.getIndexes().add(currentIndex);
        } else {
            entries.add(new Entry(word, currentIndex));
        }

    }

    private static boolean wordIsNotFinished(int ch) {
        return Character.isLetter((char) ch)
                || "'".contains((char) ch + "")
                || Character.getType(ch) == Character.DASH_PUNCTUATION;
    }

    private static class Entry {
        private String word;
        private int frequency;
        private List<Integer> indexes;

        public Entry(String word, int indexOfFirstOccurrence) {
            this.word = word;
            this.frequency = 1;
            this.indexes = new LinkedList<>() {{
                add(indexOfFirstOccurrence);
            }};
        }

        public String getWord() {
            return word;
        }

        public List<Integer> getIndexes() {
            return indexes;
        }

        public void incrementFrequency() {
            this.frequency++;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int index : indexes) {
                sb.append(" ").append(index);
            }
            return word + " " + frequency + sb.toString();
        }
    }
}
