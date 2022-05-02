package ftn.mrs.isa.rentalapp.model.entity;

import ftn.mrs.isa.rentalapp.model.user.UserType;

public enum BoatType {
    SAIL_BOAT,
    CANOE,
    ROW_BOAT,
    MOTOR_BOAT,
    YACHT;

    public static BoatType getTypeFromString(String type) {
        switch(type) {
            case "YACHT" :
                return YACHT;
            case "SAIL BOAT":
                return SAIL_BOAT;
            case"CANOE" :
                return CANOE;
            case "ROW BOAT":
                return ROW_BOAT;
            default:
                return MOTOR_BOAT;
        }
    }


}
