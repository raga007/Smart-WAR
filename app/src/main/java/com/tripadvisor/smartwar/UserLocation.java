package com.tripadvisor.smartwar;

public class UserLocation{
    private float latitude;
    private float longitude;
    private long timestamp;

    public UserLocation(float latitude,float longitude, long timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        UserLocation that = (UserLocation) o;

        if (timestamp != that.timestamp) return false;
        if (Float.compare(that.latitude, latitude) != 0) return false;
        if (Float.compare(that.longitude, longitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Float.floatToIntBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Float.floatToIntBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                '}';
    }

}
