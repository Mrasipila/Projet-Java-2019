public enum Genre {
    ACTION("Action"), AVENTURE("Aventure"), THRILLER("Thriller"), HORROR("Horror"), FANTASTIQUE("Fantastique");
    
    private String genre;
    
    private Platform(String genre) {
        this.genre = genre;
    }
    
    public String toString() {
        return genre;
    }
}
