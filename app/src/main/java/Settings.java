/**
 * Created by jsklar on 7/30/14.
 */
public class Settings {

    private static boolean useSmartTime = true;
    private static boolean useNearbySuggestions = true;
    private static boolean useExpiration = true;

    public static boolean isUseSmartTime() {
        return useSmartTime;
    }

    public static void setUseSmartTime(boolean useSmartTime) {
        Settings.useSmartTime = useSmartTime;
    }

    public static boolean isUseNearbySuggestions() {
        return useNearbySuggestions;
    }

    public static void setUseNearbySuggestions(boolean useNearbySuggestions) {
        Settings.useNearbySuggestions = useNearbySuggestions;
    }

    public static boolean isUseExpiration() {
        return useExpiration;
    }

    public static void setUseExpiration(boolean useExpiration) {
        Settings.useExpiration = useExpiration;
    }


}
