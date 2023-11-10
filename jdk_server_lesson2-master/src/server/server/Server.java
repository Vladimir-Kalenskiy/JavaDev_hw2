package server.server;

import server.client.Client;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private boolean work;
    private List<Client> clientList;
    private ServerView serverView;
    private Repository repository;

    public Server(ServerView serverView, Repository repository) {

        this.serverView = serverView;
        this.repository = repository;
        clientList = new ArrayList<>();
    }

    public void start() {
        if (work){
            printText("Сервер уже запущен.");
        } else {
            work = true;
            printText("Сервер запущен.");
        }
    }

    public void stop() {
        if (!work){
            printText("Сервер уже остановлен.");
        } else {
            work = false;
            for (Client client: clientList){
                disconnectUser(client);
            }
            printText("Сервер остановлен.");
        }
    }

    public boolean connectUser(Client client) {
        if (!work) {
            return false;
        }
        clientList.add(client);
        return true;
    }

    public void disconnectUser(Client client) {
            clientList.remove(client);
        if (client != null) {
            client.disconnect();
        }
    }

    public void sendMessage(String text) {
        if (!work) {
            return;
        }
        printText(text);
        answerAll(text);
        saveInLog(text);
    }

    private void saveInLog(String text) {
        repository.saveInLog(text);
    }

    public String getHistory() {
        return repository.readLog();
    }

    private void answerAll(String text) {
        for (Client client: clientList) {
            client.serverAnswer(text);
        }
    }

    private void printText(String text){
        serverView.showMessage(text + "\n");
    }
}
