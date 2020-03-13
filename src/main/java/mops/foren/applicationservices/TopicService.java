package mops.foren.applicationservices;

import mops.foren.domain.model.*;
import mops.foren.domain.repositoryabstraction.ITopicRepository;

import java.util.ArrayList;
import java.util.List;

public class TopicService {

    private ITopicRepository topicRepository;

    public TopicService(ITopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getTopics(ForumId forumId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method to get mocked topics.
     *
     * @param forumId The forum id
     * @return A list of the topics belonging to this forum
     */
    public List<Topic> getMockTopics(ForumId forumId) {
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(new TopicId(1L), new ForumId(1L), "Ankündigungen",
                "Hier landen alle offiziellen Ankündigungen"));
        topics.add(new Topic(new TopicId(2L), new ForumId(1L), "Diskussion",
                "Offenes Diskussionsforum für alle Teilnehmer"));
        topics.add(new Topic(new TopicId(2L + 1L), new ForumId(1L), "Aufgaben",
                "Hier gehts nur um Aufgabenstellungen und Probleme dabei"));
        topics.add(new Topic(new TopicId(2L + 2L), new ForumId(1L), "Offtopic",
                "Memes gehen hier rein"));
        return topics;
    }

    /**
     * Method to add a topic.
     *
     * @param topic   The thread to add
     * @param user    The user that wants to create the topic
     * @param forumId The forumId the topic belongs to
     */
    public void addTopic(Topic topic, User user, ForumId forumId) {
        if (user.checkPermission(forumId, Permission.CREATE_TOPIC)) {
            // ADD
        }
    }
}
