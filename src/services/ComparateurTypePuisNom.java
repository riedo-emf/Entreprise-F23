package services;

import java.util.Comparator;
import models.Element;

/**
 * Compare les types par ordre alphabétique (sans tenir compte de la casse),
 * puis les noms par ordre alphabétique (sans tenir compte de la casse)
 * lorsque les types sont identiques.
 */
public final class ComparateurTypePuisNom implements Comparator<Element> {

    /**
     * Compare deux éléments selon leur type, puis leur nom en cas d'égalité.
     * @param premier le premier élément non nul
     * @param second le second élément non nul
     * @return un entier négatif, nul ou positif selon l'ordre des critères
     */
    @Override public int compare(Element premier, Element second) {
        int resultat = String.CASE_INSENSITIVE_ORDER.compare(
                String.valueOf(premier.getType()),
                String.valueOf(second.getType()));
        if (resultat != 0) {
            return resultat;
        }
        return String.CASE_INSENSITIVE_ORDER.compare(
                premier.getNom(), second.getNom());
    }
}