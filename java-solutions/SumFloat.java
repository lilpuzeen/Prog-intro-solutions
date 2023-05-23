public class SumFloat {
    public static void main(String[] args) {

        float finalSum = 0;
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].strip();
            if (args[i].isEmpty()) {
                continue;
            }
            StringBuilder numCollector = new StringBuilder();
            for (int j = 0; j < args[i].length(); j++) {
                if (!Character.isWhitespace(args[i].charAt(j))) {
                    numCollector.append(args[i].charAt(j));
                }
                if ((Character.isWhitespace(args[i].charAt(j)) || (j == args[i].length() - 1))
                        && (!numCollector.toString().isBlank())) {
                    finalSum += Float.parseFloat(numCollector.toString());
                    numCollector = new StringBuilder();
                }
            }
        }
        System.out.printf("%.12f%n",finalSum);
    }
}
