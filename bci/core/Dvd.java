package bci.core;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;


class Dvd extends Work{
    private String _igac;
    private Creator _creator;

    Dvd(String igac, Creator creator, String title, int price, int numberOfCopies, Category type, int nextWorkId){
        super(title, price, numberOfCopies, type, nextWorkId);
        _igac = igac;
        _creator = creator;
    }

    @Override
    String getType() {
        return "DVD";
    }

    @Override
    String getAdInfo(){
        return _creator.getName() + " - " + _igac;
    }

    Creator listCreators() {
        List<Creator> creator = new ArrayList<>();
        creator.add(_creator);
        return Collection.unmodifiableList(creator);
    }

    String getIgac(){return _igac;}

}
