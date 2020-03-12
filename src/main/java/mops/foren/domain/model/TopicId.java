package mops.foren.domain.model;

public class TopicId implements Id {

    private final Long id;

    public TopicId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
