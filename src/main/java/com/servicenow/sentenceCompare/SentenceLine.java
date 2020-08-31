package com.servicenow.sentenceCompare;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceLine {
  private Pattern datePattern = Pattern.compile("(^[\\d-: ]*)");

  public SentenceLine(String line) {
    this.line = line;
    lineNoDate = removeDatePrefix(line);
    wordsArr = lineNoDate.split(" ");
  }

  private String line;
  private String lineNoDate;
  private String[] wordsArr;

  private String removeDatePrefix(String line) {
    Matcher matcher = datePattern.matcher(line);
    if (matcher.find()) {
      String date = matcher.group(0);
      return line.substring(date.length());
    }
    return "";
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public String getLineNoDate() {
    return lineNoDate;
  }

  public void setLineNoDate(String lineNoDate) {
    this.lineNoDate = lineNoDate;
  }

  public String[] getWordsArr() {
    return wordsArr;
  }

  public void setWordsArr(String[] wordsArr) {
    this.wordsArr = wordsArr;
  }
}
