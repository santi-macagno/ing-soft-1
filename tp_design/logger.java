import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class logger {
    private static logger instance;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    private logger(){}

    public static logger getInstance()
    {
        if(instance == null)
        {
            instance = new logger();
        }

        return instance;
    }

    private String getFormattedTime()
    {
        return LocalDateTime.now().format(formatter);
    }

    public void logWarning(String msg)
    {
        System.out.println("\u001B[33m" + getFormattedTime() + " [WARN] " + msg + "\u001B[0m\n"); //amarillo ansi
    }

    public void logDebug(String msg)
    {
        System.out.println("\u001B[32m" + getFormattedTime() + " [DEBUG] " + msg + "\u001B[0m\n");
    }

    public void logInfo(String msg)
    {
        System.out.println("\u001B[90m" + getFormattedTime() + " [INFO] " + msg + "\u001B[0m\n");
    }

    public void logError(String msg)
    {
        System.out.println("\u001B[31m" + getFormattedTime() + " [ERROR] " + msg + "\u001B[0m\n");
    }
}
