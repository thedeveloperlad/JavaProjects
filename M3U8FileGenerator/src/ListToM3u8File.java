import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListToM3u8File {
    public static void createM3u8File(List<ReadM3U8File.ChannelInfo> list, String path, String nameFile){
        Path outputPath = Paths.get(path);
        String fileStart = "#EXTM3U";

        try {
            // 3. Write the list to the file (each item automatically gets a new line)
            /*

            writer.write(fileStart);
            writer.newLine();

            */
            List<String> allLines = new ArrayList<>();
            allLines.add(fileStart);

            List<String> textLines = list.stream()
                    .map(ReadM3U8File.ChannelInfo::toString)
                    .collect(Collectors.toList());

            allLines.addAll(textLines);

            Files.write(outputPath, allLines);
            System.out.println("File saved successfully to: " + outputPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("An error occurred while saving the file: " + e.getMessage());
        }
    }
}
