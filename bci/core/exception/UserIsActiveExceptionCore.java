package bci.core.exception;

import pt.tecnico.uilib.menus.CommandException;

public class UserIsActiveExceptionCore extends CommandException{
    @java.io.Serial
  private static final long serialVersionUID = 202507171003L;

 /**
   * @param id the id of concerned user
   */
  public UserIsActiveExceptionCore(int id) {
    super("Não ativo" + id);
  }
}
