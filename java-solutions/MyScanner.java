import java.io.*;

public class MyScanner implements AutoCloseable {
    private final Reader in;

    private Buffer buf;

    private static final int BUF_SIZE = 256;


    private StringScanner stringScanner;


    public MyScanner(Reader in) {
        this.in = in;
        this.buf = new Buffer(in);
    }

    public boolean hasNextLine() {
        if (buf.isNeverFilled() || buf.isBufferReadCompletely()) {
            buf.read();
        }
        return !buf.isStreamReadCompletely();
    }

    public String readLine() {

        StringBuilder line = new StringBuilder();

        for (; ; ) {
            if (buf.isBufferReadCompletely()) {
                buf.read();
            }
            if (!buf.hasNewContent()) {
                return accumulatedString(line);
            }
            if (!buf.isStartedFromLineBreak()) {
                buf.putBufferedStringSnippet(line);
            }
            if (buf.isStartedFromLineBreak() && buf.breakIsAlreadySteppedOver()) {
                buf.updateClosestSeparatorIndex();
                buf.putBufferedStringSnippet(line);
            }
            if (buf.isStartedFromN() && buf.previousBatchFinishedWithR) {
                buf.stepOverStartingSeparator();
            }
            if (buf.isStringReadCompletely()) {
                buf.stepOverStartingSeparator();
                return accumulatedString(line);
            }
            if (buf.isStartedFromRNLineBreak()) {
                buf.stepOverStartingSeparator(2);
                return accumulatedString(line);
            }
            if (buf.previousBatchFinishedWithR) {
                buf.previousBatchFinishedWithR = false;
            } else if (buf.isStartedFromLineBreak()) {
                buf.stepOverStartingSeparator();
                return accumulatedString(line);
            }
        }
    }

    private String accumulatedString(StringBuilder line) {
        stringScanner = new StringScanner(line.toString().trim());
        return line.toString().trim();
    }

    public int nextInt() throws IOException {
        return stringScanner.nextInt();
    }

    public boolean hasNextInt() {
        return stringScanner.hasNextInt();
    }

    public String nextOct() throws IOException {
        return stringScanner.nextOct();
    }

    public boolean hasNextOct() {
        return stringScanner.hasNextOct();
    }

    public boolean hasNextWord() {
        return stringScanner.hasNextWord();
    }

    public String nextWord() throws IOException{
        return stringScanner.nextWord();
    }


    private static class StringScanner {

        private final Reader reader;

        private Integer lastReturnedInt;
        private Integer lastReadInt;

        private String lastReadOct;
        private String lastReturnedOct;

        private String lastReadWord;
        private String lastReturnedWord;

        public StringScanner(String string) {
            this.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(string.getBytes())));
        }

        public boolean hasNextInt() {
            if (lastReadInt == null || lastReadInt == lastReturnedInt) {
                try {
                    lastReadInt = new Integer(readint());
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }

        public int nextInt() throws IOException {
            if (lastReadInt != null && lastReadInt != lastReturnedInt) {
                lastReturnedInt = lastReadInt;
                return lastReadInt;
            }
            return readint();
        }

        public String nextWord() throws IOException {
            if (lastReadWord != null && lastReadWord != lastReturnedWord) {
                lastReturnedWord = lastReadWord;
                return lastReadWord;
            }
            return readString();
        }

        private int readint() throws IOException {
            StringBuilder line = new StringBuilder();

            fillStringBuilderWithNumberSet(line);

            return Integer.parseInt(line.toString());
        }

        private String readString() throws IOException {
            StringBuilder line = new StringBuilder();

            fillStringBuilderWithCharactersSet(line);

            return line.toString();
        }

        private void fillStringBuilderWithNumberSet(StringBuilder line) throws IOException {
            int lastReadCharCode;
            do {
                lastReadCharCode = reader.read();
            } while (Character.isSpaceChar(lastReadCharCode));

            if (Character.getType(lastReadCharCode) == Character.DASH_PUNCTUATION) {
                line.append(Character.toChars(lastReadCharCode));
                lastReadCharCode = reader.read();
            }
            while (Character.isDigit(lastReadCharCode)) {
                line.append(Character.toChars(lastReadCharCode));
                lastReadCharCode = reader.read();
            }
        }

        private void fillStringBuilderWithCharactersSet(StringBuilder line) throws IOException {
            int lastReadCharCode;
            do {
                lastReadCharCode = reader.read();
            } while (!(Character.isLetter(lastReadCharCode) || Character.getType(lastReadCharCode) == Character.DASH_PUNCTUATION || lastReadCharCode == 39) && lastReadCharCode != -1);
            while (Character.isLetter(lastReadCharCode) || Character.getType(lastReadCharCode) == Character.DASH_PUNCTUATION || lastReadCharCode == 39) {
                line.append(Character.toChars(lastReadCharCode));
                lastReadCharCode = reader.read();
            }
        }

        public boolean hasNextOct() {
            if (lastReadOct == null || lastReadOct == lastReturnedOct) {
                try {
                    String nextValue = new String(readOct());
                    if (nextValue.isEmpty()) {
                        lastReadOct = null;
                        return false;
                    } else {
                        lastReadOct = nextValue;
                        return true;
                    }

                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }

        public boolean hasNextWord() {
            if (lastReadWord == null || lastReadWord == lastReturnedWord) {
                try {
                    String nextValue = new String(readString());
                    if (nextValue.isEmpty()) {
                        lastReadWord = null;
                        return false;
                    } else {
                        lastReadWord = nextValue;
                        return true;
                    }

                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }

        public String nextOct() throws IOException {
            if (lastReadOct != null && lastReadOct != lastReturnedOct) {
                lastReturnedOct = lastReadOct;
                return lastReadOct;
            }
            return readOct();
        }

        private String readOct() throws IOException {
            StringBuilder line = new StringBuilder();

            int lastReadCharCode;
            do {
                lastReadCharCode = reader.read();
            } while (Character.isSpaceChar(lastReadCharCode));

            if (Character.getType(lastReadCharCode) == Character.DASH_PUNCTUATION) {
                line.append(Character.toChars(lastReadCharCode));
                lastReadCharCode = reader.read();
            }
            while (Character.isDigit(lastReadCharCode)
                    && Character.toChars(lastReadCharCode)[0] != '8'
                    && Character.toChars(lastReadCharCode)[0] != '9') {
                line.append(Character.toChars(lastReadCharCode));
                lastReadCharCode = reader.read();
            }
            return line.toString();
        }
    }

    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Buffer {
        private char[] buf;
        private int currentCursorPosition;
        private int closestSeparatorIndex;
        private int lastReadBatchSize;
        private final Reader in;

        private boolean wasNeverFilled = true;
        private boolean previousBatchFinishedWithR = false;

        public Buffer(Reader reader) {
            this.buf = new char[BUF_SIZE];
            this.in = reader;
        }

        public void read() {
            try {
                lastReadBatchSize = in.read(buf, 0, buf.length);
                wasNeverFilled = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentCursorPosition = 0;
            if (lastReadBatchSize == -1) {
                closestSeparatorIndex = -1;
            } else {
                closestSeparatorIndex = findClosestSeparatorIndex();
            }
        }

        private int findClosestSeparatorIndex() {
            for (int i = currentCursorPosition; i < lastReadBatchSize; i++) {
                if (buf[i] == '\n' || buf[i] == '\r') {
                    return i;
                }
            }
            return lastReadBatchSize;
        }

        public boolean isStartedFromLineBreak() {
            return lastReadBatchSize > 1 && (currentCursorPosition == 0 || currentCursorPosition == 1) && (buf[0] == '\n' || buf[0] == '\r');
        }

        public boolean isStartedFromN() {
            return lastReadBatchSize > 1 && (currentCursorPosition == 0 || currentCursorPosition == 1) && (buf[0] == '\n');
        }

        public boolean isBufferReadCompletely() {
            return !wasNeverFilled && currentCursorPosition >= lastReadBatchSize;
        }

        public boolean isStringReadCompletely() {
            return closestSeparatorIndex < lastReadBatchSize && currentCursorPosition == closestSeparatorIndex;
        }

        public boolean isStreamReadCompletely() {
            return lastReadBatchSize == -1 || lastReadBatchSize < buf.length && currentCursorPosition == lastReadBatchSize;
        }

        public void putBufferedStringSnippet(StringBuilder sb) {
            sb.append(buf, currentCursorPosition, closestSeparatorIndex - currentCursorPosition);

            currentCursorPosition = closestSeparatorIndex;
        }

        public void shiftPosition(int i) {
            currentCursorPosition += i;
        }

        public void stepOverStartingSeparator() {
            if (currentCursorPosition < lastReadBatchSize) {
                if (buf.length >= currentCursorPosition + 2 && buf[currentCursorPosition] == '\r' && buf[currentCursorPosition + 1] == '\n') {
                    shiftPosition(2);
                } else if (buf[currentCursorPosition] == '\r' || buf[currentCursorPosition] == '\n') {
                    shiftPosition(1);
                }
            }
            updateClosestSeparatorIndex();
            if (closestSeparatorIndex == buf.length && buf[closestSeparatorIndex - 1] == '\r') {
                previousBatchFinishedWithR = true;
            }
        }

        public void stepOverStartingSeparator(int separatorLength) {
            if (currentCursorPosition < lastReadBatchSize) {
                if (buf.length >= currentCursorPosition + 2 && buf[currentCursorPosition] == '\r' && buf[currentCursorPosition + 1] == '\n') {
                    shiftPosition(2);
                } else {
                    shiftPosition(separatorLength);
                }
            }
            updateClosestSeparatorIndex();
        }

        public void updateClosestSeparatorIndex() {
            closestSeparatorIndex = findClosestSeparatorIndex();
            if (closestSeparatorIndex > lastReadBatchSize) {
                closestSeparatorIndex = lastReadBatchSize;
            }
        }

        public boolean isNeverFilled() {
            return wasNeverFilled;
        }

        public boolean hasNewContent() {
            return lastReadBatchSize != -1;
        }

        public boolean breakIsAlreadySteppedOver() {
            return currentCursorPosition == 1;
        }

        public boolean isStartedFromRNLineBreak() {
            return lastReadBatchSize > 1 && (currentCursorPosition == 0 || currentCursorPosition == 1) && (buf[0] == '\r' && buf[1] == '\n');
        }
    }
}
