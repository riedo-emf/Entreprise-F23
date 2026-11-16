package ctrl;

import java.util.ArrayList;
import java.util.Comparator;
import models.*;
import services.ComparateurDatePuisTaille;
import services.ComparateurTaille;
import views.VueExplorateur;
import services.ServiceIndexationFichiers;

/**
 * Coordonne les événements de la vue, la navigation, les tris et les actions du
 * modèle.
 * Les capacités sont reconnues par leurs interfaces, indépendamment du format
 * des fichiers.
 */
public final class ControleurExplorateur {

    // Référence de la vue
    private final VueExplorateur vue;

    private Dossier courant;

    // Référence du media en cours de lecture; null si aucun. 
    // type. Ce type devra avoir une méthode "arreter()", "jouer()" et
    // "getDureeSecondes()"
    private IJouable enLecture;

    /**
     * Relie les callbacks de la vue et affiche le dossier initial.
     * À appeler sur le thread de gestion des événements Swing.
     *
     * @param vue    vue non nulle à piloter
     * @param racine dossier initial non nul
     */
    public ControleurExplorateur(VueExplorateur vue, Dossier racine) {
        this.vue = vue;
        courant = racine;
        vue.surSelection(this::selectionner);
        vue.surTri(this::afficher);
        vue.surParent(this::remonter);
        vue.surOuvrir(this::ouvrir);
        vue.surImprimer(this::imprimer);
        vue.surJouer(this::jouer);
        vue.surArreter(this::arreter);
        vue.surRechercheIndex(this::IndexationFichiers);
        afficher();
    }

    /**
     * inplémentatino Service indexation Fifhier 
     * 
     */
    private void IndexationFichiers(){
        ServiceIndexationFichiers index = new ServiceIndexationFichiers();
        ArrayList<Fichier> poisson = index.initialiseFiles(courant);
        index.initialiseList(poisson);
        ArrayList<Element> poisson2 = new ArrayList<>(index.getFilesListType(vue.getTypeRecherche()));
        vue.afficherElements(poisson2);
    }
    /**
     * Arrête la lecture en cours et adapte les actions à la nouvelle sélection.
     */
    private void selectionner() {
        if (enLecture != null) {
            vue.afficherStatut(enLecture.arreter());
            enLecture = null;
        } else {
            vue.afficherStatut("Sélectionnez une action.");
        }
        actualiserActions();
    }

    /**
     * Active les commandes et affiche le lecteur selon les interfaces de l'élément
     * sélectionné.
     */
    private void actualiserActions() {
        Element element = vue.getSelection();

        boolean imprimable = element instanceof IImprimable;
        boolean estUnDossier = element instanceof Dossier; 

        vue.afficherActions(imprimable, estUnDossier);

        if (element instanceof IJouable jouable) {
            vue.afficherLecteur(true, element.getNom() + " — "
                    + jouable.getDureeSecondes() + " secondes");
        } else {
            vue.afficherLecteur(false, "");
        }
    }

    /**
     * Affiche le parent du dossier courant, s'il existe.
     */
    private void remonter() {
        if (courant.getParent() != null) {
            courant = courant.getParent();
            afficher();
        }
    }

    /**
     * Ouvre la sélection si elle désigne un dossier.
     */
    private void ouvrir() {
        
        if (vue.getSelection() instanceof Dossier dossier) {
            courant = dossier;
            afficher();
        }
    }

    /**
     * Demande une impression simulée si la sélection est imprimable.
     */
    private void imprimer() {
        Element e = vue.getSelection();
        if (e instanceof IImprimable) {
            
            IImprimable imprimable = (IImprimable) e;
            vue.afficherStatut(imprimable.imprimer());
        }
    }

    /**
     * Mémorise le média sélectionné et affiche son message de lecture simulée.
     */
    private void jouer() {
        Element e = vue.getSelection();
        
        if (e instanceof IJouable) {
            
            IJouable jouable = (IJouable) e;
            enLecture = jouable;
            vue.afficherStatut(jouable.jouer());
        }
    }

    /**
     * Arrête la simulation du média sélectionné et oublie la lecture en cours.
     */
    private void arreter() {
        Element e = vue.getSelection();
        if (e instanceof IJouable) {
            
            IJouable jouable = (IJouable) e;
            vue.afficherStatut(jouable.arreter());
            enLecture = null;
        }
    }

    /**
     * Trie une copie du contenu, réinitialise la sélection et actualise le chemin.
     * Le contenu du modèle conserve son ordre d'insertion.
     */
    private void afficher() {
        // Le tri d'une copie préserve l'ordre d'insertion du dossier.
        ArrayList<Element> elements = new ArrayList<>(courant.getContenu());
        int typeTri = vue.getIndiceTri();
        Comparator<Element> comparateur;
        switch (typeTri) {
            case 1:
                comparateur = new ComparateurTaille();
                break;
            case 2:
                comparateur = new ComparateurDatePuisTaille();
                break;
            default:
                comparateur = Comparator.naturalOrder();
        }

        elements.sort(comparateur);
        vue.afficherElements(elements);

        String emplacement = courant.getNom();
        for (Dossier p = courant.getParent(); p != null; p = p.getParent()) {
            emplacement = p.getNom() + "/" + emplacement;
        }
        vue.afficherChemin(emplacement + "/", courant.getParent() != null);
        actualiserActions();
    }
}
