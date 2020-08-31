package com.servicenow.sentenceCompare;

public class WordsMismatch {
  private String firstWord;
  private String secondWord;

  public WordsMismatch(String firstWord, String secondWord) {
    this.firstWord = firstWord;
    this.secondWord = secondWord;
  }

  public String getFirstWord() {
    return firstWord;
  }

  public void setFirstWord(String firstWord) {
    this.firstWord = firstWord;
  }

  public String getSecondWord() {
    return secondWord;
  }

  public void setSecondWord(String secondWord) {
    this.secondWord = secondWord;
  }
}
