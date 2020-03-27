package mops.foren.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    public void PostFormattedDateWithDate() {
        //Arrange
        LocalDateTime testTime = LocalDateTime.of(1000, 10, 10, 10, 10, 10, 10);
        Post postUnderTest = Post.builder().creationDate(testTime).build();

        //Act
        String formattedDate = postUnderTest.getFormattedDate();

        //Assert
        assertThat(formattedDate).isEqualTo("10.10.1000, 10:10");

    }
}
