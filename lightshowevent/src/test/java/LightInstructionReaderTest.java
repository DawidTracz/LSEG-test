import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

public class LightInstructionReaderTest {

  @Test
  void lightInstructionReader() {

    assertDoesNotThrow(() -> new LightInstructionReader(
        "coding_challenge_input_example.txt"));
  }

  @Test
  void lightInstructionReaderThrowsExpIfFileNotFound() {

    FileNotFoundException thrown = assertThrows(
        FileNotFoundException.class,
        () -> new LightInstructionReader(
            "coding_challenge_input_example1.txt"),
        "Expected FileNotFoundException to throw, but it didn't"
    );

    assertTrue(thrown.getMessage().contains("coding_challenge_input_example1.txt' does not exist"));
  }
}
