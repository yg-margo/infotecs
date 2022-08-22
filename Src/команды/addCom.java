package commands;

import data.student;
import main.Main;

import java.util.*;
public class addCom extends Command{
    public addCom() {
        description = "Добавить студента: ";
        serialNumber = 3;
    }
    @Override
    public String execute(List<student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Имя студента: ");
        String name = sc.nextLine();
        try {
            students.add(new student(name, Main.idPool.first()));
            Main.idPool.remove(Main.idPool.first());
            changedList = true;
            return "Студент добавлен.";
        }
        catch (NoSuchElementException e){
            return "Студент не добавлен. id закончились.";
        }
    }
}
