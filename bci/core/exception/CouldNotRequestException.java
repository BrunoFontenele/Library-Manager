package bci.core.exception;

import pt.tecnico.uilib.menus.CommandException;

public class CouldNotRequestException extends CoreRuleException{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    private int error;

    public CouldNotRequestException(int errorId){
         error = errorId;
    }

    public int getError(){
        return error;
    }
}
