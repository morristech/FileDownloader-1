import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stepanov Nickolay
 * @version 1.0
 */
public class FileDownloader {

    private static final String SOURCE_FILE = "c:/temp/links.txt";

    private static List<String> links = new ArrayList<>();

    public static void main(String[] args){
        readFile();
        downloadAndWriteFiles(links);
    }

    private static void readFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(SOURCE_FILE))){
            readByLines(bufferedReader);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void downloadAndWriteFiles(List<String> linksArr) {
        int threadNumber = linksArr.size();
        for (int threadCounter = 0; threadCounter < threadNumber; threadCounter++) {
            new Thread(new DownloaderRunnable(threadCounter, linksArr.get(threadCounter))).start();
        }
    }

    private static void readByLines(BufferedReader bufferedReader) throws IOException{
        String line;
        while ((line = bufferedReader.readLine()) != null){
            links.add(line);
        }
    }
}
