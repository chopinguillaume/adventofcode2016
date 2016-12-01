package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Fichier {

    public static File ouvrir(String nom){
        return new File(Fichier.class.getResource(nom).getFile());
    }

    public static BufferedReader reader(String fichier) throws FileNotFoundException {
        File file = Fichier.ouvrir(fichier);
        return new BufferedReader(new FileReader(file));
    }
}
