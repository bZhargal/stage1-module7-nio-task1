package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;


public class FileReader {

    public Profile getDataFromFile(File file) {

        return mapping(Objects.requireNonNull(parseFile(file)));
    }

    private Profile mapping(String str) {
        String[] arr = str.split("\n");

        return new Profile(
                arr[0].split(":")[1].trim(),
                Integer.valueOf(arr[1].split(":")[1].trim()),
                arr[2].split(":")[1].trim(),
                Long.valueOf(arr[3].split(":")[1].trim())
        );
    }

    private String parseFile(File file) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            FileChannel fileChannel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            StringBuilder stringBuilder = new StringBuilder();

            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    stringBuilder.append(Character.toString(buffer.get()));
                }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
