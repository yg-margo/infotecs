package utils;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.IOException;
import java.net.*;

public class FTPConnection {
    private FtpClient ftp;
    private final String hostName;
    private final String login;
    private final String password;
    public FTPConnection(String hostName, String login, String password) {
        this.hostName = hostName;
        this.login = login;
        this.password = password;
    }

    public void establishConnection() throws FtpProtocolException, IOException {
        int PORT = 21;
        SocketAddress address = new InetSocketAddress(hostName, PORT);
        ftp = FtpClient.create();
        ftp.connect(address);
        ftp.login(login, password.toCharArray());
    }
    public FtpClient getFTPClient(){
        return ftp;
    }
}
