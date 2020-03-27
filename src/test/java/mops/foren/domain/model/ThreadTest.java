package mops.foren.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadTest {
    @Test
    public void ThreadFormattedDateWithoutDate() {
        //Arrange
        Thread threadUnderTest = Thread.builder().build();

        //Act
        String formattedDate = threadUnderTest.getFormattedDate();

        //Assert
        assertThat(formattedDate).isEqualTo("Es gibt noch keinen Inhalt in diesem Beitrag");

    }

    @Test
    public void ThreadFormattedDateWithDate() {
        //Arrange
        LocalDateTime testTime = LocalDateTime.of(1000, 10, 10, 10, 10, 10, 10);
        Thread threadUnderTest = Thread.builder().lastPostTime(testTime).build();

        //Act
        String formattedDate = threadUnderTest.getFormattedDate();

        //Assert
        assertThat(formattedDate).isEqualTo("10.10.1000, 10:10");

    }
}
