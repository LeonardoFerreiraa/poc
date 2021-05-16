package br.com.leonardoferreira.poc;

import java.util.Map;

public class TriePrinter {

    public static void print(final Trie trie) {
        print(trie.getRoot());
    }

    public static void print(final TrieNode node) {
        print(node, 0);
    }

    public static void print(final TrieNode node, final int offset) {
        for (final Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            System.out.println(rightPadding(entry.getKey(), offset));
            print(entry.getValue(), offset + 2);
        }
    }

    private static String rightPadding(final char letter, final int offset) {
        if (offset == 0) {
            return String.valueOf(letter);
        }
        if (offset == 3) {
            return "└─ " + letter;
        }
        return " " .repeat(Math.max(0, offset - 1)) + "└─ " + letter;
    }

}
