package br.com.leonardoferreira.poc;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TrieTest {

    @Nested
    class InsertTest {
        @Test
        void shouldInsertSimpleWordSuccessfully() {
            final Trie trie = new Trie();
            trie.insert("world");

            Assertions.assertEquals(5, countNodes(trie.getRoot()));
        }

        @Test
        void shouldInsertWordsReusingLetters() {
            final Trie trie = new Trie();
            trie.insert("world");
            trie.insert("word");

            Assertions.assertEquals(6, countNodes(trie.getRoot()));
        }


        long countNodes(final TrieNode node) {
            long count = 0;
            for (final Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
                count += countNodes(entry.getValue()) + 1;
            }

            return count;
        }

    }

    @Nested
    class ContainsTest {

        @Test
        void shouldContain() {
            final Trie trie = new Trie();
            trie.insert("hello");

            Assertions.assertTrue(trie.contains("hello"));
        }

        @Test
        void shouldNotContain() {
            final Trie trie = new Trie();
            trie.insert("hello");

            Assertions.assertFalse(trie.contains("word"));
        }

    }

}
