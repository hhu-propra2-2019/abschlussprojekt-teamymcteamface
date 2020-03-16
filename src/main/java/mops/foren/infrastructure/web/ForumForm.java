package mops.foren.infrastructure.web;

import mops.foren.domain.model.Forum;

import javax.validation.constraints.Size;

public class ForumForm {
    private static final int MAX_DESCRIPTION_LENGTH = 100;
    public String title;
    @Size(min = 1, max = MAX_DESCRIPTION_LENGTH)
    public String description;

    public ForumForm(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * Maps the ForumForm to the matching Forum object.
     *
     * @return matching Forum.
     */
    public Forum getForum() {
        return Forum.builder()
                .description(this.description)
                .title(this.title)
                .build();
    }
}
