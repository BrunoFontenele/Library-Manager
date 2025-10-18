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
    addIntegerField("userID", bci.app.user.Prompt.userId());
    addIntegerField("workID", bci.app.work.Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException {
    int userId = integerField("userID");
    int workId = integerField("workID");

    try{
      _display.popup(bci.app.request.Message.workReturnDay(workId, _receiver.requestWork(userId, workId)));
    }catch(CouldNotRequestException e){
      throw new BorrowingRuleFailedException(userId, workId, _receiver.getRuleError(e));
    }catch(NotEnoughInventoryExceptionCore er){
      if(Form.confirm(Prompt.returnNotificationPreference())){}
        //notificacao
    }
  }
}
