package com.example.dictionaryapp;

public class Definitions {
    private String definitionId;
    private String wordid;
    private String imageUrl;
    private String defType;
    private String defDefinition;

    public Definitions(String definitionId, String wordid, String imageUrl, String defType, String defDefinition) {
        this.definitionId = definitionId;
        this.wordid = wordid;
        this.imageUrl = imageUrl;
        this.defType = defType;
        this.defDefinition = defDefinition;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getWordid() {
        return wordid;
    }

    public void setWordid(String wordid) {
        this.wordid = wordid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDefType() {
        return defType;
    }

    public void setDefType(String defType) {
        this.defType = defType;
    }

    public String getDefDefinition() {
        return defDefinition;
    }

    public void setDefDefinition(String defDefinition) {
        this.defDefinition = defDefinition;
    }
}
