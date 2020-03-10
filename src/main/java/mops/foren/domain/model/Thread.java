package mops.foren.domain.model;

import java.util.List;

public class Thread {
    String title;

    String description;

    List<Post> posts;

    void addPost(Post post) {
        throw new UnsupportedOperationException();
    }


    void deletePost(Post post) {
        throw new UnsupportedOperationException();
    }

    Post showFirstPost() {
        throw new UnsupportedOperationException();
    }

}
