package by.epam.java.training.lifehacks.model.entityenum;

public enum LifeHackCategory {
    SAVVY("SAVVY"),
    MNEMONICS("MNEMONICS"),
    MEMORY_CARDS("MEMORY CARDS"),
    TIME_MANAGEMENT("TIME MANAGEMENT"),
    SOCIAL_ENGINEERING("SOCIAL ENGINEERING"),
    EASY_TIPS("EASY TIPS"),
    USING_REMOTE_ASSISTANTS("USING REMOTE ASSISTANTS");
    private String value;

    LifeHackCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
