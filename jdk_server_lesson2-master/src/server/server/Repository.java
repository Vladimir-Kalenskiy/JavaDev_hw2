package server.server;

public interface Repository {
    void saveInLog(String string);
    String readLog();
}
