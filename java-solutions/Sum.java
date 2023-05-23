public class Sum {
    public static void main(String[] args) {
        int finalSum = 0;
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].strip();
            if (args[i].isEmpty()) {
                continue;
            }
            StringBuilder numCollector = new StringBuilder();
            for (int j = 0; j < args[i].length(); j++) {
                if (args[i].charAt(j) == '-' || args[i].charAt(j) == '+' || Character.isDigit(args[i].charAt(j))) {
                    numCollector.append(args[i].charAt(j));
                }
                if ((Character.isWhitespace(args[i].charAt(j)) || (j == args[i].length() - 1))
                        && (!numCollector.toString().isBlank())) {
                    finalSum += Integer.parseInt(numCollector.toString());
                    numCollector = new StringBuilder();
                }

            }
        }
        System.out.println(finalSum);
    }
}
