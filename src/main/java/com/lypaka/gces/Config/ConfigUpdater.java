package com.lypaka.gces.Config;

import com.lypaka.gces.GCES;

public class ConfigUpdater {

    public static void updateConfig() {

        boolean needsSaving = false;

        /** Version 10.5.0 */
        if (!GCES.configManager.getConfigNode(0, "General-Settings", "Task").isVirtual()) {

            needsSaving = true;
            GCES.configManager.getConfigNode(0, "General-Settings", "Task", "Enable-Auto-Saves").setValue(false);
            GCES.configManager.getConfigNode(0, "General-Settings", "Task", "Enable-Auto-Saves").setComment("If true, GCES will run a task to automatically save player accounts at a set interval");
            GCES.configManager.getConfigNode(0, "General-Settings", "Task", "Interval").setValue(3);
            GCES.configManager.getConfigNode(0, "General-Settings", "Task", "Interval").setComment("Sets the interval in seconds that GCES saves player account data");

        }

        if (needsSaving) {

            GCES.configManager.save();

        }

    }

}
