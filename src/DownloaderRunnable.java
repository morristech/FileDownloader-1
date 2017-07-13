import java.io.*;
import java.net.URL;

/**
 * @author Stepanov Nickolay
 * @version 1.0
 */
public class DownloaderRunnable implements Runnable {

    private static final String RESULT_DIR = "c:/temp/downloaded/";
    private static final String RESULT_FILE_NAME = "result";
    private static final int BUFFER_SIZE = 64 * 1024;

    private int threadCount;
    private String link;

    public DownloaderRunnable(int threadCount, String link){
        this.threadCount = threadCount;
        this.link = link;
    }

    @Override
    public void run() {
        String fileExtension = getLinkExtension(link);
        if (new File(RESULT_DIR).mkdir()) {
            try (InputStream inputStream = new BufferedInputStream(new URL(link).openStream());
                 OutputStream outputStream = new FileOutputStream(RESULT_DIR + RESULT_FILE_NAME + threadCount + fileExtension)) {
                writeToFile(inputStream, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToFile(InputStream inputStream, OutputStream outputStream) throws IOException{
        byte[] buffer = new byte[BUFFER_SIZE];
        int count;
        while ((count = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, count);
        }
    }

    private String getLinkExtension(String link) {
        return link.substring(link.lastIndexOf('.'));
    }
}
