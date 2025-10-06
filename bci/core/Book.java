package bci.core;

public class Book extends Work{
    private String _isbn;
    private Creator[] _creators;

    public Book(String isbn, int price, String title, int numberOfCopies, Creator[] creators, Category type) {
        super(title, price, numberOfCopies, type);
        _isbn = isbn;
        _creators = creators.clone();
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public String getAdInfo() {
        String[] creators = new String[_creators.length];
        for (int i = 0; i < _creators.length; i++) {
            creators[i] = _creators[i].getName();
        }
        return String.join(";", creators) + " " + _isbn;
    }

    public Creator[] getCreators() {
        Creator[] creators = _creators.clone();
        return creators;
    }

}
