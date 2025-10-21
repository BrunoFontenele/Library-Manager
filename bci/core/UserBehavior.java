package bci.core;

import java.io.Serializable;

interface UserBehavior{
    int getReqTime(int copiesNum);
    int getMaxReq();
    String toString();
}

class Normal implements UserBehavior, Serializable {
    private static Normal _normal; //posso ter stattic?

    private Normal() {}

    static Normal getNormalBehavior() {
        if(_normal == null)
            _normal = new Normal();
        return _normal;
    }

    @Override
    public int getReqTime(int copiesNum){
        if(copiesNum == 1) return 3;
        else if(copiesNum <= 5) return 8;
        else return 15;
    }

    @Override
    public int getMaxReq(){ //public??
        return 3;
    }

    @Override
    public String toString(){
        return "NORMAL";
    }
}


class Faltoso implements UserBehavior, Serializable {
    private static Faltoso _faltoso; //posso ter stattic?

    private Faltoso() {}

    static Faltoso getFaltosoBehavior() {
        if(_faltoso == null)
            _faltoso = new Faltoso();
        return _faltoso;
    }

    @Override
    public int getReqTime(int copiesNum){
        return 2;
    }

    @Override
    public int getMaxReq(){
        return 1;
    }

    @Override
    public String toString(){
        return "FALTOSO";
    }
}

class Cumpridor implements UserBehavior, Serializable {
    private static Cumpridor _cumpridor; //posso ter stattic?

    private Cumpridor() {}

    static Cumpridor getCumpridorBehavior() {
        if(_cumpridor == null)
            _cumpridor = new Cumpridor();
        return _cumpridor;
    }

    @Override
    public int getReqTime(int copiesNum){
        if(copiesNum == 1) return 8;
        else if(copiesNum <= 5) return 15;
        else return 30;
    }

    @Override
    public int getMaxReq(){
        return 5;
    }

    @Override
    public String toString(){
        return "CUMPRIDOR";
    }
}