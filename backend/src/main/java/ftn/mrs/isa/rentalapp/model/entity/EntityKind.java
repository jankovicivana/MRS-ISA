package ftn.mrs.isa.rentalapp.model.entity;

public enum EntityKind {
    COTTAGE, BOAT, ADVENTURE;

    public static String toString(EntityKind kind) {
        switch(kind) {
            case BOAT:
                return "Boat";
            case ADVENTURE:
                return "Adventure";
            default:
                return "Cottage";
    }
}

    public static EntityKind getTypeFromString(String type) {
        switch(type) {
            case "Boat":
                return BOAT;
            case "Adventure" :
                return ADVENTURE;
            case "Cottage" :
                return COTTAGE;

        }
        return null;
    }
}
