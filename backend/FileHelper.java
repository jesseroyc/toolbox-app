package backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {
    private String path;

    public FileHelper() {
        this.path = "";
    }

    public String getPath() {
        return path;
    }

    public void setFilePath(String path) {
        this.path = path;
    }

    public String[] getFileContent()
    {
        String content = "";

        try
        {
            content = new String ( Files.readAllBytes( Paths.get(this.path) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(this.path.contains("items.txt") || this.path.contains("suppliers.txt")){
            return content.split("[;\n]");
        }

        // any other expected file regex expressions
        return content.split("");
    }
}
