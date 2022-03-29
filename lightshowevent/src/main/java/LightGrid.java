import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LightGrid {

  private final Integer maxX;
  private final Integer maxY;

  //Not needed but I think is nice to have
  private final Integer maxBrightnessLevel;
  private final Integer toggleIncreaseNumber;

  private final Map<Point2D, Integer> gridsWithState = new HashMap<>();

  private static final String REGEX;

  static {
    REGEX = "(turn on|turn off|toggle)\\s\\d+,\\d+\\sthrough\\s\\d+,\\d+";
  }

  private static final Logger LOG = LogManager.getLogger(LightGrid.class);

  public LightGrid(Integer maxX, Integer maxY, Integer maxBrightnessLevel,
      Integer toggleIncreaseNumber) {

    this.maxX = maxX;
    this.maxY = maxY;
    this.maxBrightnessLevel = maxBrightnessLevel;
    this.toggleIncreaseNumber = toggleIncreaseNumber;

    for (int i = 0; i < maxX; i++) {

      for (int j = 0; j < maxY; j++) {

        this.gridsWithState.put(new Point2D.Double(i, j), 0);

      }
    }
    LOG.info(String.format("You have created a scene built from %d bulbs",
        this.gridsWithState.size()));
  }


  public void proceedInstruction(String instruction) {

    var lowerCaseInstruction = instruction.toLowerCase();

    instructionPatternValidation(lowerCaseInstruction);

    List<Integer> lightsToSwitch = Arrays.stream(instruction
            .replaceAll("[^\\d]", " ").split(" "))
        .filter(value -> !value.equals(""))
        .map(Integer::valueOf)
        .collect(Collectors.toList());

    validateInstruction(lightsToSwitch);

    if (instruction.startsWith("turn on")) {
      for (int i = lightsToSwitch.get(0); i <= lightsToSwitch.get(2); i++) {
        for (int j = lightsToSwitch.get(1); j <= lightsToSwitch.get(3); j++) {

          this.gridsWithState.computeIfPresent(new Point2D.Double(i, j), (k, v) ->
              v < this.maxBrightnessLevel ? v += 1 : v);
        }

      }
    }

    if (instruction.startsWith("turn off")) {
      for (int i = lightsToSwitch.get(0); i <= lightsToSwitch.get(2); i++) {
        for (int j = lightsToSwitch.get(1); j <= lightsToSwitch.get(3); j++) {

          this.gridsWithState.computeIfPresent(new Point2D.Double(i, j), (k, v) ->
              v > 0 ? v -= 1 : v);
        }
      }
    }

    if (instruction.startsWith("toggle")) {

      /*
       * Sorry but sentence below doesn't make any sense. How can I have 1003996 bulbs turn on if
       * my total amount of bulbs is 1000000 ? This should be rewritten to more understandable
       * sentence. At least I could figure out from numbers what is going on.
       *==========================================================================================
       *With the new meaning, the result from the example instructions from part 1 is 1003996.
       *As before, the program should read the input file and print the number of lights on at the end.
       */
      for (int i = lightsToSwitch.get(0); i <= lightsToSwitch.get(2); i++) {
        for (int j = lightsToSwitch.get(1); j <= lightsToSwitch.get(3); j++) {

          this.gridsWithState.computeIfPresent(new Point2D.Double(i, j), (k, v) ->
              v < this.maxBrightnessLevel ? v += this.toggleIncreaseNumber : v);
        }

      }
    }
  }

  private void instructionPatternValidation(String instruction) {

    if (!instruction.matches(REGEX)) {

      throw new ValidationException("Error with following instruction: "
          + instruction + System.lineSeparator()
          + "Instruction has to match with pattern:" + System.lineSeparator()
          + "1st: ('turn on'/'turn off'/'toggle' followed by space" + System.lineSeparator()
          + "2nd: first bulb position for ex: (100,100) followed by space" + System.lineSeparator()
          + "3rd: 'through' followed by space" + System.lineSeparator()
          + "4th: last bulb position for ex: (500,500) NOT FOLLOWED BY ANY SPACE");
    }
  }

  private void validateInstruction(List<Integer> lightsToSwitch) {

    var exceptionMessages = new ArrayList<String>();
    if (lightsToSwitch.size() != 4) {
      exceptionMessages.add("1 instruction should contain information of 4 bulbs position");
    }
    if (lightsToSwitch.get(0) > this.maxX - 1 || lightsToSwitch.get(2) > this.maxX - 1
        || lightsToSwitch.get(1) > this.maxY - 1 || lightsToSwitch.get(3) > this.maxY - 1) {
      exceptionMessages.add(
          "Every bulb-max param shouldn't exceed maximum number of bulbs on scene");
    }
    if (lightsToSwitch.get(0) > lightsToSwitch.get(2)
        || lightsToSwitch.get(1) > lightsToSwitch.get(3)) {
      exceptionMessages.add("Starting position of bulb should not be higher than last position");
    }
    if (!exceptionMessages.isEmpty()) {

      throw new ValidationException("There were issues with processing a bulbs: "
          + String.join(System.lineSeparator(), exceptionMessages));
    }
  }

  public int numberOfLightsOn() {
    return new ArrayList<>(this.gridsWithState
        .values()
        .stream()
        .filter(integer -> integer > 0)
        .collect(Collectors.toList()))
        .stream().mapToInt(Integer::intValue)
        .sum();

  }
}


