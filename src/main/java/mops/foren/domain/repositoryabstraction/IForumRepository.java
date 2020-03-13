package mops.foren.domain.repositoryabstraction;

import mops.foren.domain.model.Forum;

import java.util.List;

public interface IForumRepository {
    List<Forum> getForums(List<Long> forumIds);
}
