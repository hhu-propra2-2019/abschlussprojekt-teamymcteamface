package mops.foren.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Value;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@AllArgsConstructor
public class ThreadForm {
    private static final int MAX_TITLE_LENGTH = 40;
    private static final int MIN_TITLE_LENGTH = 3;

    @NotNull(message = "Thread title cannot be null.")
    @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH,
            message = "Thread title must be between "
                    + MIN_TITLE_LENGTH + " and "
                    + MAX_TITLE_LENGTH + " characters.")
    private final String title;

    // For now only check for Null until there is a content class
    @NotBlank(message = "Content cannot be blank.")
    private String content;


    /**
     * Method to build a thread from the content and title.
     *
     * @param user    The user that created the thread
     * @param topicId The topic id that the thread belongs to
     * @return The thread
     */
    public Thread getThread(User user, TopicId topicId) {
        return Thread.builder()
                .title(this.title)
                .description(this.content)
                .topicId(topicId)
                .author(user)
                .build();
    }
}
