package com.nat.communication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by xuqinchao on 17/2/7.
 */

public class HLCommModule {
    public static void call(Context context,  String number, final HLModuleResultListener listener)  {
        boolean tel = HLUtil.isTel(number);
        if (!tel || TextUtils.isEmpty(number)) {
            listener.onResult(HLUtil.getError(HLConstant.CALL_INVALID_ARGUMENT, HLConstant.CALL_INVALID_ARGUMENT_CODE));
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(
                    intent);
            listener.onResult(null);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onResult(HLUtil.getError(HLConstant.CALL_PHONE_PERMISSION_DENIED, HLConstant.CALL_PHONE_PERMISSION_DENIED_CODE));
        }
    }

    public static void mail(Context context, String[] tos, HashMap<String, String> params, HLModuleResultListener listener){
        if (tos==null||tos.length<1){
            listener.onResult(HLUtil.getError(HLConstant.MAIL_INVALID_ARGUMENT, HLConstant.MAIL_INVALID_ARGUMENT_CODE));
            return;
        }
        for (String to: tos) {
            if (!HLUtil.isEmail(to)) {
                listener.onResult(HLUtil.getError(HLConstant.MAIL_INVALID_ARGUMENT, HLConstant.MAIL_INVALID_ARGUMENT_CODE));
                return;
            }
        }

        String url = "";
        for (int i = 0; i < tos.length; i++) {
            url += tos[i] + (i == tos.length - 1 ? "" : ";");
        }
        String subject = params.containsKey("subject") ? params.get("subject") : "";
        String body = params.containsKey("body") ? params.get("body") : "";

        Uri uri = Uri.parse("mailto:" + url);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//        intent.putExtra(Intent.EXTRA_CC, cc); // 抄送人
//        intent.putExtra(Intent.EXTRA_BCC, tos);//密语人
        intent.putExtra(Intent.EXTRA_SUBJECT, subject); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, body); // 正文
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        listener.onResult(null);
    }

    public static void sms(Context context, String[] tos, final String text, HLModuleResultListener listener) {
        if (tos == null || tos.length < 1) {
            listener.onResult(HLUtil.getError(HLConstant.SMS_INVALID_ARGUMENT, HLConstant.SMS_INVALID_ARGUMENT_CODE));
            return;
        }
        for (String to : tos) {
            if (!HLUtil.isMobile(to)) {
                listener.onResult(HLUtil.getError(HLConstant.SMS_INVALID_ARGUMENT, HLConstant.SMS_INVALID_ARGUMENT_CODE));
                return;
            }
        }

        String url = "";
        for (int i = 0; i < tos.length; i++) {
            url += tos[i] + (i == tos.length - 1 ? "" : ";");
        }
        Uri uri = Uri.parse("smsto:" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra("sms_body", text==null?"":text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        listener.onResult(null);
    }
}
