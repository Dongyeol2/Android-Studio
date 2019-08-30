package com.example.kakaobookserch;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

// 해당 클래스의 객체가 마샬링이 가능하도록 parcelable interface를 구현

public class KAKAOBookVO implements Parcelable {

    private ArrayList<String> authors;
    private String contents;
    private String datetime;
    private String isbn;
    private String price;
    private String publisher;
    private String sale_price;
    private String status;
    private String thumbnail;
    private String title;
    private ArrayList<String> translators;
    private String url;

    // CREATOR라고 불리는 static 상수를 반드시 정의해야한다.
    public static final Creator<KAKAOBookVO> CREATOR = new Creator<KAKAOBookVO>() {
        // 마샬링된 데이터를 언마샬링(복원)할 때 사용되는 method
        @Override
        public KAKAOBookVO createFromParcel(Parcel parcel) {
            return new KAKAOBookVO(parcel);
        }

        @Override
        public KAKAOBookVO[] newArray(int size) {
            return new KAKAOBookVO[size];
        }
    };

    // default 생성자
    public KAKAOBookVO() {
    }

    public KAKAOBookVO(ArrayList<String> authors,
                       String contents,
                       String datetime,
                       String isbn,
                       String price,
                       String publisher,
                       String sale_price,
                       String status,
                       String thumbnail,
                       String title,
                       ArrayList<String> translators,
                       String url) {
        this.authors = authors;
        this.contents = contents;
        this.datetime = datetime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.sale_price = sale_price;
        this.status = status;
        this.thumbnail = thumbnail;
        this.title = title;
        this.translators = translators;
        this.url = url;
    }

    // 복원 작업할 때 사용되는 생성자
    // 복원시 제일 신경써야 하는 부분은 순서
    // (마샬링 순서와 언마샬링 순서가 동일해야 한다.)
    protected KAKAOBookVO(Parcel parcel) {
        authors = parcel.readArrayList(null);
        contents = parcel.readString();
        datetime = parcel.readString();
        isbn = parcel.readString();
        price = parcel.readString();
        publisher = parcel.readString();
        sale_price = parcel.readString();
        status = parcel.readString();
        thumbnail = parcel.readString();
        title = parcel.readString();
        translators = parcel.readArrayList(null);
        url = parcel.readString();
    }

    // 수정할 필요 없이 Override만
    @Override
    public int describeContents() {
        return 0;
    }

    // 마샬링(데이터를 변환) 하는 역할을 하는 method
    // 데이터 변환, 데이터 복원 두가지 필요
    // 데이터 변환하는 순서와 복원하는 순서는 반드시 같아야 함.
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeList(authors); // list에 대한 변환 처리
            parcel.writeString(contents); // String에 대한 변환 처리
            parcel.writeString(datetime);
            parcel.writeString(isbn);
            parcel.writeString(price);
            parcel.writeString(publisher);
            parcel.writeString(sale_price);
            parcel.writeString(status);
            parcel.writeString(status);
            parcel.writeString(thumbnail);
            parcel.writeString(title);
            parcel.writeList(translators);
            parcel.writeString(url);



        } catch (Exception e) {
            Log.i("KAKAOBook", e.toString());
        }

    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getContents() {
        return contents;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPrice() {
        return price;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSale_price() {
        return sale_price;
    }

    public String getStatus() {
        return status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public String getUrl() {
        return url;
    }
}
