package com.nith.appteam.nimbus.Model;

import java.util.ArrayList;

/**
 * Created by LENOVO on 03-03-2019.
 */

public class GalleryModel {
    String headerImage,title;
    ArrayList<String>imagesurl;

    public GalleryModel(String headerImage, String title, ArrayList<String> imagesurl) {
        this.headerImage = headerImage;
        this.title = title;
        this.imagesurl = imagesurl;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getImagesurl() {
        return imagesurl;
    }
}
