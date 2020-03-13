package mops.foren.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Forum {
    private final ForumId id;

    private String title;

    private String description;

    public LocalDateTime getLatestUpdate() {
        return LocalDateTime.now();
    }

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }
}
