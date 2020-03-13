package mops.foren.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Forum {
    private final Long id;

    private String title;

    private String description;

    public void sendEmailToUsers() {
        throw new UnsupportedOperationException();
    }
}
