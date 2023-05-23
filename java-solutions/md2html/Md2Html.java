package md2html;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Md2Html {

    private static final Map<String, String> mdToHtmlMap = new HashMap<>(){{
        put("**", "strong");
        put("__", "strong");
        put("--", "s");
        put("`", "code");
        put("*", "em");
        put("_", "em");
        put("~", "mark");
    }};

    public static void main(String[] args) {
        Path inputFile = Paths.get(args[0]);
        Path outputFile = Paths.get(args[1]);
        String content = readContent(inputFile);
        String htmlContent = convertToHtml(content.split("\\n{2,}"));
        writeResultToFile(htmlContent, outputFile);
    }

    private static String convertToHtml(String[] split) {
        return Arrays.stream(split)
                .parallel()
                .map(Md2Html::convertStringToHtml)
                .filter(string -> !string.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    private static String convertStringToHtml(String mdString) {
        if (mdString.isEmpty()) {
            return "";
        }
        if (mdString.charAt(0) == '\n') {
            mdString = mdString.substring(1);
        }


        mdString = mdString.replaceAll("&", "&amp;");
        mdString = mdString.replaceAll("<", "&lt;");
        mdString = mdString.replaceAll(">", "&gt;");

        String lastOpenedTag = "<p>";
        StringBuilder resultSb = new StringBuilder();
        int numberOfLeadingHashes = 0;
        while (mdString.charAt(numberOfLeadingHashes) == '#') {
            numberOfLeadingHashes++;
        }
        int indexToStartAnalysisFrom = 0;
        if (numberOfLeadingHashes > 0 && mdString.charAt(numberOfLeadingHashes) == ' ') {
            lastOpenedTag = new StringBuilder("<h")
                    .append(numberOfLeadingHashes)
                    .append(">").toString();
            indexToStartAnalysisFrom = numberOfLeadingHashes + 1;
        }
        resultSb.append(lastOpenedTag);

        mdString = replaceArrayThroughout(mdString, indexToStartAnalysisFrom);
        resultSb.append(mdString);
        resultSb.append(generateClosingTag(lastOpenedTag));
        String result = resultSb.toString();
        while (result.contains("\\")) {
            result = result.replace("\\", "");
        }
        return result;
    }

    private static String replaceArrayThroughout(String source, int indexToStartAnalysisFrom) {
        List<Integer> starStartIndexes = new ArrayList<>();
        List<Integer> dashStartIndexes = new ArrayList<>();
        Map<Integer, String> markdowns = new LinkedHashMap<>();
        char[] sourceAsCharArray = source.toCharArray();
        for (int index = indexToStartAnalysisFrom; index < sourceAsCharArray.length; index++) {
            if (sourceAsCharArray[index] == '*' && index != sourceAsCharArray.length - 1 && sourceAsCharArray[index + 1] == '*') {
                markdowns.put(index, "**");
            } else if (sourceAsCharArray[index] == '_' && index != sourceAsCharArray.length - 1 && sourceAsCharArray[index + 1] == '_') {
                markdowns.put(index, "__");
            } else if (sourceAsCharArray[index] == '-' && index != sourceAsCharArray.length - 1 && sourceAsCharArray[index + 1] == '-') {
                markdowns.put(index, "--");
            } else if (sourceAsCharArray[index] == '*'  && (index == 0 || (sourceAsCharArray[index-1] != '*' && sourceAsCharArray[index-1] != 92))) {
                starStartIndexes.add(index);
                markdowns.put(index, "*");
            } else if (sourceAsCharArray[index] == '_'  && (index == 0 || (sourceAsCharArray[index-1] != '_' && sourceAsCharArray[index-1] != 92))) {
                dashStartIndexes.add(index);
                markdowns.put(index, "_");
            } else if (sourceAsCharArray[index] == '`') {
                markdowns.put(index, "`");
            } else if (sourceAsCharArray[index] == '~'  && (index == 0 || (sourceAsCharArray[index-1] != '~' && sourceAsCharArray[index-1] != 92))) {
                markdowns.put(index, "~");
            }
        }
        if (starStartIndexes.size() % 2 != 0) {
            markdowns.remove(starStartIndexes.get(starStartIndexes.size() - 1));
        }
        if (dashStartIndexes.size() % 2 != 0) {
            markdowns.remove(dashStartIndexes.get(dashStartIndexes.size() - 1));
        }
        if (markdowns.isEmpty()) {
            return source.substring(indexToStartAnalysisFrom);
        }
        StringBuilder sb = new StringBuilder();
        int currentIndex = indexToStartAnalysisFrom;
        Map<String, Boolean> openedFlags = new HashMap<>(){{
            put("**",false);
            put("__", false);
            put("--", false);
            put("`", false);
            put("*", false);
            put("_", false);
            put("~", false);
        }};
        for (int mdStartIndex: markdowns.keySet()) {
            String md = markdowns.get(mdStartIndex);
            String html = mdToHtmlMap.get(md);
            sb.append(source, currentIndex, mdStartIndex)
                    .append(openedFlags.get(md) ? "</" : "<")
                    .append(html)
                    .append(">");
            openedFlags.put(md, !openedFlags.get(md));
            currentIndex = mdStartIndex + md.length();
        }
        if (currentIndex < sourceAsCharArray.length) {
            sb.append(source.substring(currentIndex));
        }
        return sb.toString();

    }


    private static String readContent(Path inputFile) {
        try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
            return reader.lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeResultToFile(String htmlContent, Path outputFile) {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(htmlContent);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateClosingTag(String openingTag) {
        return "</" + openingTag.substring(1);
    }
}
