package global.goit.romashko.concurrency.web;

import global.goit.romashko.concurrency.web.user.User;
import global.goit.romashko.concurrency.web.util.HttpUtil;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class Task1Demo {

    private final static String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final static String USERS_URL = BASE_URL + "users/";

    public static void main(String[] args) throws IOException, InterruptedException {
        List<User> serverUserList = HttpUtil.getAllUsers(URI.create(BASE_URL + "users"));


        //Task 1.1 - create new resource
        User newUser = new User(-1, "default", "default@xyz.zz");
        User addedUser = HttpUtil.createUser(URI.create(USERS_URL), newUser);

        int maxId = -1;
        for (User user : serverUserList) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }

        String result11 = "Task 1.1 - fail.";
        if (addedUser.getId() == maxId + 1) {
            result11 = "Task 1.1 - success";
        }
        System.out.println(result11
                + " (initial id = " + newUser.getId()
                + ", maximum id = " + maxId
                + ", received new user id = " + addedUser.getId() + ")."
                + "\n");


        //Task 1.2 - update resource
        User updatingUser = new User(serverUserList.get(0).getId(), "defaultUsername", "default@email.com");

        User returnedUserUpdateById = HttpUtil.updateUserById(URI.create(USERS_URL), "1", updatingUser);

        String result12 = "Task 1.2 - fail.";
        if (updatingUser.getUsername().equals(returnedUserUpdateById.getUsername())) {
            result12 = "Task 1.2 - success.";
        }
        System.out.println(result12);
        System.out.println(" ".repeat(4) + "Existing user:\n"
                + " ".repeat(8) + serverUserList.get(0) + "\n"
                + " ".repeat(4) + "Updating user:\n"
                + " ".repeat(8) + updatingUser + "\n"
                + " ".repeat(4) + "Returned user:\n"
                + " ".repeat(8) + returnedUserUpdateById + "\n");


        //Task 1.3 - delete resource
        int responseStatusOnDelete = HttpUtil.deleteUserById(URI.create(USERS_URL), "1");
        String result13 = "Task 1.3 - fail.";
        if (responseStatusOnDelete / 100 == 2) {
            result13 = "Task 1.3 - success. Returned with response status code "
                    + responseStatusOnDelete + ".\n";
        }
        System.out.println(result13);


        //Task 1.4 - get all users
        int responseStatusAllUsers = HttpUtil.getAllUsersStatusCode(URI.create(USERS_URL));
        String result14 = "Task 1.4 - fail.";
        if (responseStatusOnDelete / 100 == 2) {
            result14 = "Task 1.4 - success. Returned with response status code "
                    + responseStatusAllUsers + ".\n";
        }
        System.out.println(result14);


        //Task 1.5 - get user using id
        String userIdById = "1";
        User userById = HttpUtil.getUserById(URI.create(USERS_URL), userIdById);

        String result15 = "Task 1.5 - fail.";
        if (serverUserList.get(0).equals(userById)) {
            result15 = "Task 1.5 - success.";
        }
        System.out.println(result15);
        System.out.println(" ".repeat(4) + "Existing user from all users (1st in the list):\n"
                + " ".repeat(8) + serverUserList.get(0) + "\n"
                + " ".repeat(4) + "Returned user with id = " + userIdById + "\n"
                + " ".repeat(8) + userById + "\n");

        //Task 1.6 - get user using username
        String username = "Antonette";
        User existingUserByUsername = serverUserList.get(1);
        User userByUsername = HttpUtil.getUserByUsername(URI.create(USERS_URL), username);

        String result16 = "Task 1.6 - fail.";
        if (existingUserByUsername.equals(userByUsername)) {
            result16 = "Task 1.6 - success.";
        }
        System.out.println(result16);
        System.out.println(" ".repeat(4) + "Existing user from all users (2nd in the list):\n"
                + " ".repeat(8) + existingUserByUsername + "\n"
                + " ".repeat(4) + "Returned user with username = " + username + "\n"
                + " ".repeat(8) + userByUsername + "\n");
    }
}
