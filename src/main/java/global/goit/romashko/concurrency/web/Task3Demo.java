package global.goit.romashko.concurrency.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.goit.romashko.concurrency.web.task.Task;
import global.goit.romashko.concurrency.web.user.User;
import global.goit.romashko.concurrency.web.util.HttpUtil;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class Task3Demo {
    private final static String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final static String USERS_URL = BASE_URL + "users";

    public static void main(String[] args) {
        String userId = "1";
        User user = HttpUtil.getUserById(URI.create(USERS_URL), userId);

        List<Task> tasks;
        try {
            tasks = HttpUtil.getTasksByUser(URI.create(USERS_URL), user);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Exception while getting tasks for User" + e);
        }

        printTasks(tasks);
    }

    private static void printTasks(List<Task> tasks) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(tasks));
    }
}
