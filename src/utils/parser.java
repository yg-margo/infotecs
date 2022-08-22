package utils;

import data.student;
import main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class parser {
    public List<student> fromJSONToStudentsList(String json) {
        String jsonFile = editing(json);
        String[] words = jsonFile.split("\"\"");
        List<student> result = new ArrayList<>();
        words[0] = words[0].substring(1);
        words[words.length-1] = words[words.length - 1].substring(0,
                words[words.length - 1].length() - 1);
        if (words[0].equals("students")) {
            for (int i = 1; i < words.length; i += 4) {
                result.add(new student(words[i + 3],
                        Integer.parseInt(words[i + 1])));
                Main.idPool.remove(Integer.parseInt(words[i + 1]));
            }
        }
        return result;
    }

    private String editing(String json) {
        StringBuilder jsonFile = new StringBuilder();
        String pattern = "[\\d\\w\"]";
        boolean inBrackets = false;
        for (char chr: json.toCharArray()){
            if (inBrackets) {
                if (chr == '"') inBrackets = false;
                jsonFile.append(chr);
            }
            else {
                if (chr == '"') inBrackets = true;
                if (Pattern.matches(pattern, Character.toString(chr)))
                    jsonFile.append(chr);
            }
        }
        return jsonFile.toString();
    }
    public String studentFromJson(List<student> students) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        result.append("\"students\": [\n");
        for (int i = 0; i < students.size(); i++){
            result.append("{\n");
            result.append("\"id\": \"").append(students.get(i).getId()).append("\",\n");
            result.append("\"name\": \"").append(students.get(i).getName()).append("\"\n");
            result.append("}");
            if (i != students.size() - 1) result.append(",");
            result.append("\n");
        }
        result.append("]\n");
        result.append("}");
        return result.toString();
    }
}
