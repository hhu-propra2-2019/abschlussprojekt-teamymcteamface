package mops.foren.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ForumTest {

    @Test
    public void formattedDateWithoutDate() {
        //Arrange
        Forum forumUnderTest = Forum.builder().build();

        //Act
        String formattedDate = forumUnderTest.getFormattedDate();

        //Assert
        assertThat(formattedDate).isEqualTo("--:--:--");

    }

    @Test
    public void formattedDateWithDate() {
        //Arrange
        LocalDateTime testTime = LocalDateTime.of(1000, 10, 10, 10, 10, 10, 10);
        Forum forumUnderTest = Forum.builder().lastChange(testTime).build();

        //Act
        String formattedDate = forumUnderTest.getFormattedDate();

        //Assert
        assertThat(formattedDate).isEqualTo("10.10.1000, 10:10");

    }
}
