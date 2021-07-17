package com.example.parkinson.model.general_models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.parkinson.R;

import java.util.List;

public class Medicine implements Parcelable {
    private String id;// המזהה של התרופה בדאטה בייס מאפשר גישה מהירה
    private String categoryId;// המזהה של הקטוגריה בדאטה בייס מאפשר גישה מהירה

    private String name;// שם התרופה
    private Double dosage;// כמות השימוש של נטילת התרופה
    List<Time> hoursArr;// מערך של שעות נטילת התרופות

    public Medicine(){

    }

    public Medicine(String id, String categoryId, String name, Double dosage, List<Time> hoursArr) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.dosage = dosage;
        this.hoursArr = hoursArr;
        this.categoryId = categoryId;
    }

    protected Medicine(Parcel in) {
        id = in.readString();
        categoryId = in.readString();
        name = in.readString();
        dosage = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(categoryId);
        dest.writeString(name);
        dest.writeDouble(dosage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public List<Time> getHoursArr() {
        return hoursArr;
    }

    public void setHoursArr(List<Time> hoursArr) {
        this.hoursArr = hoursArr;
    }

    public String dosageString(Context context){
        if (dosage == 0.25) {
            return context.getString(R.string.one_quarter);
        } else if (dosage == 0.50) {
            return context.getString(R.string.half_a_pill);
        } else if (dosage == 0.75) {
            return context.getString(R.string.Three_quarters);
        } else if (dosage == 1.00) {
            return context.getString(R.string.one_pill);
        } else if (dosage == 1.25) {
            return context.getString(R.string.one_and_a_quarter);
        } else if (dosage == 1.50) {
            return context.getString(R.string.one_and_a_half);
        } else if (dosage == 1.75) {
            return context.getString(R.string.one_and_three_quarters);
        } else if (dosage == 2.00) {
            return context.getString(R.string.two_pills);
        }
        return "";
    }

}
