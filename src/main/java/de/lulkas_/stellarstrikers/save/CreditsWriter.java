package de.lulkas_.stellarstrikers.save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CreditsWriter {
    public static void write() {
        writeFile("/misc/credits.txt", "credits.txt");
        writeFile("/misc/bsd2.txt", "bsd2.txt");
        writeFile("/misc/licence.txt", "LICENCE.txt");
    }

    public static void writeFile(String source, String dest) {
        try {
            InputStream inputStream = CreditsWriter.class.getResourceAsStream(source);
            File fileOut = new File(dest);
            fileOut.createNewFile();

            FileWriter fileWriter = new FileWriter(fileOut);
            Scanner scanner = new Scanner(inputStream);

            while(scanner.hasNext()) {
                fileWriter.write(scanner.nextLine());
                fileWriter.write(System.lineSeparator());
            }

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("error while creating credits file");
            e.printStackTrace();
        }
    }
}
