package mops.foren.domain.model;

public class ForumId implements Id {

    private final Long id;

    public ForumId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
