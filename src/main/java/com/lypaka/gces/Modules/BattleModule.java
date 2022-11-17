package com.lypaka.gces.Modules;

public class BattleModule {

    private final boolean checkBattles;
    private final String dynamaxPermission;
    private final String megaPermission;
    private final String zMovePermission;

    public BattleModule (boolean checkBattles, String dynamaxPermission, String megaPermission, String zMovePermission) {

        this.checkBattles = checkBattles;
        this.dynamaxPermission = dynamaxPermission;
        this.megaPermission = megaPermission;
        this.zMovePermission = zMovePermission;

    }

    public boolean doCheckBattles() {

        return this.checkBattles;

    }

    public String getDynamaxPermission() {

        return this.dynamaxPermission;

    }

    public String getMegaPermission() {

        return this.megaPermission;

    }

    public String getZMovePermission() {

        return this.zMovePermission;

    }

}
