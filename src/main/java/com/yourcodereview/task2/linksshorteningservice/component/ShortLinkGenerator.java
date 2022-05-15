package com.yourcodereview.task2.linksshorteningservice.component;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class ShortLinkGenerator {

    private static final int SMALL_LETTER_ALPHABET_EDGE = 26;
    private static final int BIG_LETTER_ALPHABET_EDGE = SMALL_LETTER_ALPHABET_EDGE * 2;
    private static final int NUMBERS_ALPHABET_EDGE = BIG_LETTER_ALPHABET_EDGE + 10;
    private List<Character> indexToCharTable;
    private int base;

    private void initializeIndexToCharTable() {
        indexToCharTable = new ArrayList<>();
        for (int i = 0; i < SMALL_LETTER_ALPHABET_EDGE; ++i) {
            char c = 'a';
            c += i;
            indexToCharTable.add(c);
        }
        for (int i = SMALL_LETTER_ALPHABET_EDGE; i < BIG_LETTER_ALPHABET_EDGE; ++i) {
            char c = 'A';
            c += (i - SMALL_LETTER_ALPHABET_EDGE);
            indexToCharTable.add(c);
        }
        for (int i = BIG_LETTER_ALPHABET_EDGE; i < NUMBERS_ALPHABET_EDGE; ++i) {
            char c = '0';
            c += (i - BIG_LETTER_ALPHABET_EDGE);
            indexToCharTable.add(c);
        }
    }

    @PostConstruct
    public void initSymbolsStorages() {
        initializeIndexToCharTable();
        base = indexToCharTable.size();
    }

    public String createUniqueId(Long id) {
        List<Integer> base62Id = convertBase10ToBase62Id(id);
        var uniqueUrlId = new StringBuilder();
        for (int digit : base62Id) {
            uniqueUrlId.append(indexToCharTable.get(digit));
        }
        return uniqueUrlId.toString();
    }

    private List<Integer> convertBase10ToBase62Id(Long id) {
        LinkedList<Integer> digits = new LinkedList<>();
        while (id > 0) {
            int remainder = (int) (id % base);
            digits.addFirst(remainder);
            id /= base;
        }
        return digits;
    }
}
