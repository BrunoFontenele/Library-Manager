package bci.core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Creator {
    private String _name;
    private List<Work> _workList;

    public Creator(String name){
        _name = name;
        _workList = new ArrayList<>();
    }

    public void addWork(Work work){
        _workList.add(work);
    }

    public void removeWork(int workId){
        Iterator<Work> it = _workList.iterator();
        while(it.hasNext()){
            if(it.next().getWorkId() == workId){
                it.remove();
            }
        }

    }
    
    public String getName() {return _name;}

    public List<Work> getWorkList() {
        return Collections.unmodifiableList(_workList);
    }
    
} 

