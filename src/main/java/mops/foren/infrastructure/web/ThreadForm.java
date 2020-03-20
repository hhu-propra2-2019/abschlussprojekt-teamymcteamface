package mops.foren.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Value;
import mops.foren.domain.model.Thread;
import mops.foren.domain.model.TopicId;
import mops.foren.domain.model.User;

@Value
@AllArgsConstructor
public class ThreadForm {
    private String title;
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
                .author(user.getName())
                .build();
    }
}
