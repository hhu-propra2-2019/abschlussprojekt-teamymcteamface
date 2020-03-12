package mops.foren.domain.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Thread {
    private final ThreadId id;

    private final TopicId topicId;

    private String title;

    private String description;

}
