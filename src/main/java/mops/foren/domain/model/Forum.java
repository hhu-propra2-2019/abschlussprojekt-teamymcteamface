package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Forum {
    private final ForumId id;

    private String title;

    private String description;

    private LocalDateTime lastChange;

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method formats the lastChange into a nice String representation.
     * "Es gab noch keinen Beitrag in diesem Forum" will be returned if the Thread has no posts.
     *
     * @return a Date formatted in a String.
     */
    public String getFormattedDate() {
        if (this.lastChange == null) {
            return "Es gab noch keinen Beitrag in diesem Thread";
        }
        return this.lastChange.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }
}
