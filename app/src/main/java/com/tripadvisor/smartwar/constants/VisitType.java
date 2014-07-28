package com.tripadvisor.smartwar.constants;


import com.tripadvisor.smartwar.R;

public enum VisitType {

    SOLO(R.string.mobile_trip_type_solo_8e0, "solo"),
    BUSINESS(R.string.mobile_trip_type_business_8e0, "business"),
    COUPLES(R.string.mobile_trip_type_couples_8e0, "couples"),
    FAMILY(R.string.mobile_trip_type_family_8e0, "family"),
    FRIENDS(R.string.mobile_trip_type_friends_8e0, "friends"),
    FAMILY_WITH_TEENAGERS(R.string.mobile_trip_type_family_with_teenagers_8e0, "family_with_teenagers"),
    FAMILY_WITH_YOUNG_CHILDREN(R.string.mobile_trip_type_family_with_young_children_8e0, "family_with_young_children");

    public final int stringId;
    public final String value;

    private VisitType(int stringId, String value) {
        this.stringId = stringId;
        this.value = value;
    }

    public static VisitType findByValue(String value) {
        for (VisitType type : VisitType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    public static final VisitType[] FOR_HOTELS_RESTAURANTS = { SOLO, BUSINESS, COUPLES, FAMILY, FRIENDS };

    public static final VisitType[] FOR_ATTRACTIONS = { SOLO, BUSINESS, COUPLES, FRIENDS, FAMILY_WITH_TEENAGERS, FAMILY_WITH_YOUNG_CHILDREN };
}
