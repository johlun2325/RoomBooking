package com.example.roombooking.utilities;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class StreamProvider {
    public InputStream getDataStream(String url) throws IOException {
        URL streamUrl = new URL(url);
        return streamUrl.openStream();
    }
}
