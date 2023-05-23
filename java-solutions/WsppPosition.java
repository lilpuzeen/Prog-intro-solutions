import java.io.*;
import java.util.*;


public class WsppPosition {

    public static void main(String[] args) {


        List<Entry> entries = new ArrayList<>();

        Integer currentIndex = 1;
        Integer currentString = 1;

        try (MyScanner reader = new MyScanner(new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8")))) {
            while (reader.hasNextLine()) {
                String currentLine = reader.readLine();
                while (reader.hasNextWord()) {
                    incorporateSbResultToEntries(entries, reader.nextWord().toLowerCase(), currentIndex, currentString);
                    currentIndex++;
                }
                currentString++;
                currentIndex = 1;
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

    private static void incorporateSbResultToEntries(List<Entry> entries, String word, int currentIndex, int currentString) {
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
            entryWithTheSameWord.getPositions().add(new Position(currentString, currentIndex));
        } else {
            entries.add(new Entry(word, new Position(currentString, currentIndex)));
        }

    }

    private static boolean wordIsNotFinished(int ch) {
        return Character.isLetter((char) ch)
                || "'".contains((char) ch + "")
                || Character.getType(ch) == Character.DASH_PUNCTUATION;
    }

    private static class Entry {
        private String word;
        private Integer frequency;
        private List<Position> positions;

        public Entry(String word, Position positionOfFirstOccurrence) {
            this.word = word;
            this.frequency = 1;
            this.positions = new LinkedList<>() {{
                add(positionOfFirstOccurrence);
            }};
        }

        public String getWord() {
            return word;
        }

        public List<Position> getPositions() {
            return positions;
        }

        public void incrementFrequency() {
            this.frequency++;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Position position : positions) {
                sb.append(" ").append(position.toString());
            }
            return word + " " + frequency + sb.toString();
        }
    }

    private static class Position {
        private final int stringNumber;
        private final int wordInStringNumber;

        public Position(int stringNumber, int wordInStringNumber) {
            this.stringNumber = stringNumber;
            this.wordInStringNumber = wordInStringNumber;
        }

        public String toString() {
            return String.format("%d:%d", stringNumber, wordInStringNumber);
        }
    }
}
