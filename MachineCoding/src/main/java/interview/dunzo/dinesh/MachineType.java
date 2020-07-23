package interview.dunzo.dinesh;

public enum MachineType {

    BEVERAGES("Beverage Machine"),
    BURGURES("Burger");

    private String machineName;

    MachineType(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineName() {
        return machineName;
    }
}
