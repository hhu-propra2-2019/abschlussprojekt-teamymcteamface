package mops.foren.applicationservices;

import mops.foren.model.repositoryabstraction.IForumRepository;
import org.springframework.stereotype.Service;

@Service
public class ForumService {

    IForumRepository forumRepository;

    public ForumService() {

    }
}
