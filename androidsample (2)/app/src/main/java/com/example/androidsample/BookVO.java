package com.example.androidsample;

public class BookVO {
    private String bimgurl; // 도서 이미지
    private String btitle; // 도서 제목
    private String bauthor; // 저자
    private String bprice; // 도서 가격

    public BookVO() {

    }

    public BookVO(String bimgurl, String btitle, String bauthor, String bprice) {
        this.bimgurl = bimgurl;
        this.btitle = btitle;
        this.bauthor = bauthor;
        this.bprice = bprice;
    }

    public String getBimgurl() {
        return bimgurl;
    }

    public String getBtitle() {
        return btitle;
    }

    public String getBauthor() {
        return bauthor;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBimgurl(String bimgurl) {
        this.bimgurl = bimgurl;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }
}
