package bci.core.exception;

import pt.tecnico.uilib.menus.CommandException;

public class CouldNotRequestException extends Exception{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public CouldNotRequestException(){
         super("Não foi possível criar a requisição");
    }
}
