package bci.core;

import java.io.*;

import bci.core.exception.NoSuchCreatorExceptionCore;
import bci.core.exception.NoSuchUserExceptionCore;
import bci.core.exception.NoSuchWorkExceptionCore;
import bci.core.exception.UnrecognizedEntryException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {
  private List<Work> _listOfWorks;
  private List<Creator> _listOfCreators;
  private List<User> _listOfUsers;
  private int _currentDay;
  private int _nextWorkId;
  private int _nextUserId;

  /** Serial number for serialization. */
  @Serial
  private static final long serialVersionUID = 202501101348L;

  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define more methods

  /**
   * Read text input file at the beginning of the program and populates the
   * the state of this library with the domain entities represented in the text file.
   * 
   * @param filename name of the text input file to process
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    MyParser parser = new MyParser(this);
    parser.parseFile(filename);
  }


  public Library(){
    _listOfCreators = new ArrayList<>();
    _listOfWorks = new ArrayList<>();
    _listOfUsers = new ArrayList<>();
    _nextUserId = _nextWorkId = _currentDay = 1;
  }

  //getters
  public List<Work> getListOfWorks() {return _listOfWorks;}

  public List<Creator> getListOfCreators() {return _listOfCreators;}

  public List<User> getListOfUsers(){return _listOfUsers;}

  public int getCurrentDay(){ return _currentDay;}




  //work
  public String listWork(int workId) throws NoSuchWorkExceptionCore{
    for (Work w : _listOfWorks) {
        if (w.getWorkId() == workId) {
            return w.toString();
        }
    }
    throw new NoSuchWorkExceptionCore(workId);
}


  public String listWorks() {
    _listOfWorks.sort(Comparator.comparingInt(Work::getWorkId));
    
    StringBuilder sb = new StringBuilder();
    for (Work w : _listOfWorks) {
        sb.append(w.toString()).append("\n");
    }
    
    return sb.toString();
  }


  public String listWorksByCreators(String name) throws NoSuchCreatorExceptionCore {
    List<Work> works = new ArrayList<>();

    for (Creator c : _listOfCreators) {
        if (c.getName().equals(name)) {
            works.addAll(c.getWorkList());
        }
    }

    if (works.isEmpty()) {
        throw new NoSuchCreatorExceptionCore(name);
    }

    works.sort(Comparator.comparing(w -> w.getTitle().toLowerCase()));

    StringBuilder sb = new StringBuilder();
    for (Work w : works) {
        sb.append(w).append("\n");
    }

    return sb.toString();
  }




  public void verify(Work work){
    if(work.getNumberOfCopies() == 0){
      _listOfWorks.remove(work);
    }
    if(work instanceof Book){
      Book workb = (Book)work;
      for(Creator c: workb.getCreators()){
        if(c.getWorkList().isEmpty()){
          _listOfCreators.remove(c);
        }
      }
    }
    else{
      Dvd workd = (Dvd)work;
      if(workd.getCreator().getWorkList().isEmpty()){
        _listOfCreators.remove(workd.getCreator());
      }
    }
  }
  
  public void alterInvWork(int number, int workId){
    for(Work w : _listOfWorks){
      if(w.getWorkId() == workId){
        int numberOfCopies = w.getNumberOfCopies();
        if(number > 0){
            numberOfCopies += number;
        }
        else{
            if(numberOfCopies <= number){
                numberOfCopies = 0;
                verify(w);
            }
            else{
                numberOfCopies -= number;
            }
        }
      }
    }
  }

  public void registerDvd(String igac, Creator creator, String title, int price, int numberOfCopies, Category type){
    Dvd newDvd = new Dvd(igac, creator, title, price, numberOfCopies, type, _nextWorkId);
    _listOfWorks.add(newDvd);
    creator.addWork(newDvd);
    _nextWorkId++;
  }

  public void registerBook(String isbn, int price, String title, int numberOfCopies, List<Creator> creators, Category type) {
    Book newBook = new Book(isbn, price, title, numberOfCopies, creators, type, _nextWorkId);
    _listOfWorks.add(newBook);
    for (Creator c : creators) {
      c.addWork(newBook);
    }
    _nextWorkId++;
  } 


  public Creator registerCreator(String name){
    Creator newCreator =new Creator(name);
    _listOfCreators.add(newCreator);
    return newCreator;
  }


  //time
    public void advanceDays(int days){
    _currentDay += days;
  }

  //User

  public int registerUser(String name, String email){
    _listOfUsers.add(new User(name, email, _nextUserId));
    int userId = _nextUserId;
    _nextUserId++;
    return userId;
  }


  public String listUser(int userId) throws NoSuchUserExceptionCore{
    for(User u : _listOfUsers){
      if(u.getUserId() == userId){
        return u.toString();
      }
    }
    throw new NoSuchUserExceptionCore(userId);
  }

  public String listUsers() {
    _listOfUsers.sort(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER).thenComparingInt(User::getUserId));

    StringBuilder sb = new StringBuilder();
    for (User u : _listOfUsers) {
        sb.append(u.toString()).append("\n");
    }

    return sb.toString();
}


  
}

  
