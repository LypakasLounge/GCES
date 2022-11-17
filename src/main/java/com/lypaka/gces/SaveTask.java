package com.lypaka.gces;

import com.lypaka.gces.Config.ConfigGetters;

import java.util.Timer;
import java.util.TimerTask;

public class SaveTask {

    public static Timer timer = null;

    public static void startAutoSave() {

        if (!ConfigGetters.doAutoSaves) return;

        if (timer != null) {

            timer.cancel();

        }

        timer = new Timer();
        long interval = ConfigGetters.autoSaveInterval * 1000L;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                GCES.configManager.getConfigNode(1, "Accounts").setValue(ConfigGetters.playerAccountsMap);
                GCES.configManager.save();

            }

        }, interval, interval);

    }

}
