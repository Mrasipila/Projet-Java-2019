public enum Platform {
    NINTENDO("Nintendo"), PC("PC"), PLAYSTATION("Playstation"), XBOX("Xbox");
    
    private String console;
    
    private Platform(String console) {
        this.console = console;
    }
    
    public String toString() {
        return console;
    }
}
