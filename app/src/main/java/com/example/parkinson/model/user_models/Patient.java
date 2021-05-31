package com.example.parkinson.model.user_models;

public class Patient {
    private String clinic;//לאיזה קופת חולים משוייך המטופל
    private String country;//באיזה מדינה נולד
    private Long date_of_birth;//תאריך הלידה של המטופל
    private String doctor;//שם הרופא אליו הוא משוייך
    private String email;//כתובת המייל של המטופל
    private String first_name;//שמו הפרטי של ה מטופל
    private String gender;//מין המטופל
    private String last_name;//שם המשפחה של המטופל
    private String mobile_phone;//מספר הפלאפון של המטופל
    private String token;//המזהה של המכשיר של המטופל על מנת לקבל התראות

    private Boolean hasUnansweredQuestionnaire;//משתנה בוליאני האומר אם המטופל לא ענה על שאלון

    private Boolean needToUpdateMedicine;//משתנה בוליאני האומר אם על המטופל לעדכן את רשימת התרופות

    public Patient() {
    }

    public Patient(String clinic, String country, Long date_of_birth, String doctor, String email, String first_name, String gender, String last_name, String mobile_phone, String token, Boolean hasUnansweredQuestionnaire, Boolean needToUpdateMedicine) {
        this.clinic = clinic;
        this.country = country;
        this.date_of_birth = date_of_birth;
        this.doctor = doctor;
        this.email = email;
        this.first_name = first_name;
        this.gender = gender;
        this.last_name = last_name;
        this.mobile_phone = mobile_phone;
        this.token = token;
        this.hasUnansweredQuestionnaire = hasUnansweredQuestionnaire;
        this.needToUpdateMedicine = needToUpdateMedicine;
    }



    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Long date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getHasUnansweredQuestionnaire() {
        if (hasUnansweredQuestionnaire != null) {
            return hasUnansweredQuestionnaire;
        } else {
            return true;
        }
    }

    public void setHasUnansweredQuestionnaire(Boolean hasUnansweredQuestionnaire) {
        this.hasUnansweredQuestionnaire = hasUnansweredQuestionnaire;
    }

    public Boolean getNeedToUpdateMedicine() {
        if (needToUpdateMedicine != null) {
            return needToUpdateMedicine;
        } else {
            return true;
        }
    }

    public void setNeedToUpdateMedicine(Boolean needToUpdateMedicine) {
        this.needToUpdateMedicine = needToUpdateMedicine;
    }

}