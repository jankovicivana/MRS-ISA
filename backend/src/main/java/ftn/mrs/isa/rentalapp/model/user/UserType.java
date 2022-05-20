package ftn.mrs.isa.rentalapp.model.user;


import org.springframework.security.core.GrantedAuthority;

public enum UserType{
    FISHING_INSTRUCTOR,
    COTTAGE_OWNER,
    BOAT_OWNER,
    CLIENT,
    ADMINISTRATOR;

    public static String toString(UserType type) {

        switch(type) {
            case FISHING_INSTRUCTOR:
                return "fishingInstructor";
            case COTTAGE_OWNER:
                return "cottageOwner";
            case BOAT_OWNER:
                return "boatOwner";
            case CLIENT:
                return "client";
            case ADMINISTRATOR:
                return "admin";
            default:
                return "unknown";
        }
    }

    public static UserType getTypeFromString(String type) {
        switch(type) {
            case "fishingInstructor" :
                return FISHING_INSTRUCTOR;
            case "cottageOwner":
                return COTTAGE_OWNER;
            case"boatOwner" :
                return BOAT_OWNER;
            case "admin":
                return ADMINISTRATOR;
            default:
                return CLIENT;
        }
    }

}
