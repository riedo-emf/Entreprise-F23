package models;

public interface IExporteur<T> {
    String exporter(T objet); // generischer Typ
}
