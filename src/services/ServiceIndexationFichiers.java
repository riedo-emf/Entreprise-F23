package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import models.*;

public class ServiceIndexationFichiers {

    private TreeMap<String, ArrayList<Fichier>> filesList;

    public ServiceIndexationFichiers() {
        this.filesList = new TreeMap<>();
    }

    public ArrayList<Fichier> initialiseFiles(Dossier dossier){
        ArrayList<Element> elements = new ArrayList<>(dossier.getContenu());
        ArrayList<Fichier> retour = new ArrayList<>();

        for (Element element : elements) {
            if(element instanceof Dossier){
                ArrayList<Fichier> list = this.initialiseFiles((Dossier)element);
                for (Fichier fichier : list) {
                    retour.add(fichier);
                }
            }
            else if(element instanceof Fichier){
                retour.add((Fichier)element);
            }
        }

        return retour;
        
    }

    public void initialiseList(ArrayList<Fichier> listFiles) {

        for (Fichier f1 : listFiles) {
            for (String type : filesList.keySet()) {
                if (f1.getType() == type) {
                    filesList.get(type).add(f1);
                }

                else {
                    ArrayList<Fichier> test = new ArrayList<>();
                    test.add(f1);
                    filesList.put(type, test);
                }

            }
        }

    }

    public ArrayList<Fichier> getFilesListType(String type) {

        ArrayList<Fichier> transaction = new ArrayList<>();

        if (filesList.containsKey(type)) {
            transaction = filesList.get(type);
        }

        return transaction;

    }

}
