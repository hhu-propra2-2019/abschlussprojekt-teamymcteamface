package mops.foren.domain.model;

import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
public class Post {
    final User author;

    final Date creationDate;

    boolean edited;

    List<Content> contentList;

    void edit(Post post) {
        throw new UnsupportedOperationException();
    }

    void addContent(Content content) {
        throw new UnsupportedOperationException();
    }

    void deleteContent(Content content) {
        throw new UnsupportedOperationException();
    }
}
