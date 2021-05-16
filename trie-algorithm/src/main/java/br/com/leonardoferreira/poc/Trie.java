package br.com.leonardoferreira.poc;

import lombok.Data;

@Data
public class Trie {

    private final TrieNode root = new TrieNode();

    public void insert(final String word) {
        TrieNode current = root;

        for (char letter : word.toCharArray()) {
            current = current.getChildren()
                    .computeIfAbsent(letter, c -> new TrieNode());
        }

        current.setEndOfWord(true);
    }

    public boolean contains(final String word) {
        TrieNode current = root;

        for (final char letter : word.toCharArray()) {
            final TrieNode node = current.getChildren()
                    .get(letter);

            if (node == null) {
                return false;
            }

            current = node;
        }

        return current.isEndOfWord();
    }

}
