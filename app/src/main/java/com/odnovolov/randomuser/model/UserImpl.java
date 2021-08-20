package com.odnovolov.randomuser.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class UserImpl implements User, Parcelable {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String photoUrl;
    private final int age;
    private final String city;
    private final String email;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public UserImpl(
            String id,
            String firstName,
            String lastName,
            String photoUrl,
            int age,
            String city,
            String email
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
        this.age = age;
        this.city = city;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserImpl user = (UserImpl) o;
        return age == user.age &&
                id.equals(user.id) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                photoUrl.equals(user.photoUrl) &&
                city.equals(user.city) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, photoUrl, age, city, email);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(photoUrl);
        dest.writeInt(age);
        dest.writeString(city);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserImpl> CREATOR = new Creator<UserImpl>() {
        @Override
        public UserImpl createFromParcel(Parcel in) {
            String id = in.readString();
            String firstName = in.readString();
            String lastName = in.readString();
            String photoUrl = in.readString();
            int age = in.readInt();
            String city = in.readString();
            String email = in.readString();
            return new UserImpl(
                    id,
                    firstName,
                    lastName,
                    photoUrl,
                    age,
                    city,
                    email
            );
        }

        @Override
        public UserImpl[] newArray(int size) {
            return new UserImpl[size];
        }
    };
}