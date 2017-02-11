/**
 * Created by Maciej Marzęta on 10.02.2017.
 */
import java.io.*;
import java.util.*;

public class Parser {
    private String buffer;
    private Map<String, List<String>> elementsMap = new HashMap<>();
    private String elementsTab[] = {"K", "Ca", "Ti", "Cr", "Mn",
            "Fe", "Cu", "Zn", "Ga", "Se",
            "Br", "Rb", "Sr", "Titles"};

    /**
        Konstruktor wypełniający słownik NazwaPierwiastka-Wartości.
        Wywołuje metody openFile() i filtrList() które odpowiadają za realizację całego zadania.
     **/
    Parser() {
        for (String it : elementsTab) {
            elementsMap.put(it, new ArrayList<String>());
        }
        this.getFilesNames();
        for (String it : this.elementsMap.get("Titles")) {
            openFile(it);
            filtrList();
        }
        for(String it : this.elementsTab){
            createElementFile(this.elementsMap.get(it), it);
        }
    }

    /**
        Dodaje nazwy plików *.txt z folderu src/samples/ do Mapy pod kluczem "Titles"
     **/
    public void getFilesNames() {
        File[] files = new File("src/samples/").listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().contains(".txt")) {
                this.elementsMap.get("Titles").add(file.getName());
            }
        }
    }

    /**
        Dodaje dane z pliku src/samples/file_name do buffera.
     **/
    private void openFile(String file_name) {
        this.buffer = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/samples/" + file_name));
            String text = null;
            while ((text = reader.readLine()) != null) {
                buffer += text;
                buffer += "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
        Tworzy plik o nazwie klucza z wartościami Mapy.
     **/
    private void createElementFile(List<String> list, String name) {
        try {
            PrintWriter writer = new PrintWriter(name + ".txt", "UTF-8");
            list.forEach(item -> writer.println(item));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
        Dodaje wartości pierwiastków z buffera do odpowiadających im kluczom w Mapie.
     **/
    private void filtrList() {
        String lines[] = this.buffer.split("\n");
        //usuwam wszystkie nadliczbowe spacje
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim().replaceAll(" +", " ");
        }
        //pierwsze dwie linijki nas nie interesuja wiec zaczynam od 3 linijki
        //iteruje do ostatniej
        for (int i = 2; i < lines.length - 1; i++) {
            String tmp[] = lines[i].split(" ");
            for (String it : this.elementsTab) {
                if (tmp[1].equals(it)) {
                    this.elementsMap.get(it).add(tmp[2]);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Parser();
    }
}
