package bci.core;


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

    Creator getCreator() {
        return _creator;
    }

    String getIgac(){return _igac;}

    void removeWork(Work work){
        _creator.removeWork(work.getWorkId());
        if (_creator.getWorkList().isEmpty())
            removeCreator(_creator.getName());
    }
}
