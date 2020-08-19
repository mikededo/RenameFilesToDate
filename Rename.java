import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Rename {
    private static final DateFormat _dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        return i == -1 ? "" : fileName.substring(i);
    }

    private static Map<FileHolder, List<String>> mapFiles(String folderPath) {
        File directory = new File(folderPath);
        String fileNames[] = directory.list();

        Map<FileHolder, List<String>> fileMap = new TreeMap<>();
        for (String fileName : fileNames) {
            try {
                Path path = Paths.get(folderPath + fileName);

                if (Files.isRegularFile(path)) {
                    String date = _dateFormat.format(Files.getLastModifiedTime(path).toMillis());
                    FileHolder temp = new FileHolder(fileName, date, getFileExtension(fileName));

                    fileMap.computeIfAbsent(temp, k -> new ArrayList<>()).add(fileName);
                } else {
                    // Recursive
                    startRename(path.toString() + "\\");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        return fileMap;
    }

    private static void renameFiles(String folderPath, Map<FileHolder, List<String>> fileMap) {
        fileMap.forEach((k, v) -> {
            try {
                if (v.size() == 1) {
                    Files.move(Paths.get(folderPath + v.get(0)), Paths.get(folderPath + k.newFileName(0)));
                } else {
                    for (int i = 0; i < v.size(); i++) {
                        Files.move(Paths.get(folderPath + v.get(i)), Paths.get(folderPath + k.newFileName(i + 1)));
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static void startRename(String folderPath) {
        Map<FileHolder, List<String>> fileMap = mapFiles(folderPath);

        renameFiles(folderPath, fileMap);
    }

    public static void main(String[] args) {
        String path = FolderNavigator.startNavigator();

        System.out.println("Choosen directory: " + path);

        long startTime = System.nanoTime();
        startRename(path + "\\");

        double elapsedTime = TimeUnit.MICROSECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS) / 1000000.0 ;
        System.out.println("Elapsed time: " + elapsedTime + "s");
    }
}