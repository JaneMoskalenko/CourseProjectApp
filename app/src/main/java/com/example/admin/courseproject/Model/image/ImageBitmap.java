package com.example.admin.courseproject.Model.image;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageBitmap implements Parcelable{

    Bitmap imageBitmap;

    public ImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    private ImageBitmap(Parcel parcel) {
        imageBitmap = parcel.readParcelable(Bitmap.class.getClassLoader());
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(imageBitmap, flags);
    }

    public static final Creator<ImageBitmap> CREATOR = new Creator<ImageBitmap>() {
        @Override
        public ImageBitmap createFromParcel(Parcel parcel) {
            return new ImageBitmap(parcel);
        }

        @Override
        public ImageBitmap[] newArray(int size) {
            return new ImageBitmap[size];
        }
    };
}
