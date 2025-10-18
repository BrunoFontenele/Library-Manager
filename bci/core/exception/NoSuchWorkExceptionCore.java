package bci.core.exception;

import pt.tecnico.uilib.menus.CommandException;

public class NoSuchWorkExceptionCore extends CoreRuleException{
    
    /**
     * Class encoding reference to an invalid work id.
     */
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
      /**
    * @param id unknown work id
       */
    public NoSuchWorkExceptionCore(int idWork) {
        super("A obra " + idWork + " não existe.");
    }
}
    