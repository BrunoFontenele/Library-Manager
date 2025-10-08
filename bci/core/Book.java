package bci.core;

import java.util.ArrayList;
import java.util.List;

public class Book extends Work{
    private String _isbn;
    private List<Creator> _creators; 

    public Book(String isbn, int price, String title, int numberOfCopies, List<Creator> creators, Category type, int nextWorkId) {
        super(title, price, numberOfCopies, type, nextWorkId);
        _isbn = isbn;

        _creators = new ArrayList<>(creators);
    }

    @Override
    public String getType() {
        return "Livro";
    }

    @Override
    public String getAdInfo() {
        List<String> creatorNames = new ArrayList<>();
        for (Creator c : _creators) {
            creatorNames.add(c.getName());
        }
        return String.join(";", creatorNames) + " - " + _isbn;
    }

    public List<Creator> getCreators() {
        return new ArrayList<>(_creators);
    }
}
