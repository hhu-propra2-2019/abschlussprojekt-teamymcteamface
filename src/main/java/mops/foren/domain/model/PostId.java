package mops.foren.domain.model;

public class PostId implements Id {

    private final Long id;

    public PostId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
