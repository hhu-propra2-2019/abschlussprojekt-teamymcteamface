package mops.foren.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class Forum {
    private final ForumId id;

    private String title;

    private String description;

    public String getLatestUpdate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }

    public String getStringId() {
        return this.id.getId().toString();
    }

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }
}
