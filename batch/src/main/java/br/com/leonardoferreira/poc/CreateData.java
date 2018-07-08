package br.com.leonardoferreira.poc;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;

//@Component
public class CreateData implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StringBuilder sb = new StringBuilder("externalId,customStr,customIgnoredProperty,customIgnoredProperty2\n");
        for (int i = 0; i < 300000; i++) {
            sb.append(String.format("%d,str-%d,customIgnoredProperty-%d,customIgnoredProperty-%d", i, i, i, i))
                    .append("\n");
        }
        FileOutputStream outputStream = new FileOutputStream("/home/lferreira/bla.csv");
        byte[] strToBytes = sb.toString().getBytes();
        outputStream.write(strToBytes);

        outputStream.close();
    }

}
