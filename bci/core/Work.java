package bci.core;

import java.io.Serializable;

public abstract class Work implements Serializable{
    private int _workId;
    private String _title;
    private int _price;
    private int _numberOfCopies;
    private int _AvailableCopies;
    private Category _category;

    
    public Work(String title, int price, int numberOfCopies, Category category, int nextWorkId){
        _workId = nextWorkId;
        _title = title;
        _numberOfCopies = _AvailableCopies = numberOfCopies;
        _price = price;
        _category = category;
    }

    public int getWorkId() {return _workId;}

    public int getNumberOfCopies() {return _numberOfCopies;}

    public int getAvailableCopies() {return _AvailableCopies;}

    public String getTitle() {return _title;}

    public int getPrice() {return _price;}

    public abstract String getType();

    public abstract String getAdInfo();

    public String getCategoryString() {
        if(_category == Category.FICTION){return "Ficção";}
        if(_category == Category.REFERENCE){return "Referência";}
        else{
            return "Técnica e Científica";
        }
    } 

    public String toString(){
        return String.format("%d - %d de %d - %s - %s - %d - %s - %s",
        _workId, _AvailableCopies, _numberOfCopies, getType(), _title, _price, getCategoryString(), getAdInfo());
    }

    //package-private?
    //alterar numero de copias


}
