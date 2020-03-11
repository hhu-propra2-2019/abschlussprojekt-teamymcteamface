package mops.foren.domain.model;

public class ForumId implements Id {

    private Long id;

    public ForumId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
