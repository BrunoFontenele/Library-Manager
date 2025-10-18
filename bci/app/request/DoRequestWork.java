package bci.app.request;

import bci.core.LibraryManager;
import bci.core.exception.CouldNotRequestException;
import bci.core.exception.NotEnoughInventoryExceptionCore;
import bci.app.exception.NoSuchUserException;
import bci.app.exception.NoSuchWorkException;
import bci.app.exception.UserRegistrationFailedException;
import bci.app.main.Message;
import bci.app.exception.BorrowingRuleFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

  DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
    addStringField("userID", Prompt.userID());
    addStringField("WorkID", Prompt.workID());
  }

  @Override
  protected final void execute() throws CommandException {
    int userId = integerField("userID");
    int workId = integerField("workID");

    try{
      _display.popup(Message.workReturnDay(_receiver.requestWork(userId, workId)));
    }catch(CouldNotRequestException e){
      throw new BorrowingRuleFailedException(e);
    }catch(NotEnoughInventoryExceptionCore er){
      if(Form.confirm(Prompt.returnNotificationPreference()))
        //notificacao
        throw new BorrowingRuleFailedException(er);
    }
  }
}
