package com.lypaka.gces;

import com.lypaka.lypakautils.PixelmonHandlers.RandomHandler;

public class Utils {

    public static int getNewLevel (int maxLevel, String scale) {

        String[] split = scale.split(" ");
        String function = split[0];
        String amount = split[1];
        int maxAmount;

        if (amount.contains("r")) {

            maxAmount = RandomHandler.getRandomNumberBetween(1, Integer.parseInt(amount.replace("r", "")));

        } else {

            maxAmount = Integer.parseInt(amount);

        }

        int newLevel;
        switch (function) {

            case "+-":
                if (RandomHandler.getRandomChance(50)) {

                    newLevel = Math.min(GCES.maxPokemonLevel, maxAmount + maxLevel);

                } else {

                    int value;
                    if (maxLevel > maxAmount) {

                        value = maxLevel - maxAmount;

                    } else {

                        value = maxAmount - maxLevel;

                    }
                    newLevel = Math.max(1, value);

                }
                break;

            case "+":
                newLevel = Math.min(GCES.maxPokemonLevel, maxAmount + maxLevel);
                break;

            default:
                int value;
                if (maxLevel > maxAmount) {

                    value = maxLevel - maxAmount;

                } else {

                    value = maxAmount - maxLevel;

                }
                newLevel = Math.max(1, value);
                break;

        }

        return newLevel;

    }

}
