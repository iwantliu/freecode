package com.freecode.util;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>Description:nio</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-09-18 15:32
 */
public class NioUtil {

    public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream("")) {
            FileChannel channel = fin.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(256);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
