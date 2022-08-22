package commands;

import data.student;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class findCom extends Command{
    public findCom() {
        description = "Полученить информацию о студенте по id: ";
        serialNumber = 2;
    }

    @Override
    public String execute(List<student> students) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите id студента: ");
            int id = scanner.nextInt();
            for (data.student student : students) {
                if (student.getId() == id) {
                    return student.toString();
                }
            }
            return "Студент с id = " + id + " не найден";
        }catch (InputMismatchException e){
            return "Неверный id.";
        }
    }
}
