package pl.themolka.commons.storage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IStorageFileManagement {
    File getDefaultFile();

    List<Storage> readDefaultFile() throws IOException;

    List<Storage> readFile(File file) throws IOException;
}
