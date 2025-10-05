package bci.core;

import java.util.ArrayList;
import java.util.List;

public class Book extends Work{
    private String _isbn;
    private List<Creator> _creators;

    public Book(String isbn, int price, String title, int numberOfCopies, List<Creator> creators, Category type, int id) {
        super(title, price, numberOfCopies, type, id);
        _isbn = isbn;
        _creators = new ArrayList<>();
        for(Creator creator : creators)
            _creators.add(creator);
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public String getAdInfo() {
    List<String> names = new ArrayList<>();
    for (Creator creator : _creators)
        names.add(creator.getName());
    return String.join(";", names) + " " + _isbn;
    }

    public List<Creator> getCreators() {
        List<Creator> creators = new ArrayList<>();
        for(Creator creator : _creators)
            creators.add(creator);
        return creators;
    }

}
