package data;

public class student implements Comparable<student>{
    private final String name;
    private final int id;
    public student(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "id: " + id + ", name: " + name;
    }
    @Override
    public int compareTo(student student) {
        return this.name.compareTo(student.name);
    }
}
