package mops.foren.infrastructure.web;

import mops.foren.domain.model.Forum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static mops.foren.infrastructure.web.ValidationService.*;

public class ForumForm {

    @NotNull(message = "Forum title cannot be null.")
    @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH,
            message = "Forum title must be between "
                    + MIN_TITLE_LENGTH + " and "
                    + MAX_TITLE_LENGTH + " characters.")
    private final String title;

    @NotNull(message = "Forum description cannot be null.")
    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_DESCRIPTION_LENGTH,
            message = "Forum description must be between "
                    + MIN_DESCRIPTION_LENGTH + " and "
                    + MAX_DESCRIPTION_LENGTH + " charcaters.")
    private final String description;

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
