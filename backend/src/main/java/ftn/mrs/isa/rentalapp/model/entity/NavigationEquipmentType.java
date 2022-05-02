package ftn.mrs.isa.rentalapp.model.entity;

public enum NavigationEquipmentType {
    GPS,
    RADAR,
    VHF_RADIO,
    FISHFINDER;

    public static NavigationEquipmentType getTypeFromString(String type) {
        switch(type) {
            case "GPS" :
                return GPS;
            case "RADAR":
                return RADAR;
            case"VHF RADIO" :
                return VHF_RADIO;
            default:
                return FISHFINDER;
        }
    }

}