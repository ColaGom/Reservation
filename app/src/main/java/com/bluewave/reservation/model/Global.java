package com.bluewave.reservation.model;

/**
 * Created by Developer on 2016-05-12.
 */
public class Global {
    private static User loginUser;

    public static User getLoginUser()
    {
        return loginUser;
    }

    public static void setLoginUser(User user)
    {
        loginUser = user;
    }

}
