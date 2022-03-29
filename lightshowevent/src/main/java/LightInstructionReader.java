import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class LightInstructionReader {

  private String fileName;

  private File file;
  private LineIterator lineIterator;

  private static final String PATH = "src/main/resources//";

  public LightInstructionReader(String fileName) throws IOException {
    this.fileName = fileName;

    this.file = FileUtils.getFile(PATH + this.fileName);

    this.lineIterator = FileUtils.lineIterator(this.file, "UTF-8");

  }

  public LineIterator getLineIterator() {
    return lineIterator;
  }

}
