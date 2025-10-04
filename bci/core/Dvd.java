package bci.core;

public class Dvd extends Work{
    private String _igac;
    private Creator _creator;

    public Dvd(String igac, Creator creator, String title, int price, int numberOfCopies, Category type){
        super(title, price, numberOfCopies, type);
        _igac = igac;
        _creator = creator;
    }

    @Override
    public String getType() {
        return "DVD";
    }

    @Override
    public String getAdInfo(){
        return _creator.getName() + " " + _igac;
    }

    public Creator getCreator() {
        return _creator;
    }

    
}
