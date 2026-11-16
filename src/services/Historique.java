package services;

import java.util.ArrayList;

import models.*;

/**
 * Historique
 */
public class Historique {
    // attributs
    private int pos;
    private ArrayList<Dossier> his;

    /**
     * allows you to create the "historique" object
     * 
     * @param racine base folder (home folder) cannot be null or it wont work
     */
    public Historique(Dossier racine) {
        this.pos = 0;
        this.his = new ArrayList<>();
        his.add(racine);
    }

    // méthodes
    /**
     * will add a folder and reset if you aren't at the last position
     *
     * @param dos the folder you want to add
     */
    public void ajouterDos(Dossier dos) {
        if (pos != his.size() - 1) {
            réinitialiseAPos();
        }
        his.addLast(dos);
        pos++;
    }

    /**
     * Tells you wether it is possible to go to the next file or not based on your
     * position in the ArrayList
     *
     * @return Returns true if you can go to the next and false if you can't
     */
    public boolean suivantPossible() {
        return (pos == his.size() - 1) ? false : true;
    }

    /**
     * Tells you wether it is possible to go to the previous file or not based on
     * your position in the ArrayList
     *
     * @return Returns a boolean true if you can go to the previous and false if you
     *         can't
     */
    public boolean precedentPossible() {
        return (pos == 0) ? false : true;
    }

    /**
     * moves you to the next folder in the Historique
     * 
     * @return the folder you land on (next to your current one)
     */
    public Dossier suivant() {
        if (pos != his.size() - 1) {
            pos++;
        }
        return his.get(pos);
    }

    /**
     * moves you to the previous folder in the Historique
     * 
     * @return the folder you land on (before to your current one)
     */
    public Dossier precedent() {
        if (pos != 0) {
            pos--;
        }
        return his.get(pos);
    }

    /**
     * Will clear everything in the ArrayList after the current position
     * 
     * @return a boolean based on if it worked or if an error occured
     */
    public boolean réinitialiseAPos() {
        boolean success = true;
        try {
            for (int index = pos + 1; index < his.size(); index++) {
                his.remove(index);
            }
        } catch (Exception e) { //
            success = false; // in case there are any errors we'll return false to be able to print said
                             // error
        }
        return success;
    }
}