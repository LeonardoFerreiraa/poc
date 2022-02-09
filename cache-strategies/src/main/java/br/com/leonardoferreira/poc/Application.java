package br.com.leonardoferreira.poc;

public class Application {

    public static void main(String[] args) {
        final Cache<String> cache = LFUCache.from(5);
        bla(cache, "1");
        bla(cache, "2");
        bla(cache, "3");
        bla(cache, "4");
        bla(cache, "5");
        cache.contains("1");
        bla(cache, "6");
        bla(cache, "7");
    }

    private static void bla(final Cache<String> cache, final String entry) {
        cache.add(entry);
        System.out.println(cache.contains(entry));
        System.out.println(cache);
    }

}
