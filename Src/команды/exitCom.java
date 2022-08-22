package commands;

import data.student;

import java.util.*;

public class exitCom extends Command{
    public exitCom() {
        description = "Завершение работы";
        serialNumber = 5;
    }
    @Override
    public String execute(List<student> students) {
        return "Программа завершена.";
    }
}
