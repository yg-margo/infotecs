package commands;

import data.student;

import java.util.Collections;
import java.util.List;

public class getCom extends Command{
    public getCom() {
        description = "Полученить список по имени студентов: ";
        serialNumber = 1;
    }

    @Override
    public String execute(List<student> students) {
        if (students.size() == 0) {
            return "Данные о студенте в файле отсутстввуют.";
        }
        StringBuilder result = new StringBuilder();
        Collections.sort(students);
        for (data.student student: students) {
            result.append(student.toString()).append("\n");
        }
        return result.toString();
    }
}
