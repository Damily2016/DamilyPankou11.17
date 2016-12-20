package com.damily.pkds.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dandan.Cao on 2016/12/15.
 */
public class GiftInfo implements Serializable {
    private int code;
    private DataBean data;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
private GiftInfo(){}


    public static class DataBean implements Serializable{

        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable{

            private DataDetailBean data;

            public DataDetailBean getData() {
                return data;
            }

            public void setData(DataDetailBean data) {
                this.data = data;
            }

            public static class DataDetailBean  implements Serializable{
                private String cover_image_url;
                private String name;
                private String description;

                public void setPurchase_url(String purchase_url) {
                    this.purchase_url = purchase_url;
                }

                private String purchase_url;

                public String getPurchase_url() {
                    return purchase_url;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getDescription() {
                    return description;
                }

                public String getCover_image_url() {
                    return cover_image_url;
                }

                public void setCover_image_url(String cover_image_url) {
                    this.cover_image_url = cover_image_url;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
