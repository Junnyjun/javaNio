import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

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
        System.out.println("넌다이렉트 버퍼가 생성되었습니다.");


    }
}
