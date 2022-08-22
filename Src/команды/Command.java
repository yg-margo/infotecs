package commands;

import data.student;

import java.util.*;

public abstract class Command {
    protected boolean changedList = false;
    protected String description;
    protected int serialNumber;
    public String getDescription(){
        return description;
    }
    public int getSerialNumber() {
        return serialNumber;
    }
    public boolean isChangedList() {return changedList;}
    public abstract String execute(List<student> students);
}
