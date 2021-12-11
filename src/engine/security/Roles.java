package engine.security;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String text;

    Roles(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}