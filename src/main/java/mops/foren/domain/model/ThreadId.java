package mops.foren.domain.model;

public class ThreadId implements Id {

    private final Long id;

    public ThreadId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
