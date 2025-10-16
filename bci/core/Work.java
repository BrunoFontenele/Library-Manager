package bci.core;

import java.io.Serializable;

abstract class Work implements Serializable {
    private final int _workId;
    private String _title;
    private int _price;
    private int _numberOfCopies;
    private int _availableCopies;
    private Category _category;

    Work(String title, int price, int numberOfCopies, Category category, int nextWorkId){
        _workId = nextWorkId;
        _title = title;
        _numberOfCopies = _availableCopies = numberOfCopies;
        _price = price;
        _category = category;
    }

    int getWorkId() {return _workId;}

    int getNumberOfCopies() {return _numberOfCopies;}

    int getAvailableCopies() {return _availableCopies;}

    String getTitle() {return _title;}

    int getPrice() {return _price;}

    abstract String getType();

    abstract String getAdInfo();

    abstract void removeWork();

    void setNumberOfCopies(int _numberOfCopies) { this._numberOfCopies = _numberOfCopies; }

    String getCategoryString() {
        if(_category == Category.FICTION) return "Ficção";
        if(_category == Category.REFERENCE) return "Referência";
        return "Técnica e Científica";
    } 

    @Override
    public String toString(){
        return String.format("%d - %d de %d - %s - %s - %d - %s - %s",
            _workId, _numberOfCopies, _availableCopies, getType(), _title, _price, getCategoryString(), getAdInfo());
    }
}
