package mops.foren;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.User;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import mops.foren.infrastructure.persistence.repositories.ForumRepositoryImpl;
import mops.foren.infrastructure.persistence.repositories.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ForumRepositoryImplTests {

    private final ForumRepositoryImpl forumRepositoryImpl;

    private final User user1;

    private final List<Forum> user1Forums;

    @Autowired
    public ForumRepositoryImplTests(UserJpaRepository userJpaRepository, ForumRepositoryImpl forumRepositoryImpl) {

        this.forumRepositoryImpl = forumRepositoryImpl;

        List<ForumId> forumIds = Stream.of(1L, 2L, 3L)
                .map(ForumId::new)
                .collect(Collectors.toList());

        Set<ForumDTO> user1ForumDTOs = forumIds.stream()
                .map(id -> ForumDTO.builder()
                        .id(id.getId())
                        .title("title for " + id.getId())
                        .description("description for " + id.getId())
                        .build()
                )
                .collect(Collectors.toSet());

        this.user1Forums = user1ForumDTOs.stream()
                .map(ForumMapper::mapForumDtoToForum)
                .collect(Collectors.toList());

        UserDTO user1DTO = UserDTO.builder()
                .username("user1")
                .email("user1@hhu.de")
                .forums(user1ForumDTOs)
                .build();

        userJpaRepository.save(user1DTO);

        this.user1 = UserMapper.mapUserDtoToUser(user1DTO);
    }

    @Test
    public void testIfForumsCanBeLoadedForUser() {

        // Act
        List<Forum> forumsForUser1 = this.forumRepositoryImpl.getForumsFromDB(this.user1);
        // Assert
        assertThat(forumsForUser1).isEqualTo(this.user1Forums);
    }

}
