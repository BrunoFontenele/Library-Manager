package bci.core;

public abstract class Rule {
    private final int _ruleId;

    protected Rule(int ruleId){
        _ruleId = ruleId;
    }

    abstract void check(Work work, User user);

}
