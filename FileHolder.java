public class FileHolder implements Comparable<FileHolder> {
    String date;
    String extension;

    FileHolder(String fileName, String date, String extension) {
        this.date = date;
        this.extension = extension;
    }

    public String newFileName(int i) {
        if (i == 0) {
            return date + extension;
        } else {
            return date + " (" + i + ")" + extension;
        }
    }

    @Override
    public int compareTo(FileHolder that) {
        boolean equalTime = this.date.equals(that.date);
        boolean equalExt = this.extension.equals(that.extension);

        if (equalTime && equalExt) {
            return 0;
        } else if (equalTime != equalExt) {
            return 1;
        } else {
            return -1;
        }
    }
}
