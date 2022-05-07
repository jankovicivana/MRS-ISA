package ftn.mrs.isa.rentalapp.model.entity;


public enum BoatType {
    SAIL_BOAT,
    CANOE,
    ROW_BOAT,
    MOTOR_BOAT,
    YACHT;

    public static String toString(BoatType type) {

        switch(type) {
            case SAIL_BOAT:
                return "SAIL BOAT";
            case CANOE:
                return "CANOE";
            case ROW_BOAT:
                return "ROW BOAT";
            case MOTOR_BOAT:
                return "MOTOR BOAT";
            case YACHT:
                return "YACHT";
            default:
                return "Unknown";
        }
    }

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
