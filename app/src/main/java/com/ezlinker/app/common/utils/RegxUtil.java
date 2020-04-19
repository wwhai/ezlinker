package com.ezlinker.app.common.utils;


import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: ezlinker
 * @description: 正则辅助
 * @author: wangwenhai
 * @create: 2019-09-17 16:33
 **/

public class RegxUtil {
    /**
     * 检查身份证号码是否正确
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        String rgx = "^\\d{15}|^\\d{17}([1-9]|X|x)$";
        Pattern p = Pattern.compile(rgx);
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /**
     * 电话号码验证
     *
     * @param phone
     * @return 验证通过返回true
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[01234567890]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 检查是否为邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 是否为合法营业执照：18位 91350100MA32CP1M47
     *
     * @param license
     * @return
     */

    public static boolean isCompanyId(String license) {
        if (StringUtils.isEmpty(license)) {
            return false;
        }
        if (license.length() != 18) {
            return false;
        }

        String regex = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";
        if (!license.matches(regex)) {
            return false;
        }
        String str = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        int[] ws = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
        String[] codes = new String[2];
        codes[0] = license.substring(0, license.length() - 1);
        codes[1] = license.substring(license.length() - 1);
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += str.indexOf(codes[0].charAt(i)) * ws[i];
        }
        int c18 = 31 - (sum % 31);
        if (c18 == 31) {
            c18 = 'Y';
        } else if (c18 == 30) {
            c18 = '0';
        }
        if (str.charAt(c18) != codes[1].charAt(0)) {
            return false;
        }
        return true;
    }

    /**
     * 获取 营业执照注册号的校验码
     *
     * @param ints
     * @return
     */
    private static int getCheckCode(int[] ints) {
        if (null != ints && ints.length > 1) {
            int ti = 0;
            int si = 0;
            int cj = 0;
            int pj = 10;
            for (int i = 0; i < ints.length; i++) {
                ti = ints[i];
                pj = (cj % 11) == 0 ? 10 : (cj % 11);
                si = pj + ti;
                cj = (0 == si % 10 ? 10 : si % 10) * 2;
                if (i == ints.length - 1) {
                    pj = (cj % 11) == 0 ? 10 : (cj % 11);
                    return pj == 1 ? 1 : 11 - pj;
                }
            }
        }
        return -1;
    }

    /**
     * 判断是否为合法Cron 表达式
     * @param cronExpression
     * @return
     */
    public boolean isCron(String cronExpression){
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (Exception e) {
        }
        return false;
    }

}

