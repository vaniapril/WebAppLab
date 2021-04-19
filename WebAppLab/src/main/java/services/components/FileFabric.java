package services.components;

import java.io.File;

public class FileFabric {
    private static final String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
    private static final String dataPath = path + "\\data";

    public static String getFilePath(FileName fileName, FileType fileType){
        String currentPath = dataPath + "\\";
        switch (fileName){
            case MONETARY_UNIT:
                currentPath += "monetaryunit";
                break;
            case CRYPTO_CURRENCY:
                currentPath += "cryptocurrency";
                break;
            case PRECIOUS_METAL:
                currentPath += "preciousmetal";
                break;
            case STOCK:
                currentPath += "stock";
                break;
        }
        switch (fileType) {
            case XML:
                currentPath += ".xml";
                break;
            case CSV:
                currentPath += ".csv";
                break;
            case JSON:
                currentPath += ".json";
                break;
        }
        return currentPath;
    }
    public static File getFile(FileName fileName, FileType fileType){
        return new File(getFilePath(fileName, fileType));
    }
}
