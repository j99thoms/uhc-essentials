package com.j99thoms.uhcessentials.api;

import java.util.List;

public interface GameContext {

    double getPlayerX();
    double getPlayerY();
    double getPlayerZ();
    float  getPlayerFacingYaw();   // wrapped to [-180, 180]

    String getBiomeName();

    List<String> getArmorItemNames();           // resource location strings, top-to-bottom
    List<Float>  getArmorDurabilityFractions(); // parallel list, 0.0 (new) to 1.0 (broken)

    int getArrowCount();
    int getFPS();

    float getGamma();
    void setGamma(float gamma);
}
