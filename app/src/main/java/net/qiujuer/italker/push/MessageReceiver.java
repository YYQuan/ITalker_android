package net.qiujuer.italker.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;

import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.data.helper.AccountHelper;
import net.qiujuer.italker.factory.persistence.Account;

/**
 * Created by Yqquan on 2018/6/13.
 */

public class MessageReceiver extends BroadcastReceiver {

    private static final String TAG  ="MessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent ==null)
            return;
        Bundle bundle =  intent.getExtras();

        switch(bundle.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_CLIENTID:
                onClientInit(bundle.getString("clientid"));
                Log.d(TAG,"PushConsts.GET_CLIENTID"+bundle.toString());
                break;
            case PushConsts.GET_MSG_DATA:

                byte[] payload = bundle.getByteArray("payload");
                if(payload!=null){
                    String message = new String(payload);
                    onMessageArrived(message);
                    Log.d(TAG,"PushConsts.GET_MSG_DATA"+message);
                }
                break;
            default:
                Log.d(TAG,"PushConsts.other");
                break;
        }
    }


    /**
     * 消息到达的时候
     * @param message
     */
    private void onMessageArrived(String message) {
        Factory.dispatchPush(message);
    }


    /**
     *
     * @param cid
     */
    private void  onClientInit(String cid){
        //设置设备ID
        Account.setPushId(cid);

        if(Account.isLogin()){
            AccountHelper.bindPush(null);
        }

    }
}
