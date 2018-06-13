package net.qiujuer.italker.factory.persistence;

/**
 * Created by Yqquan on 2018/6/13.
 */

public class Account {

    //设备的推送ID
    private static String pushId = "test";

    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        Account.pushId = pushId;
    }

}
