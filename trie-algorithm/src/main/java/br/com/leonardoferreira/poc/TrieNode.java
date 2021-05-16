package br.com.leonardoferreira.poc;

import java.util.HashMap;

import lombok.Data;

@Data
public class TrieNode {
    private final HashMap<Character, TrieNode> children = new HashMap<>();
    private boolean endOfWord;
}
