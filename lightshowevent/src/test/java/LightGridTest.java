import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LightGridTest {

  @Test
  void proceedInstructionTurnOn() {
    var grid = new LightGrid(10, 10, 2, 2);

    grid.proceedInstruction("turn on 0,0 through 9,9");

    assertEquals(100, grid.numberOfLightsOn());
  }

  @Test
  void proceedInstructionTurnOff() {
    var grid = new LightGrid(10, 10, 2, 2);

    grid.proceedInstruction("turn on 0,0 through 9,9");
    grid.proceedInstruction("turn off 5,0 through 9,9");

    assertEquals(50, grid.numberOfLightsOn());
  }

  @Test
  void proceedInstructionToggle() {
    var grid = new LightGrid(10, 10, 2, 2);

    grid.proceedInstruction("toggle 5,0 through 9,9");

    assertEquals(100, grid.numberOfLightsOn());
  }

  @Test
  void proceedInstructiosForExampleScenario() {
    var grid = new LightGrid(1000, 1000, 5, 2);

    grid.proceedInstruction("turn on 0,0 through 999,999");
    grid.proceedInstruction("turn off 499,499 through 500,500");
    grid.proceedInstruction("toggle 0,499 through 999,500");

    assertEquals(1003996, grid.numberOfLightsOn());
  }

  @Test
  void shouldThrowExceptionIfWrongInstructionPattern() {
    var grid = new LightGrid(10, 10, 2, 2);
    ValidationException thrown = assertThrows(
        ValidationException.class,
        () -> grid.proceedInstruction("togglefd 5,0 through 9,9"),
        "Expected instructionPatternValidation to throw, but it didn't"
    );

    assertTrue(thrown.getMessage().contains("Instruction has to match with pattern"));
  }

  @Test
  void shouldThrowExceptionIfExceededMaxNumber() {
    var grid = new LightGrid(10, 10, 2, 2);
    ValidationException thrown = assertThrows(
        ValidationException.class,
        () -> grid.proceedInstruction("toggle 10,0 through 9,9"),
        "Expected validateInstruction to throw, but it didn't"
    );

    assertTrue(thrown.getMessage()
        .contains("Every bulb-max param shouldn't exceed maximum number of bulbs on scene"));
  }

  @Test
  void shouldThrowExceptionIfBulbStartPositionIsBiggerThanLast() {
    var grid = new LightGrid(10, 10, 2, 2);
    ValidationException thrown = assertThrows(
        ValidationException.class,
        () -> grid.proceedInstruction("toggle 7,0 through 5,5"),
        "Expected validateInstruction to throw, but it didn't"
    );

    assertTrue(thrown.getMessage()
        .contains("Starting position of bulb should not be higher than last position"));
  }

}
