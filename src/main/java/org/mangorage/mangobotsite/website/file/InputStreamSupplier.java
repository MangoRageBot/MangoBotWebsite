package org.mangorage.mangobotsite.website.file;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSupplier {
    InputStream get() throws IOException;
}
