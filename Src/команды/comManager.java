package commands;

import data.student;
import sun.net.ftp.FtpProtocolException;
import utils.*;
import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
public class comManager {
    private final FTPConnection ftpConnection;
    private final List<Command> commandList = new ArrayList<>();
    public comManager(FTPConnection ftpConnection) {
        this.ftpConnection = ftpConnection;
        commandList.add(new getCom());
        commandList.add(new findCom());
        commandList.add(new addCom());
        commandList.add(new removeCom());
        commandList.add(new exitCom());
    }
    public void execute(Command command, List<student> students) {
        if (command != null) System.out.println(command.execute(students));
        else throw new NumberFormatException();
        boolean noUpdate = false;
        boolean update = true;
        while (!noUpdate && update && command.isChangedList()) {
            noUpdate = updateServer(students);
            if (!noUpdate) {
                System.out.println("Сохранить данные еще раз? (yes)");
                Scanner sc = new Scanner(System.in);
                String inp = sc.nextLine();
                if (inp.equals("yes")) update = true;
                else update = false;
            }
        }
    }
    public List<Command> getCommandList(){
        return commandList;
    }
    public boolean updateServer(List<student> students) {
        String output = new parser().studentFromJson(students);
        try {
            File file = File.createTempFile("students", ".json");
            FileOutputStream f = new FileOutputStream(file);
            f.write(output.getBytes(StandardCharsets.UTF_8));
            FileInputStream fi = new FileInputStream(file);
            if (!ftpConnection.getFTPClient().isConnected()) ftpConnection.establishConnection();
            ftpConnection.getFTPClient().putFile("/htdocs/students.json", fi);
            System.out.println("Данные обновлены.");
            return true;
        } catch (FtpProtocolException | IOException e) {
            System.out.println("Ошибка, данные на сервер не добавлены.");
            return false;
        }
    }
}
