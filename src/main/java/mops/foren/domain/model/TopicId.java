package mops.foren.domain.model;

public class TopicId implements Id {

    private Long id;

    public TopicId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
