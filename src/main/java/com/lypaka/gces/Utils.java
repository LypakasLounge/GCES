package com.lypaka.gces;

import com.pixelmonmod.pixelmon.api.config.PixelmonConfigProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;

public class Utils {

    public static int getNewLevel (int maxLevel, String scale) {

        String[] split = scale.split(" ");
        String function = split[0];
        String amount = split[1];
        int maxAmount;

        if (amount.contains("r")) {

            maxAmount = RandomHelper.getRandomNumberBetween(1, Integer.parseInt(amount.replace("r", "")));

        } else {

            maxAmount = Integer.parseInt(amount);

        }

        int newLevel;
        switch (function) {

            case "+-":
                if (RandomHelper.getRandomChance(50)) {

                    newLevel = Math.min(PixelmonConfigProxy.getGeneral().getMaxLevel(), maxAmount + maxLevel);

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
                newLevel = Math.min(PixelmonConfigProxy.getGeneral().getMaxLevel(), maxAmount + maxLevel);
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
