package com.xiang.otcmedicinedelivery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PharmacyModel implements Parcelable {

    private String name;
    private String address;
    private String image;
    private double latitude;
    private double longitude;
    private List<Menu> menus;

    public PharmacyModel() {};

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    protected PharmacyModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        image = in.readString();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<PharmacyModel> CREATOR = new Creator<PharmacyModel>() {
        @Override
        public PharmacyModel createFromParcel(Parcel in) {
            return new PharmacyModel(in);
        }

        @Override
        public PharmacyModel[] newArray(int size) {
            return new PharmacyModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(image);
        dest.writeTypedList(menus);
    }
}
