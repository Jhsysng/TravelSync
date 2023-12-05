package com.uhban.travelsync.util;

public class LocationUtils {
    private static final double EARTH_RADIUS = 6371e3; // 지구 반지름 (미터 단위)

    public static boolean isNear(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(long2 - long1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c; // 미터 단위로 계산된 거리

        return distance <= 10; // 10미터 이내이면 true 반환
    }
}
