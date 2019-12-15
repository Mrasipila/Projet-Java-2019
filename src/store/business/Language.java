public enum Language {

    FRENCH("French"), ENGLISH("English"), ITALIAN("Italian"), SPANISH("Spanish"), GERMAN("German"), RUSSIAN("Russian"), CHINESSE("Chinese");

    private String langue;
    
    private Platform(String console) {
        this.langue = langue;
    }
    
    public String toString() {
        return langue;
    }
}
