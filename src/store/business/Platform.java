public enum Platform {
    NINTENDO("Nintendo"), PC("Pc"), PLAYSTATION("PlayStation"), XBOX("Xbox");

    private String platformName;

    private Platform(String plateformName) {
        this.platformName = plateformName;
    }

    @Override
    public String toString() {
        return platformName;
    }
}