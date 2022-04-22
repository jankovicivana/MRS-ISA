package ftn.mrs.isa.rentalapp.model.user;


public enum UserType {
    FISHING_INSTRUCTOR,
    COTTAGE_OWNER,
    BOAT_OWNER,
    CLIENT,
    ADMINISTRATOR;

    public static String toString(UserType type) {

        switch(type) {
            case FISHING_INSTRUCTOR:
                return "Fishing Instructor";
            case COTTAGE_OWNER:
                return "Cottage Owner";
            case BOAT_OWNER:
                return "Boat Owner";
            case CLIENT:
                return "Client";
            case ADMINISTRATOR:
                return "Administrator";
            default:
                return "Unknown";
        }
    }

    public static UserType getTypeFromString(String type) {
        switch(type) {
            case "Fishing Instructor" :
                return FISHING_INSTRUCTOR;
            case "Cottage Owner":
                return COTTAGE_OWNER;
            case"Boat Owner" :
                return BOAT_OWNER;
            case "Administrator":
                return ADMINISTRATOR;
            default:
                return CLIENT;
        }
    }


}
