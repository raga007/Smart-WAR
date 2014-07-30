package com.tripadvisor.smartwar;

/**
 * Created by jsklar on 7/30/14.
 */
public class SmartWarSettings {

    private static boolean useSmartTime = true;
    private static boolean useNearbySuggestions = true;
    private static boolean useExpiration = true;

    public static boolean isUseSmartTime() {
        return useSmartTime;
    }

    public static void setUseSmartTime(boolean useSmartTime) {
        SmartWarSettings.useSmartTime = useSmartTime;
    }

    public static boolean isUseNearbySuggestions() {
        return useNearbySuggestions;
    }

    public static void setUseNearbySuggestions(boolean useNearbySuggestions) {
        SmartWarSettings.useNearbySuggestions = useNearbySuggestions;
    }

    public static boolean isUseExpiration() {
        return useExpiration;
    }

    public static void setUseExpiration(boolean useExpiration) {
        SmartWarSettings.useExpiration = useExpiration;
    }


}
