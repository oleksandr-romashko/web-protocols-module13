package global.goit.romashko.concurrency.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.goit.romashko.concurrency.web.comment.Comment;
import global.goit.romashko.concurrency.web.post.Post;
import global.goit.romashko.concurrency.web.user.User;
import global.goit.romashko.concurrency.web.util.HttpUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class Task2Demo {

    private final static String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final static String USERS_URL = BASE_URL + "users";
    private final static String POSTS_URL = BASE_URL + "posts";

    public static void main(String[] args) {
        final String userId = "1";
        Post latestPost = getLatestPost(userId);
        List<Comment> comments = getComments(latestPost);
        printComments(comments);
        User user = HttpUtil.getUserById(URI.create(USERS_URL), userId);
        writeCommentsToFile(user, latestPost, comments);
    }
    private static Post getLatestPost(String userId)  {
        List<Post> latestPosts;
        try {
            latestPosts = new java.util.ArrayList<>(HttpUtil.getLatestPostsByUser(URI.create(USERS_URL), userId));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Exception while getting latest post by ID" + e);
        }

        latestPosts.sort((o1, o2) -> {
            if (o1.getId() < o2.getId()) {
                return -1;
            } else if (o1.getId() > o2.getId()) {
                return 1;
            }
            return 0;
        });

        return latestPosts.get(latestPosts.size() - 1);
    }

    private static List<Comment> getComments(Post post) {
        int postId = post.getId();
        try {
            return HttpUtil.getCommentsByPostId(URI.create(POSTS_URL), postId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Exception while getting comments by ID" + e);
        }
    }


    private static void printComments(List<Comment> comments) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(comments));
    }

    private static void writeCommentsToFile(User user, Post latestPost, List<Comment> comments) {
        String fileName = "user-" + user.getId() + "-post-" + latestPost.getId() + "-comments";
        File file = new File(fileName);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(comments);

        try (FileWriter writer = new FileWriter(file))
        {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Exception while writing users to file: " + file
                    + " " + e.getMessage());
        }

        System.out.println("\nComments were saved to " + file.getAbsolutePath());
    }
}
