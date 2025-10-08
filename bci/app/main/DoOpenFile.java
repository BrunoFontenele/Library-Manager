package bci.app.main;

import bci.core.LibraryManager;
import bci.app.exception.FileOpenFailedException;
import bci.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoOpenFile extends Command<LibraryManager> {

  DoOpenFile(LibraryManager receiver){
    super(Label.OPEN_FILE, receiver);
    addStringField("fileName", Prompt.openFile());
  }

  @Override
  protected final void execute() throws CommandException {
    if(_receiver.isModified())
      if(Form.confirm(Prompt.saveBeforeExit()))
        try{
          _receiver.save();
        }catch(Exception e){
          _display.addLine("Erro em salvar.");
        }
    String fileName = Form.requestString(Prompt.newSaveAs());
    try {
      _receiver.load(fileName);
    } catch (UnavailableFileException efe) {
    throw new FileOpenFailedException(efe);
    }
  }
}
