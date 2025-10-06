package bci.app.user;

import bci.core.LibraryManager;
import bci.app.exception.UserRegistrationFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

  DoRegisterUser(LibraryManager receiver) {
    super(Label.REGISTER_USER, receiver);
    //FIXME add command fields
    addStringField("nome", Prompt.userName());
    addStringField("email", Prompt.userEMail());
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String nome = stringField("nome");
    String email = stringField("email");

      _display.popup(Message.registrationSuccessful(_receiver.registerUser(nome, email)));
      //falta exception
  }
}

