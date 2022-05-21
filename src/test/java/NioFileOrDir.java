import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class NioFileOrDir {
    static final String PATH = "text.txt";


    @BeforeEach
    void setup() throws IOException {
        File file = new File(PATH);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.close();

    }

    @Test
    void NIO_파일존재(){
        boolean exists = Files.exists(Path.of(PATH));
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    void PATH_존재(){
        Path path = Paths.get(PATH);
        Assertions.assertThat(path.getFileName()).isNotNull();
    }

    @Test
    void FILE_SYSTEM() throws IOException {
        FileSystem fileSystem = FileSystems.getDefault();

        for (FileStore store : fileSystem.getFileStores()) {
            System.out.println("드라이버명: " + store.name());
            System.out.println("파일시스템: " + store.type());
            System.out.println("전체 공간: " + store.getTotalSpace() + " 바이트");
            System.out.println("사용 중인 공간: " + (store.getTotalSpace() - store.getUnallocatedSpace()) + " 바이트");
            System.out.println("사용 가능한 공간: " + (store.getTotalSpace() - store.getUsableSpace()) + " 바이트");
            System.out.println();
        }

        System.out.println("파일 구분자: " + fileSystem.getSeparator());
        System.out.println();

        for (Path path : fileSystem.getRootDirectories()) {
            System.out.println(path.toString());
        }
    }

    @Test
    void FILE_PATHS() throws IOException {
        Path path = Paths.get(PATH);

        System.out.println("디렉토리 여부: " + Files.isDirectory(path));
        System.out.println("파일 여부: " + Files.isRegularFile(path));
        System.out.println("마지막 수정 시간: " + Files.getLastModifiedTime(path));
        System.out.println("파일 크기: " + Files.size(path));
        System.out.println("소유자: " + Files.getOwner(path));
        System.out.println("숨김 파일 여부: " + Files.isHidden(path));
        System.out.println("읽기 가능 여부: " + Files.isReadable(path));
        System.out.println("쓰기 가능 여부: " + Files.isWritable(path));
    }

    @Test
    @Disabled
    void 파일_변경_감지() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path directory = Paths.get("./");
        directory.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        while (true){
            WatchKey watchKey = watchService.take();
            List<WatchEvent<?>> list = watchKey.pollEvents();

            for ( WatchEvent<?> event : list){
                WatchEvent.Kind<?> kind =  event.kind();
                Path path = (Path) event.context();


                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("파일 생성 감지");
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("파일 삭제 감지");
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("파일 변경 감지");
                } else if (kind == StandardWatchEventKinds.OVERFLOW) {
                    System.out.println("파일 감지 .. ? ");
                }
                boolean reset = watchKey.reset();

                if (!reset) {
                    break;
                }
            }
        }
    }

    @AfterEach
    void  after(){
        File file = new File(PATH);
        file.delete();
    }
}
