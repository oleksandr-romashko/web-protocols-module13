package global.goit.romashko.concurrency.web.comment;

import java.util.Objects;

public class Comment {
    private final int postId;
    private final int id;
    private final String name;
    private final String email;
    private final String body;

    public Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (postId != comment.postId) return false;
        if (id != comment.id) return false;
        if (!Objects.equals(name, comment.name)) return false;
        if (!Objects.equals(email, comment.email)) return false;
        return Objects.equals(body, comment.body);
    }

    @Override
    public int hashCode() {
        int result = postId;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment {\n" +
                "postId=" + postId + ",\n" +
                "id="     + id     + ",\n" +
                "name='"  + name   + "',\n" +
                "email='" + email  + "',\n" +
                "body='"  + body   + "',\n}";
    }
}
