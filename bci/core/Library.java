package bci.core;

import java.io.*;
import bci.core.exception.UnrecognizedEntryException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {
  private Map<Integer, Work> _listOfWorks;
  private Map<String, Creator> _listOfCreators;
  private Map<Integer, User> _listOfUsers;
  
  /** Serial number for serialization. */
  @Serial
  private static final long serialVersionUID = 202501101348L;

  private int _nextWorkId;
  private int _nextUserId;
  private int _currentDate; //criar classe separada?
  private boolean _modified;

  public Library(){
    _nextWorkId = 1;
    _nextUserId = 1;
    _currentDate = 1;
    _modified = false;
    _listOfWorks = new HashMap<>();
    _listOfCreators = new HashMap<>();
    _listOfUsers = new HashMap<>();
  }

  /**
   * Read text input file at the beginning of the program and populates the
   * the state of this library with the domain entities represented in the text file.
   * 
   * @param filename name of the text input file to process
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/

  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    try {
      if (datafile != null && !datafile.isEmpty())
        MyParser(this);
    } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(datafile, e);
    }
  } 

  /* Time */

  int getCurrentDay() {
    return _currentDay;
  }

  void advanceDays(int days){
    _currentDay += days;
  }

  /* User  */

  User registerUser(String userName, String email){
    User newUser = new User(userName, email, _nextUserId);
    _listOfUsers.put(_nextUserId, newUser);
    _nextUserId++;
    _modified = true;
    return newUser;
  }

  User getUser(int id){
    return _listOfUsers.get(id);
  }

  void removeUser(int id){
    _listOfUsers.remove(id);
  }
 
  List<User> getUsers(){
    List<User> listOfUsers = new ArrayList<>();

    for(User users : _listOfUsers)
      listOfUsers.add(users);
    
    return listOfUsers;
  }

  /* Creator */

  Creator registerCreator(String name){
    Creator newCreator = new Creator(name);
    _listOfCreators.put(name, newCreator);
    _modified = true;
    return newCreator;
  }

  /* Work */

  void registerDvd(String igac, Creator creator, String title, int price, int numberOfCopies, Category type){
    Dvd newDvd = new Dvd(igac, creator, title, price, numberOfCopies, type, _nextWorkId);
    _listOfWorks.put(_nextWorkId, newDvd);
    creator.addWork(newDvd);
    _nextWorkId++;
    _modified = true;
  }

  void registerBook(String isbn, int price, String title, int numberOfCopies, Creator[] creators, Category type){
    Book newBook = new Book(igac, creator, title, price, numberOfCopies, type, _nextWorkId);
    _listOfWorks.put(_nextWorkId, newBook);
    for(Creator creator : creators)
      creator.addWork(newBook);
    _nextWorkId++;
    _modified = true;
  }

  public List<Work> getListOfWorks() {
    return _listOfWorks;
  }

  
}

  
