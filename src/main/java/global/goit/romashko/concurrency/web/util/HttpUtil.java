package global.goit.romashko.concurrency.web.util;

import com.google.gson.Gson;
import global.goit.romashko.concurrency.web.comment.Comment;
import global.goit.romashko.concurrency.web.post.Post;
import global.goit.romashko.concurrency.web.task.Task;
import global.goit.romashko.concurrency.web.user.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtil {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();


    public static List<User> getAllUsers(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return List.of(GSON.fromJson(response.body(), User[].class));
    }

    public static int getAllUsersStatusCode(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static User getUserById(URI uri, String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + userId))
                .GET()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response;
        try {
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Exception while getting User by ID" + e);
        }
        return GSON.fromJson(response.body(), User.class);
    }

    public static User getUserByUsername(URI uri, String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "?username=" + username))
                .GET()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return List.of(GSON.fromJson(response.body(), User[].class)).get(0);
    }

    public static User createUser(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User updateUserById(URI uri, String userId, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + userId))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static int deleteUserById(URI uri, String userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + userId))
                .DELETE()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static List<Post> getLatestPostsByUser(URI usersUri, String userId) throws IOException, InterruptedException {
        final URI path = URI.create(usersUri + "/" + userId + "/posts");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(path)
                .GET()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return List.of(GSON.fromJson(response.body(), Post[].class));
    }

    public static List<Comment> getCommentsByPostId(URI postsUri, int postId) throws IOException, InterruptedException {
        URI uriById = URI.create(postsUri + "/" + postId + "/comments");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriById)
                .GET()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return List.of(GSON.fromJson(response.body(), Comment[].class));
    }

    public static List<Task> getTasksByUser(URI usersUri, User user) throws IOException, InterruptedException {
        URI todosUri = URI.create(usersUri + "/" + user.getId() + "/todos");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(todosUri + "?completed=false"))
                .GET()
                .header("Content-type", "application/json; charset=UTF-8")
                .build();

        final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return List.of(GSON.fromJson(response.body(), Task[].class));
    }
}
