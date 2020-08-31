package com.servicenow.sentenceCompare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainClass {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Argument is missing\nUsage:\njava com.servicenow.sentenceCompare.MainClass <input file name>");
      System.exit(1);
    }
    String fileName = args[0];
    MainClass mainClass = new MainClass();
    mainClass.processFile(fileName);
  }

  public void processFile(String fileName) {
    Path inputFilePath = Paths.get(fileName);
    int endIndex = fileName.lastIndexOf('.');
    Path outputFilePath = Paths.get(fileName.substring(0, endIndex) + "_output" + fileName.substring(endIndex));
    try(BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
    List<String> lines = Files.readAllLines(inputFilePath);
      List<SentenceLine> sentenceLines = lines.stream().map(SentenceLine::new).collect(Collectors.toList());
      for (int currLine = 0 ; currLine < sentenceLines.size() ; currLine++) {
        SentenceLine sentenceLine = sentenceLines.get(currLine);
        String[] wordsArr = sentenceLine.getWordsArr();
        for (int checkedLine = currLine + 1 ; checkedLine < sentenceLines.size() ; checkedLine++) {
          SentenceLine checkedSentenceLine = sentenceLines.get(checkedLine);
          List<WordsMismatch> wordsMismatches = new ArrayList<>();
          String[] checkedWordsArr = checkedSentenceLine.getWordsArr();
          boolean printMismatch = false;
          for (int currWord = 0 ; currWord < Math.min(wordsArr.length, checkedWordsArr.length) ; currWord++) {
            if (!wordsArr[currWord].equals(checkedWordsArr[currWord])) {
              if (wordsMismatches.size() == 1) {
                printMismatch = false;
                break;
              }
              wordsMismatches.add(new WordsMismatch(wordsArr[currWord], checkedWordsArr[currWord]));
              printMismatch = true;
            }
          }
          if (printMismatch) {
            printMismatch(writer, sentenceLine, checkedSentenceLine, wordsMismatches.get(0));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printMismatch(BufferedWriter writer, SentenceLine sentenceLine,
    SentenceLine checkedSentenceLine, WordsMismatch wordsMismatch) throws IOException {
    writer.write(sentenceLine.getLine());
    writer.newLine();
    writer.write(checkedSentenceLine.getLine());
    writer.newLine();
    writer.write("The changing word was: " + wordsMismatch.getFirstWord() + ", " +
      wordsMismatch.getSecondWord());
    writer.newLine();
  }
}
