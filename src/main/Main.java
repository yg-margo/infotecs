package main;

import commands.Command;
import commands.comManager;
import data.student;
import sun.net.ftp.FtpProtocolException;
import utils.FTPConnection;
import utils.parser;


import java.io.*;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public class Main {
    private static FTPConnection ftpConnection;
    private static String hostName;
    private static String login;
    private static String password;
    public static TreeSet<Integer> idPool = new TreeSet<>();
    private static final parser jsonParser = new parser();
    private static final int MAX_CAPACITY = 1000000;
    private static final Integer PORT = 21;
    private static List<student> students;
    private static comManager comManager;
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите имя хоста:");
        String hostName = console.nextLine();
        System.out.println("Введите логин:");
        String login = console.nextLine();
        System.out.println("Введите пароль:");
        String password = console.nextLine();

        for (int i = 0; i < MAX_CAPACITY; i++) idPool.add(i);

        try {
            ftpConnection = new FTPConnection(hostName, login, password);
            ftpConnection.establishConnection();
        } catch (UnknownHostException e) {
            System.err.println("Не удалось подключиться к серверу");
            return;
        }
        catch (FtpProtocolException | IOException e) {
            System.out.println("Ошибка аутентификации");
            return;
        }

        try {
            String res = getFileTextFromServer("/htdocs/students.json");
            students = jsonParser.fromJSONToStudentsList(res);
            comManager = new comManager(ftpConnection);
            for (Command command : comManager.getCommandList()) System.out.println(
                    command.getSerialNumber() + ". " + command.getDescription());
            while (true) {
                try {
                    System.out.println("Введите номер команды:");
                    System.out.print("$");
                    String input = console.nextLine();
                    Command com = getCommandFromInput(input);
                    comManager.execute(com, students);
                    if (com.getSerialNumber() == 5) {
                        break;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Неверный формат команды (введите число от 1 до 5)");
                }
            }
        } catch (FtpProtocolException | IOException e) {
            System.err.println("Файл students.json не найден на сервере");
        }
    }
    public static Command getCommandFromInput(String input) {
        List<Command> commands = comManager.getCommandList();
        for (Command command : commands) {
            if (Integer.parseInt(input) == command.getSerialNumber()) {
                return command;
            }
        }
        return null;
    }
    public static String getFileTextFromServer(String path) throws FtpProtocolException, IOException {
        InputStream is =
                ftpConnection.getFTPClient().getFileStream(path);
        InputStreamReader isr =
                new InputStreamReader(is);
        String result = new String("");
        while (true) {
            int b = isr.read();
            if (b != -1) result += (char) b;
            else break;
        }
        return result;
    }
}
