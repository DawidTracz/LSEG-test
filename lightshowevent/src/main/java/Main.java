import java.io.IOException;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException {

    startInstructions();

  }

  public static void startInstructions() throws IOException {
    var lights = new LightGrid(1000, 1000, 10, 2);

    //If you want you can add another file to resources and change a file name here.
    var instructionReader = new LightInstructionReader("coding_challenge_input.txt");

    var instructionIterator = instructionReader.getLineIterator();

    try {
      while (instructionIterator.hasNext()) {
        var line = instructionIterator.nextLine();

        lights.proceedInstruction(line);

      }
    } catch (ValidationException e) {
      LOG.error("Error due to instruction issue: " + e.getMessage(), e);
      throw new ValidationException("Error due to instruction issue all process whole be reverted");
    } finally {
      LineIterator.closeQuietly(instructionIterator);
    }
    LOG.info(String.format("Number of lights on %d", lights.numberOfLightsOn()));
  }

}