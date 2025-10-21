package bci.core;

import java.io.*;
import java.util.*;

import bci.core.exception.*;

public class Library implements Serializable {
    private Map<Integer, Work> _worksById;
    private Map<Integer, User> _usersById;
    private Map<String, Creator> _creatorsByName;
    //os users guardam suas proprias requests

    private int _currentDay;
    private int _nextWorkId;
    private int _nextUserId;

    private RuleChecker _ruleChecker;

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202501101348L;

    void importFile(String filename) throws UnrecognizedEntryException, IOException {
        MyParser parser = new MyParser(this);
        parser.parseFile(filename);
    }

    Library() {
        _worksById = new LinkedHashMap<>();
        _usersById = new LinkedHashMap<>();
        _creatorsByName = new LinkedHashMap<>(); 
        _nextUserId = _nextWorkId = _currentDay = 1;
        _ruleChecker = new RuleChecker(); 
    }

    // ---------- GETTERS ----------
    boolean validUser(int userId){
        if(_usersById.get(userId) == null) return false;
        else return true;
    }

    boolean validWork(int workId){
        if(_worksById.get(workId) == null) return false;
        else return true;
    }

    List<Work> getListOfWorks() {
        return Collections.unmodifiableList(new ArrayList<>(_worksById.values()));
    }

    List<Creator> getListOfCreators() {
        return Collections.unmodifiableList(new ArrayList<>(_creatorsByName.values()));
    }

    List<User> getListOfUsers() {
        return Collections.unmodifiableList(new ArrayList<>(_usersById.values()));
    }

    // ---------- WORK HELPERS ----------
    private void addWorkInternal(Work work) {
      _worksById.put(work.getWorkId(), work);
    }

    private void removeWorkInternal(Work work) {
        if (work == null) return;

        _worksById.remove(work.getWorkId());
        removeCreators(work.listCreators());
    }

    private Work getWorkById(int workId) {
        return _worksById.get(workId);
    }

    // ---------- MENU / LISTING ----------
    String listWork(int workId) throws NoSuchWorkExceptionCore {
        Work w = getWorkById(workId);
        if (w != null) return w.toString();
        throw new NoSuchWorkExceptionCore(workId);
    }

    String listWorks() {
        List<Work> copy = new ArrayList<>(_worksById.values());
        copy.sort(Comparator.comparingInt(Work::getWorkId));

        StringBuilder sb = new StringBuilder();
        for (Work w : copy)
            sb.append(w.toString()).append("\n");
        return sb.toString();
    }

    String listWorksByCreators(String name) throws NoSuchCreatorExceptionCore {
        Creator c = _creatorsByName.get(name);
        if (c == null) throw new NoSuchCreatorExceptionCore(name);

        List<Work> works = new ArrayList<>(c.getWorkList());
        if (works.isEmpty()) throw new NoSuchCreatorExceptionCore(name);

        works.sort(Comparator.comparing(w -> w.getTitle().toLowerCase()));

        StringBuilder sb = new StringBuilder();
        for (Work w : works)
            sb.append(w).append("\n");
        return sb.toString();
    }

    String performSearch(String search) {
        if (search == null) return null;
        String searchLower = search.trim().toLowerCase();
        if (searchLower.isEmpty()) return null;

        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (Work w : _worksById.values()) {
            String workStr = w.toString();
            if (workStr.toLowerCase().contains(searchLower)) {
                sb.append(workStr).append("\n");
                found = true;
            }
        }
        return found ? sb.toString() : null;
    }

    // ---------- VERIFY / INVENTORY ----------
    void verify(Work work) {
        if (work.getNumberOfCopies() == 0) {
            removeWorkInternal(work);
        }
    }

    void alterInvWork(int quantityChange, int workId) throws NoSuchWorkExceptionCore, NotEnoughInventoryExceptionCore {
        Work work = getWorkById(workId);
        if (work == null) throw new NoSuchWorkExceptionCore(workId);

        int current = work.getNumberOfCopies();
        if (quantityChange < 0){
            int remove = -quantityChange;
            if (current < remove) throw new NotEnoughInventoryExceptionCore(work.getTitle());
            work.setNumberOfCopies(current - remove);
            verify(work);
        } else {
            work.setNumberOfCopies(current + quantityChange); // acho q falta a verificação para caso passe do limite
        }
    }

    // ---------- REGISTER WORKS / CREATORS ----------
    void registerDvd(String igac, Creator creator, String title, int price, int numberOfCopies, Category type) {
        int id = _nextWorkId++;
        Dvd newDvd = new Dvd(igac, creator, title, price, numberOfCopies, type, id);

        Creator stored = _creatorsByName.get(creator.getName());
        if (stored == null) {
            _creatorsByName.put(creator.getName(), creator);
            stored = creator;
        }

        stored.addWork(newDvd);
        addWorkInternal(newDvd);
    }

    void registerBook(String isbn, int price, String title, int numberOfCopies, List<Creator> creators, Category type) {
        int id = _nextWorkId++;
        Book newBook = new Book(isbn, price, title, numberOfCopies, creators, type, id);

        for (Creator c : creators) {
            Creator stored = _creatorsByName.get(c.getName());
            if (stored == null) {
                _creatorsByName.put(c.getName(), c);
                stored = c;
            }
            stored.addWork(newBook);
        }

        addWorkInternal(newBook);
    }

    Creator registerCreator(String name) {
        Creator existing = _creatorsByName.get(name);
        if (existing != null) return existing;

        Creator newCreator = new Creator(name);
        _creatorsByName.put(name, newCreator);
        return newCreator;
    }

    // ---------- TIME ----------
    int getCurrentDay() {
        return _currentDay;
    }

    void advanceDays(int day) {
        _currentDay += day;
        checkUserState(day);
    }

    // ---------- USERS ----------
    int registerUser(String name, String email) {
        int id = _nextUserId++;
        User u = new User(name, email, id);
        _usersById.put(id, u);
        return id;
    }

    String listUser(int userId) throws NoSuchUserExceptionCore {
        User u = _usersById.get(userId);
        if (u != null) return u.toString();
        throw new NoSuchUserExceptionCore(userId);
    }

    String listUsers() {
        List<User> copy = new ArrayList<>(_usersById.values());
        copy.sort(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER).thenComparingInt(User::getUserId));

        StringBuilder sb = new StringBuilder();
        for (User u : copy)
            sb.append(u.toString()).append("\n");
        return sb.toString();
    }

    void checkUserState(int day){
        for(User u : _usersById.values())
            u.checkRequisitions(day);
    }

  //------------Creators-------------

  void removeCreators(List<Creator> creators){
    for(Creator creator : creators)
        _creatorsByName.remove(creator.getName());
  }


  //------------Requisitions-------------

  int requestWork(int userId, int workId) throws CouldNotRequestException, NotEnoughInventoryExceptionCore{
    User user = _usersById.get(userId);

    Work work = _worksById.get(workId);

    _ruleChecker.checkRules(work, user);

    Request request = new Request(userId, workId, user.getBehavior().getReqTime(work.getAvailableCopies()));
    user.addUserRequest(request);

    return request.getEndOfRequest();
  }

  int returnWork(int userId, int workId){
    return _usersById.get(userId).removeUserRequest(workId);
  }

  void payFine(int userId, int quant, int day){
    _usersById.get(userId).payFine(quant, day);
  }


}
