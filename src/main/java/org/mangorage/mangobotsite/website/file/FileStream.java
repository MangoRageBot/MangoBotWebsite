package org.mangorage.mangobotsite.website.file;

import java.io.IOException;
import java.io.InputStream;

public final class FileStream {
    private final String fileName;
    private final String extension;
    private final InputStreamSupplier streamSupplier;

    public FileStream(String name, InputStreamSupplier streamSupplier) {
        this.fileName = name;
        this.extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
        this.streamSupplier = streamSupplier;
    }

    public InputStream getInputStream() throws IOException {
        return streamSupplier.get();
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return extension;
    }
}
