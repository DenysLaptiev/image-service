package com.springboot.image_service.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    boolean upload(String fileName, InputStream inputStream) throws IOException;

    boolean download(String keyName) throws IOException;

    byte[] downloadRest(String keyName);

    boolean delete(String keyName);

    void listAll();
}
