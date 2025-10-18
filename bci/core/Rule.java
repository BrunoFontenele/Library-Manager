package bci.core;

import bci.core.exception.NotEnoughInventoryExceptionCore;

abstract class Rule {
    private final int _ruleId;

    protected Rule(int ruleId){
        _ruleId = ruleId;
    }

    protected int getId(){return _ruleId;}

    abstract void check(Work work, User user) throws Exception;

}

class CheckRequestTwice extends Rule{
    CheckRequestTwice(int id){
        super(id);
    }   

    void check(Work work, User user) throws CouldNotRequestException{
        List<Request> userRequests = _requestById(user.getUserId());
        if(userRequests == null)
            return;
        for(Request request : userRequests){
            if(request.get_workId() == work.getWorkId())
                throw new CouldNotRequestException();
        }
    }
}

class CheckActiveUser extends Rule{
    CheckActiveUser(int id){
        super(id);
    }   

    void check(Work work, User user) throws CouldNotRequestException{
        if(!user.isActive())
            throw new CouldNotRequestException();
    }
}

class CheckInventory extends Rule{
    CheckInventory(int id){
        super(id);
    }

    void check(Work work, User user) throws NotEnoughInventoryExceptionCore{ //Para as notificacoes
        if(work.getAvailableCopies() < 1)
            throw new NotEnoughInventoryExceptionCore();
    }
}

class CheckNumberRequisitions extends Rule{
    CheckNumberRequisitions(int id){
        super(id);
    }

    void check(Work work, User user) throws CouldNotRequestException{
        if(user.getActiveNumReq()+1 > user.getBehavior().getMaxReq())
            throw new CouldNotRequestException();
    }
}

class CheckCategory extends Rule{
    CheckCategory(int id){
        super(id);
    }

    void check(Work work, User user) throws CouldNotRequestException{
        if(work.getCategoryString().equals("Referência"))
            throw new CouldNotRequestException();
    }
}


class CheckPrice extends Rule{
    CheckPrice(int id){
        super(id);
    }

    void check(Work work, User user) throws CouldNotRequestException{
        if(user.getBehavior() == Cumpridor) return;

        if(work.getPrice()>25) throw new CouldNotRequestException();
    }
}

