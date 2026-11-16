package services;

import models.*;

public class ExporteurElementTexte implements IExporteur<Element> {

    public ExporteurElementTexte() {
    }

    public String exporter(Element element) {
        return element.getNom() + " " + element.getTaille() + " Byte "
                + element.getDateModification();
    }

}