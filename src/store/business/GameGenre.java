public enum GameGenre {
    ACTION("Action"), AVENTURE("Aventure"), FANTASTIQUE("Fantastique"), RPG("Rpg"), SOCIETY("Society");

    private String genre;
    
    private Platform(String genre) {
        this.genre = genre;
    }
    
    public String toString() {
        return genre;
    }
}
