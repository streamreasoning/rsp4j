package it.polimi.jasper.engine.querying.response;

import lombok.extern.log4j.Log4j;

import java.io.*;

@Log4j

public class FileService {

    public static boolean write(String where, String data) {
        log.info("Try to write [" + data + "] to [" + where + "]");
        try {
            FileWriter writer;
            File file = new File(where);
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(data);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            log.error(where);
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void createFolders(String vsfolder) {
        new File(vsfolder).mkdirs();
    }

    public static void createFolder(String vsfolder) {
        new File(vsfolder).mkdir();
    }

    public static void createOutputFolder(String folder) {
        new File(folder).mkdirs();
    }

    public static FileReader getFileReader(String fileName) {
        log.debug("Try to load FileReader [" + fileName + "]");
        try {
            File file = new File(fileName);
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static BufferedReader getBuffer(FileReader in) {
        log.debug("Try to load BufferReader");
        return new BufferedReader(in);
    }

    public static BufferedReader getBuffer(String fileName) {
        log.debug("Try to load [" + fileName + "]");
        try {
            File file = new File(fileName);
            FileReader in = new FileReader(file);
            return new BufferedReader(in);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
