package com.example.root.ghost;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Random;
import android.util.Log;


class TrieNode {
    private String garbageWord = "INVALID_WORD";
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        char keyString;
        TrieNode node = this;
        for(int i = 0; i < s.length(); i++){
            keyString = s.charAt(i);
            if(!node.children.containsKey(keyString)){
                node.children.put(keyString, new TrieNode());
            }
            node = node.children.get(keyString);
        }
        node.isWord = true;
    }

    public boolean isWord(String s) {
        Log.d("-->", "isWord");
        isWord = false;
        TrieNode node = this;
        for(int i = 0; i < s.length(); i++){
            node = node.children.get(s.charAt(i));
            if(node == null){
                return false;
            }
        }
        Log.d("-->", "isWord result " + node.isWord);
        Log.d("-->", "...for string " + s);
        return node.isWord;
    }

    public String selectRandomWord(){
        Set keyList;
        char key;
        String word = "";
        TrieNode node = this;
        while(!node.isWord){
            keyList = node.children.keySet();
            key = (char) keyList.toArray()[new Random().nextInt(keyList.size())];
            node = node.children.get(key);
            word += key;
        }
        return word;
    }
    public boolean garbageWord(String s){
        Log.d("--> ", "Checking Word for Trash");
        TrieNode node = this;
        for(int i = 0; i < s.length(); i++){
            node = node.children.get(s.charAt(i));
            if(node == null){
                Log.d("--> node == null ", s);
                return true;
            }
        }
        return false;
    }

    public String getAnyWordStartingWith(String s) {
        if(garbageWord(s)){
            return garbageWord;
        }
        if(s.equals("")){
            return selectRandomWord();
        }
        Set keyList;
        char key;
        String word = s;
        TrieNode node = this;
        for(int i = 0; i < s.length(); i++){
            node = node.children.get(s.charAt(i));
        }
        while(!node.isWord){
            keyList = node.children.keySet();
            int index;
            try{
                index = new Random().nextInt(keyList.size());
            }
            catch(Exception e){
                return garbageWord;
            }
            key = (char) keyList.toArray()[new Random().nextInt(keyList.size())];
            node = node.children.get(key);
            word += key;
        }
        return word;
    }

    public Set<Character> getCleanKeys(TrieNode node){
        Set<Character> keyList = node.children.keySet();
        char key;
        for(int i = 0; i < keyList.size(); i++){
            key = (char) keyList.toArray()[i];
            if(!node.children.containsKey(key)){
                keyList.remove(key);
            }
        }
        return keyList;
    }

    public String getGoodWordStartingWith(String s) {
        if(garbageWord(s)){
            Log.d("--> ", "Garbage Word --> Null");
            return garbageWord;
        }
        Set<Character> keyList;
        char key;
        String word = s;
        TrieNode node = this;
        for(int i = 0; i < s.length(); i++){
            node = node.children.get(s.charAt(i));
        }
        while(!node.isWord) {
            keyList = getCleanKeys(node);
            if (keyList.size() == 0) {
                return getAnyWordStartingWith(s);
            }
            key = (char) keyList.toArray()[new Random().nextInt(keyList.size())];
            node = node.children.get(key);
            word += key;
        }
        return word;
    }
}