package bci.core;

public abstract class Work {
    private static int _newWorkId = 1;
    private int _workId;
    private String _title;
    private int _price;
    private int _numberOfCopies;
    private int _AvailableCopies;
    private Category _type;

    
    public Work(String title, int price, int numberOfCopies, Category type){
        _workId = _newWorkId;
        _newWorkId++;
        _title = title;
        _numberOfCopies = _AvailableCopies = numberOfCopies;
        _price = price;
        _type = type;
    }

    public int getWorkId() {return _workId;}

    public int getNumberOfCopies() {return _numberOfCopies;}

    public int getAvailableCopies() {return _AvailableCopies;}

    public String getTitle() {return _title;}

    public int getPrice() {return _price;}

    public Category getCategory() {return _type;} 

    public abstract String getType();

    public abstract String getAdInfo();

    public String toString(){
        return String.format("%d - %d de %d - %s - %s - %d - %s - %s",
        _workId, _AvailableCopies, _numberOfCopies, getType(), _title, _price, getCategory(), getAdInfo());
    }

    //package-private?
    //alterar numero de copias


}
