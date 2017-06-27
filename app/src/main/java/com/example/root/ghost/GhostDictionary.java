package com.example.root.ghost;

/**
 * Created by root on 27/6/17.
 */

public interface GhostDictionary {
    public final static int MIN_WORD_LENGTH = 4;
    boolean isWord(String word);
    String getAnyWordStartingWith(String prefix);
    String getGoodWordStartingWith(String prefix);
}
