import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class NIOBuffer {


    @Test
    void Buffer_비교(){
        Runtime runtime = Runtime.getRuntime();

        //JVM 버퍼
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(800 * 1024 * 1024);
        System.out.println("FREE MEMORY : " + runtime.freeMemory());
        System.out.println("다이렉트 버퍼가 생성되었습니다.");

        runtime.gc();

        // HOST 버퍼
        ByteBuffer nonDirectBuffer = ByteBuffer.allocate(1024 * 1024 * 1024 * 1024);
        runtime.gc();
        System.out.println("FREE MEMORY : " + runtime.freeMemory());
        System.out.println("넌다이렉트 버퍼가 생성되었습니다. :::: " + nonDirectBuffer.capacity());

    }


    @Test
    void 파일_채널_버퍼_차이() throws IOException {
        int BUFFER_SIZE = 100;
        Path from = new File("text.txt").toPath();


        FileChannel channel1 = FileChannel.open(from, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));

        ByteBuffer noneDirectBuffer =  ByteBuffer.allocate(BUFFER_SIZE);
        ByteBuffer directBuffer =  ByteBuffer.allocateDirect(BUFFER_SIZE);

        System.out.println("OS name  : " + System.getProperty("os.name"));
        System.out.println("바이트 해석 순서  : " + ByteOrder.nativeOrder());

    }

    @Test
    void 버퍼_바이트_핸들링(){
        ByteBuffer buffer = ByteBuffer.allocateDirect(8);

        System.out.println("버퍼 추가");
        buffer.put((byte) 10);
        buffer.put((byte) 11);
        printState(buffer);

        System.out.println("버퍼 추가");
        buffer.put((byte) 12);
        buffer.put((byte) 13);
        buffer.put((byte) 14);
        printState(buffer);

        System.out.println("버퍼 읽기");
        buffer.mark();
        buffer.get(new byte[3]);
        printState(buffer);

    }

    public static void printState(Buffer buffer) {
        System.out.print("\tposition: " + buffer.position() + ", ");
        System.out.print("\tlimit: " + buffer.limit() + ", ");
        System.out.println("\tcapacity: " + buffer.capacity());
    }


    @Test
    void 버퍼_인코딩(){
        Charset csUTF = Charset.forName("UTF-8");
        Charset csDEF = Charset.defaultCharset();

        System.out.println("ENC TYPE : " + csUTF.name());
        System.out.println("ENC TYPE : " + csDEF.name());

        ByteBuffer buffer =  csDEF.encode("HELLO");
        printState(buffer);

        System.out.println(csDEF.decode(buffer));
    }

}
