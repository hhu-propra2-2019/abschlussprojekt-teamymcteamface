package mops.foren.applicationservices;

import mops.foren.domain.repositoryabstraction.IForumRepository;
import org.springframework.stereotype.Service;

@Service
public class ForumService {

    IForumRepository forumRepository;

    public ForumService() {

    }
}
