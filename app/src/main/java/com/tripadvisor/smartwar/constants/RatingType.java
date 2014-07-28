package com.tripadvisor.smartwar.constants;


import com.tripadvisor.smartwar.R;

public enum RatingType {

    TERRIBLE(R.string.mobile_terrible, 1),
    POOR(R.string.mobile_poor, 2),
    AVERAGE(R.string.mobile_average, 3),
    VERY_GOOD(R.string.mobile_very_good, 4),
    EXCELLENT(R.string.mobile_excellent, 5);
    
    public final int stringId;
    public final int value;

    private RatingType(int stringId, int value) {
        this.stringId = stringId;
        this.value = value;
    }
    
    public static RatingType findByValue(int value) {
        return RatingType.values()[value - 1];
    }
}
