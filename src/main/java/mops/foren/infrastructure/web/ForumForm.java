package mops.foren.infrastructure.web;

import mops.foren.domain.model.Forum;

import javax.validation.constraints.Size;

public class ForumForm {
    public String title;
    @Size(min = 1, max = 100)
    public String description;

    public ForumForm(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Forum map() {
        return Forum.builder().
                description(this.description)
                .title(this.title)
                .build();
    }
}
