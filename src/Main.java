import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress [] progress = {
                new GameProgress(100, 15, 38, 12.5),
                new GameProgress(100, 18, 40, 13.0),
                new GameProgress(100, 20, 42, 13.5)
        };
        File files = new File("D:\\Учеба\\JAVA\\Progi\\Core\\Games\\Games\\savegames\\");
        String path = "D:\\Учеба\\JAVA\\Progi\\Core\\Games\\Games\\savegames\\";

        for (int i = 0; i < progress.length; i++) {
            saveGame(path, progress[i]);
            zipFiles(path, files.list());

        }

        for (int i = 0; i < files.list().length; i++) {
            if (files.list()[i].contains("zip")) {
                continue;
            } else {
                File delFiles = new File(path + files.list()[i]);
                delFiles.delete();
            }
        }

    }

    public static void saveGame(String path, GameProgress progress) {

        try (FileOutputStream fos = new FileOutputStream(path + "save.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String path, String[] listZip) {

        for (int i = 0; i < listZip.length; i++) {
            String saveGame = listZip[i];
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + "saveGame" + i + ".zip"));
                 FileInputStream fis = new FileInputStream(path + saveGame)) {
                ZipEntry entry = new ZipEntry("saveGame" + i + ".dat");
                zout.putNextEntry(entry);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                // закрываем текущую запись для новой записи
                zout.closeEntry();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}