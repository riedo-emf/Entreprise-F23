package services;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServiceFormatage {

  public String formaterTaille(long taille) {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator('\'');

    DecimalFormat format = new DecimalFormat("#,###,###", symbols);
    String formatee = format.format(taille);
    return formatee + " bytes";
  }

  public String formaterDuree(int seconds) {
    int hour = 0;
    int minutes = 0;
    if(seconds > 60) {
      minutes = seconds / 60;
      seconds = seconds % 60;
      if(minutes > 60) {
        hour = minutes / 60;
        minutes = minutes % 60;
      }
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String format = LocalTime.of(hour, minutes, seconds).format(formatter);
    return format;
  }
}
