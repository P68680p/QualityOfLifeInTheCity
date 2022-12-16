package com.example.qualityoflifeinthecity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String geoname_id;
    String city;
    String country;

    public City() {
    }

    public City(String geoname_id, String fullCityString) {
        this.geoname_id = geoname_id;
        this.city = fullCityString.substring(0, fullCityString.indexOf(','));
        this.country = fullCityString.substring(fullCityString.indexOf(',') + 2);
    }

    protected City(Parcel in) {
        id = in.readInt();
        geoname_id = in.readString();
        city = in.readString();
        country = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(geoname_id);
        parcel.writeString(city);
        parcel.writeString(country);
    }
}
