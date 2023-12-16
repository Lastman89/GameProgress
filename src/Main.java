import java.io.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress[] progress = {
                new GameProgress(100, 15, 38, 12.5),
                new GameProgress(100, 18, 40, 13.0),
                new GameProgress(100, 20, 42, 13.5)
        };
        File files = new File("D:\\Учеба\\JAVA\\Progi\\Core\\Games\\Games\\savegames\\");
        String path = "D:\\Учеба\\JAVA\\Progi\\Core\\Games\\Games\\savegames\\";

        saveGame(path, progress);
        zipFiles(path, files.list());
        System.out.println(files.list().length);

        for (int i = 0; i <= files.list().length - 1; i++) {
            if (files.list()[i].contains("zip")) {
                continue;
            }
            File delFiles = new File(path + files.list()[i]);
            delFiles.deleteOnExit();

        }

    }

    public static void saveGame(String path, GameProgress[] progress) {
        for (int i = 0; i < progress.length; i++) {
            try (FileOutputStream fos = new FileOutputStream(path + "save" + i + ".dat");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                // запишем экземпляр класса в файл
                oos.writeObject(progress[i]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    public static void zipFiles(String path, String[] listZip) {
        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(new FileOutputStream(path + "saveGame.zip"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < listZip.length; i++) {
            String saveGame = listZip[i];
            try (FileInputStream fis = new FileInputStream(path + saveGame)) {
                ZipEntry entry = new ZipEntry(saveGame);
                zout.putNextEntry(entry);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                // закрываем текущую запись для новой записи
                zout.closeEntry();
                fis.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}