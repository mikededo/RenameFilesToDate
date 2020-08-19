import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FolderNavigator {
    final static String BOLD = "\u001b[1m";
    final static String ITALIC = "\u001b[3m";
    final static String RESET = "\u001b[0m";

    private static void menu() {
        System.out.println("Option list");
        System.out.println("[c: folder back | m: show 10 next | l: show 10 before | s: select current directory]");
        System.out.println();
    }

    private static File getParent(File current) {
        if (current.getParent() == null) {
            System.out.println("This folder does not have a parent");
            return current;
        } else {
            return current.getParentFile();
        }
    }

    private static int sortFiles(File f1, File f2) {
        if (f1.isDirectory() && !f2.isDirectory()) {
            return -1;
        } else if (!f1.isDirectory() && f2.isDirectory()) {
            return 1;
        } else {
            return f1.compareTo(f2);
        }
    }

    private static String selectPath(String path) {
        int i = 0;
        boolean stop = false;
        File directory = new File(path);

        while (!stop) {
            File files[] = directory.listFiles();
            // Sort directories first
            Arrays.sort(files, (File f1, File f2) -> sortFiles(f1, f2));

            String dirPath = directory.getPath();

            boolean nextDir = false;
            int currItemsPage = 1;

            while (!nextDir) {
                System.out.println("Current directory: " + dirPath);
                menu();

                for (; i < Math.min(files.length, currItemsPage * 10); i++) {
                    String f = files[i].getName();

                    if (Files.isDirectory(Paths.get(dirPath + "\\" + f))) {
                        System.out.println(BOLD + ((i % 10) + 1) + ". " + f + " - [d]" + RESET);
                    } else {
                        System.out.println(((i % 10) + 1) + ". " + f + " - [f]");
                    }
                }

                System.out.println("Page " + currItemsPage + " of " + ((files.length / 10) + 1));
                System.out.println();
                System.out.print("Option: ");

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String option = in.readLine().trim();

                    if (option.equals("c")) {
                        String oldPath = directory.getPath();
                        directory = getParent(directory);

                        if (!oldPath.equals(directory.getPath())) {
                            nextDir = true;
                        }
                    } else if (option.equals("m")) {
                        if (currItemsPage <= files.length / 10) {
                            currItemsPage++;
                        } else {
                            currItemsPage = 1;
                        }
                    } else if (option.equals("l")) {
                        if (currItemsPage > 1) {
                            currItemsPage--;
                        } else {
                            currItemsPage = files.length / 10 + 1;
                        }
                    } else if (option.equals("s")) {
                        return dirPath;
                    } else {
                        try {
                            int optNum = Integer.parseInt(option);
                            Path temp = Paths.get(dirPath + "\\" + files[(optNum * currItemsPage) - 1].getName());

                            if (Files.isDirectory(temp)) {
                                directory = new File(temp.toString());
                                nextDir = true;
                            }
                        } catch (NumberFormatException n) {
                            System.out.println("Enter a valid option");
                            stop = false;
                        }
                    }

                    // Reset counter
                    i = 10 * (currItemsPage - 1);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return null;
    }

    public static String startNavigator() {
        return selectPath(System.getProperty("user.dir"));
    }
}