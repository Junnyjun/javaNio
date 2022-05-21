import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.FileChannel;
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

}
