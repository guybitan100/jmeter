import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    private final File tmpFile;
    private ArrayList<File> csvFiles;
    final static org.apache.log4j.Logger log4j = Logger.getLogger(FileManager.class);

    public FileManager(String tmpFileName) {
        this.csvFiles = new ArrayList<File>();
        this.tmpFile = new File(tmpFileName);
    }

    public void createCsvFromTemplate(String name, long from, long till, int limit) throws IOException {
        File file = new File(name);
        Files.copy(tmpFile, file);
        setDuration(file, from, till);
        setLimit(file, limit);
        csvFiles.add(file);
    }

    public void deleteAllCsvTests() throws IOException {
        for (File file : csvFiles)
            file.delete();
    }

    private void setDuration(File file, long from, long till) throws IOException {
        String result = Files.toString(file, Charsets.UTF_8);
        result = result.replace("$$$$", from + "");
        result = result.replace("$$$$", till + "");
        Files.write(result, file, Charsets.UTF_8);
    }

    private void setLimit(File file, int limit) throws IOException {
        String result = Files.toString(file, Charsets.UTF_8);
        result = result.replace("$$$", limit + "");
        Files.write(result, file, Charsets.UTF_8);
    }
}
