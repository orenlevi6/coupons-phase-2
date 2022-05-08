package com.jb.couponsproject.beans;

public enum Categories {
    FOOD,
    ELECTRICITY,
    RESTAURANT,
    VACATION;

    public final int value = ordinal() + 1;
}
