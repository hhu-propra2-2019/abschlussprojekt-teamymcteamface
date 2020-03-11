package mops.foren.domain.model;

public class ThreadId implements Id {

    private Long id;

    public ThreadId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
