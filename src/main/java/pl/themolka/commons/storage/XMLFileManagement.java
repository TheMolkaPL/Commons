package pl.themolka.commons.storage;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XMLFileManagement implements IStorageFileManagement {
    public static final File STORAGE_FILE = new File("storage.xml");

    @Override
    public File getDefaultFile() {
        return STORAGE_FILE;
    }

    @Override
    public List<Storage> readDefaultFile() throws IOException {
        return this.readFile(this.getDefaultFile());
    }

    @Override
    public List<Storage> readFile(File file) throws IOException {
        if (!file.exists()) {
            this.copyDefaultFile(file);
        }

        try {
            Document document = new SAXBuilder().build(file);
            return this.readStorage(document.getRootElement());
        } catch (JDOMException jdom) {
            return null;
        }
    }

    public List<Storage> readStorage(Element element) {
        List<Storage> storageList = new ArrayList<>();
        for (Element provider : element.getChildren("provider")) {
            Storage storage = this.readStorageProvider(provider);

            if (storage != null) {
                storageList.add(storage);
            }
        }

        return storageList;
    }

    public Storage readStorageProvider(Element element) {
        String name = element.getAttributeValue("name");
        StorageProvider provider = StorageProvider.find(element.getAttributeValue("provider"));
        Properties properties = this.readStorageProviderProperties(element);

        if (provider == null) {
            return null;
        }

        return provider.createStorage(name, properties);
    }

    public Properties readStorageProviderProperties(Element element) {
        Properties properties = new Properties();
        for (Element child : element.getChildren("property")) {
            properties.setProperty(child.getAttributeValue("key"), child.getAttributeValue("value"));
        }

        return properties;
    }

    private void copyDefaultFile(File to) throws IOException {
        Files.copy(this.getClass().getClassLoader().getResourceAsStream(STORAGE_FILE.getName()), to.toPath());
    }
}
