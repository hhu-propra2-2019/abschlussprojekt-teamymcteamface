package mops.foren.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Value;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static mops.foren.infrastructure.web.ValidationService.*;

@Value
@AllArgsConstructor
public class ThreadForm {

    @NotNull(message = "Thread title cannot be null.")
    @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH,
            message = "Thread title must be between "
                    + MIN_TITLE_LENGTH + " and "
                    + MAX_TITLE_LENGTH + " characters.")
    private final String title;

    @NotNull(message = "Thread title cannot be null.")
    @Size(min = MIN_CONTENT_LENGTH, max = MAX_CONTENT_LENGTH,
            message = "Thread content must be between "
                    + MIN_CONTENT_LENGTH + " and "
                    + MAX_CONTENT_LENGTH + " characters.")
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
