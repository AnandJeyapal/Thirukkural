package com.impiger.thirukkural.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by anand on 07/12/15.
 */
public class Adhigaram implements Parcelable{
    private int adhigaramNumber;
    private String partName;
    private String iyalName;
    private int startKural;
    private int endKural;
    private String name;

    public Adhigaram(int adhigaramNumber, String name) {
        this.adhigaramNumber = adhigaramNumber;
        this.name = name;
    }

    public Adhigaram() {

    }

    protected Adhigaram(Parcel in) {
        adhigaramNumber = in.readInt();
        name = in.readString();
    }

    public static final Creator<Adhigaram> CREATOR = new Creator<Adhigaram>() {
        @Override
        public Adhigaram createFromParcel(Parcel in) {
            return new Adhigaram(in);
        }

        @Override
        public Adhigaram[] newArray(int size) {
            return new Adhigaram[size];
        }
    };

    public int getAdhigaramNumber() {
        return adhigaramNumber;
    }

    public void setAdhigaramNumber(int adhigaramNumber) {
        this.adhigaramNumber = adhigaramNumber;
    }

    public String getAdhigaramName() {
        return name;
    }

    public void setAdhigaramName(String name) {
        this.name = name;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setIyalName(String iyalName) {
        this.iyalName = iyalName;
    }

    public void setStartKural(int startKural) {
        this.startKural = startKural;
    }

    public void setEndKural(int endKural) {
        this.endKural = endKural;
    }

    public int getStartKural() {
        return startKural;
    }

    public int getEndKural() {
        return endKural;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(adhigaramNumber);
        dest.writeString(name);
    }
}
