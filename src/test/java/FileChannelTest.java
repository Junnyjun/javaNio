import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public class FileChannelTest {

    @Test
    void 채널_파일_생성() throws IOException {
        FileChannel channel = FileChannel.open(
                Paths.get(UUID.randomUUID().toString()+".txt"),
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE
        );

        channel.close();
    }

    @Test
    void 채널_파일_생성_후_쓰기() throws IOException {
        String s = UUID.randomUUID().toString();
        FileChannel channel = FileChannel.open(
                Paths.get("copy."+".txt"),
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE
        );

        String input = "data";
        Charset charset = Charset.defaultCharset();
        ByteBuffer buffer = charset.encode(input);

        int write = channel.write(buffer);
        System.out.println(write);

        channel.close();
    }

    @Test
    void 파일_채널_읽기() throws IOException {
        Path path = Paths.get("C:\\workspace\\javaNio\\73e574c9-945e-4366-b788-132aece90700.txt");
        FileChannel channel = FileChannel.open(
                path,
                StandardOpenOption.READ
        );

        ByteBuffer buffer = ByteBuffer.allocateDirect(100);

        Charset charset = Charset.defaultCharset();
        int byteCount;
        String data = "";

        while (true){
            byteCount = channel.read(buffer);

            if(byteCount == -1){
                break;
            }

            buffer.flip();
            data += charset.decode(buffer).toString();
            buffer.clear();
        }
        channel.close();

        System.out.println("data = " + data);
    }

    @Test
    void 파일_채널_복사() throws IOException {
        Path path = Paths.get("C:\\workspace\\javaNio\\73e574c9-945e-4366-b788-132aece90700.txt");
        Path to = Paths.get("C:\\workspace\\javaNio\\copy.txt");

        FileChannel channelFrom = FileChannel.open(path,StandardOpenOption.READ);
        FileChannel channelTo = FileChannel.open(to,StandardOpenOption.WRITE);

        int byteCount = 0;
        ByteBuffer buffer = ByteBuffer.allocateDirect(100);

        while (true){
            buffer.clear();
            byteCount = channelFrom.read(buffer);

            if(byteCount == -1){
                break;
            }

            buffer.flip();
            channelTo.write(buffer);
        }

        channelFrom.close();
        channelTo.close();
    }
}
