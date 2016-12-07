package challenges;

import input.Fichier;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Day07 {

    public static void main(String[] args) {

        try {
            BufferedReader reader = Fichier.reader("day07");

            int tlsNumber = 0;
            int sslNumber = 0;
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {

                if (isTLS(line)) {
                    tlsNumber++;
                }
                if (isSSL(line)) {
                    sslNumber++;
                }

                line = reader.readLine();
            }

            System.out.println("Nombre d'adresses avec TLS = "+tlsNumber);
            System.out.println("Nombre d'adresses avec SSL = "+sslNumber);

            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static boolean isSSL(String line) {
        boolean inBrackets = false;
        int i=0;
        List<String> abas = new ArrayList<>();
        List<String> babs = new ArrayList<>();
        while(i<line.length()){

            if(line.charAt(i) == '['){
                inBrackets = true;
            }
            if(line.charAt(i) == ']'){
                inBrackets = false;
            }

            if(i>=2){
                char a = line.charAt(i-2);
                char b = line.charAt(i-1);
                char c = line.charAt(i);

                if(a==c && a!=b){
                    if(inBrackets){
                        babs.add(""+a+b+c);
                    }else{
                        abas.add(""+a+b+c);
                    }
                }
            }

            i++;
        }

        return !babs.isEmpty() && !abas.isEmpty() && corresponding(abas,babs);
    }

    private static boolean corresponding(List<String> abas, List<String> babs) {
        return abas.stream().anyMatch(aba -> {
            String bab = ""+aba.charAt(1)+aba.charAt(0)+aba.charAt(1);
            return babs.contains(bab);
        });
    }

    private static boolean isTLS(String line) {

        boolean valid = false;
        boolean inBrackets = false;
        int i=0;
        while(i<line.length()){

            if(line.charAt(i) == '['){
                inBrackets = true;
            }
            if(line.charAt(i) == ']'){
                inBrackets = false;
            }

            if(i>=3){
                char a = line.charAt(i-3);
                char b = line.charAt(i-2);
                char c = line.charAt(i-1);
                char d = line.charAt(i);

                if(a==d && b==c && a!=b){
                    if(inBrackets){
                        return false;
                    }else{
                        valid = true;
                    }
                }

            }

            i++;
        }
        return valid;
    }
}
