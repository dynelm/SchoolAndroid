package com.dynelm.robotdarttest.getWeatherinfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class InputStreamTOString {
    public String InputStreamTOString(InputStream in, String encoding) throws Exception {
        final  int BUFFER_SIZE = 4096;

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(),"utf-8");
    }
}

