package com.asa.asri_larisso.Activity.retail;

import android.text.InputFilter ;
import android.text.Spanned ;

public class QtyMinMax implements InputFilter {

    private int mIntMin , mIntMax ;
    public QtyMinMax ( int minValue , int maxValue) {
        this . mIntMin = minValue ;
        this . mIntMax = maxValue ;
    }

    public QtyMinMax (String minValue , String maxValue) {
        this . mIntMin = Integer. parseInt (minValue) ;
        this . mIntMax = Integer. parseInt (maxValue) ;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer. parseInt (dest.toString() + source.toString()) ;
            if (isInRange( mIntMin , mIntMax , input))
                return null;
        } catch (NumberFormatException e) {
            e.printStackTrace() ;
        }
        return "" ;
    }
    private boolean isInRange ( int a , int b , int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a ;
    }
}
