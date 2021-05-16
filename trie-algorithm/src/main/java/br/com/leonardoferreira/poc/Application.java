package br.com.leonardoferreira.poc;

public class Application {

    public static void main(String[] args) {
        final Trie trie = new Trie();
        trie.insert("hello");
        trie.insert("word");
        trie.insert("world");
        trie.insert("wide");
        trie.insert("wild");

        System.out.println(trie.contains("hello"));
        System.out.println(trie.contains("mundo"));
        System.out.println(trie.contains("word"));

        TriePrinter.print(trie);
    }

}
