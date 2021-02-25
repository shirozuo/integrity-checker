import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TestTask {
    public static void main(String[] args) {
        //hashShower("scr.jpg");
        pathsInput();

    }

    public static void pathsInput() {
        String filePath;
        String directoryPath;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                filePath = bufferedReader.readLine();
                directoryPath = bufferedReader.readLine();
                File checkFile = new File(filePath);
                File checkDirectory = new File(directoryPath);

                if (checkFile.exists() && !(checkFile.isDirectory())) {
                    if (checkDirectory.exists() && checkDirectory.isDirectory()) {
                        readAndCheck(checkFile, directoryPath);
                        break;
                    } else {
                        System.out.println("DIRECTORY NOT FOUND OR THIS IS NOT DIRECTORY, PLEASE TRY AGAIN");
                    }

                } else {
                    System.out.println("FILE NOT FOUND, PLEASE TRY AGAIN");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readAndCheck(File checkFile, String directoryPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(checkFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (findInLine(line, 0) != null && findInLine(line, 1) != null && findInLine(line, 2) != null) {
                    switch (findInLine(line, 1)) { //arg : 0 - filename, 1 - algorithm, 2 - hash
                        case ("md5"):
                            System.out.println(findInLine(line, 0) + " " + md5Digest(findInLine(line, 0), findInLine(line, 2), directoryPath));
                            break;
                        case ("sha1"):
                            System.out.println(findInLine(line, 0) + " " + sha1Digest(findInLine(line, 0), findInLine(line, 2), directoryPath));
                            break;
                        case ("sha256"):
                            System.out.println(findInLine(line, 0) + " " + sha256Digest(findInLine(line, 0), findInLine(line, 2), directoryPath));
                            break;
                    }
                } else continue;
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String findInLine(String line, int arg) { //arg : 0 - filename, 1 - algorithm, 2 - hash

        try {
            String[] arr = line.split(" ");
            switch (arg) {
                case (0):
                    return arr[0];
                case (1):
                    return arr[1];
                case (2):
                    return arr[2];
                default:
                    return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static boolean checkExists(String fileName, String directoryPath) {
        File path = new File(directoryPath + fileName);
        return path.exists();
    }

    public static String md5Digest(String fileName, String hash, String directoryPath) throws IOException {
        if (checkExists(fileName, directoryPath)) {
            try (InputStream inputStream = Files.newInputStream(Paths.get(directoryPath + fileName))) {
                String md5 = DigestUtils.md5Hex(inputStream);
                if (md5.equals(hash)) {
                    return "OK";
                } else return "FAIL";
            }
        } else return "NOT FOUND";
    }

    public static String sha1Digest(String fileName, String hash, String directoryPath) throws IOException {
        if (checkExists(fileName, directoryPath)) {
            try (InputStream inputStream = Files.newInputStream(Paths.get(directoryPath + fileName))) {
                String sha1 = DigestUtils.sha1Hex(inputStream);
                if (sha1.equals(hash)) {
                    return "OK";
                } else return "FAIL";
            }
        } else return "NOT FOUND";
    }

    public static String sha256Digest(String fileName, String hash, String directoryPath) throws IOException {
        if (checkExists(fileName, directoryPath)) {
            try (InputStream inputStream = Files.newInputStream(Paths.get(directoryPath + fileName))) {
                String sha256 = DigestUtils.sha256Hex(inputStream);
                if (sha256.equals(hash)) {
                    return "OK";
                } else return "FAIL";
            }
        } else return "NOT FOUND";
    }

    /*public static void hashShower(String fileName) { //for test
        try (InputStream inputStream = Files.newInputStream(Paths.get("c:/test/" + fileName))) {
            String sha256 = DigestUtils.sha256Hex(inputStream);
            System.out.println(sha256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}