import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class MainTest {

  @Test
  void main() {
    assertDoesNotThrow(Main::startInstructions);
  }
}
