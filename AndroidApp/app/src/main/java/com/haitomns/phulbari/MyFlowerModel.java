package com.haitomns.phulbari;

public class MyFlowerModel {
    private String flowerName;
    private String waterRequirement;
    private String sunlightRequirement;

    public MyFlowerModel(String flowerName, String waterRequirement, String sunlightRequirement) {
        this.flowerName = flowerName;
        this.waterRequirement = waterRequirement;
        this.sunlightRequirement = sunlightRequirement;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getWaterRequirement() {
        return waterRequirement;
    }

    public void setWaterRequirement(String waterRequirement) {
        this.waterRequirement = waterRequirement;
    }

    public String getSunlightRequirement() {
        return sunlightRequirement;
    }

    public void setSunlightRequirement(String sunlightRequirement) {
        this.sunlightRequirement = sunlightRequirement;
    }
}
