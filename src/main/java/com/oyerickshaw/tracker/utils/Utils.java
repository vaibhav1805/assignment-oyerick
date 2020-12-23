package com.oyerickshaw.tracker.utils;

import java.math.BigDecimal;

public class Utils {
    public static  boolean isValidLongitude(BigDecimal coordinate){
        if((coordinate.compareTo(new BigDecimal(180)) == -1 || coordinate.compareTo(new BigDecimal(180)) == 0)
                && (coordinate.compareTo(new BigDecimal(-180)) == 1 || coordinate.compareTo(new BigDecimal(-180)) == 0)){
            return true;
        }
        return false;
    }

    public static boolean isValidLatitude(BigDecimal coordinate){
        if((coordinate.compareTo(new BigDecimal(90)) == -1 || coordinate.compareTo(new BigDecimal(90)) == 0)
                && (coordinate.compareTo(new BigDecimal(-90)) == 1 || coordinate.compareTo(new BigDecimal(-90)) == 0)){
            return true;
        }
        return false;
    }

    public static Boolean validCoordinates(BigDecimal latitude, BigDecimal longitude){
        return isValidLatitude(latitude) && isValidLongitude(longitude);
    }
}
