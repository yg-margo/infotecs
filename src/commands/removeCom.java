package commands;

import data.student;
import main.Main;

import java.util.*;

public class removeCom extends Command{
    public removeCom() {
        description = "Удаление студента по id";
        serialNumber = 4;
    }
    @Override
    public String execute(List<student> students) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите id студента");
            int id = sc.nextInt();
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == id) {
                    Main.idPool.add(students.get(i).getId());
                    students.remove(i);
                    changedList = true;
                    return "Студент с id = " + id + " был удален из списка";
                }
            } return "В списке нет студента с таким id";
        } catch (InputMismatchException e){
            return "Неверный формат id";
        }
    }
}
