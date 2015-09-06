package com.example.mall.model;

import com.example.mall.model.base.BaseModel;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.model.TestMedel.java
 * @author: liqiongwei
 * @date: 2015-09-06 14:47
 */
public class TestModel extends BaseModel {
    private static final String TAG = TestModel.class.getSimpleName();
        private String id = "";
        private String title = "";
        private String url = "";
        private String createDate = "";

        public TestModel(String id, String title, String url, String createDate){
            super();
            this.setId(id);
            this.setTitle(title);
            this.setUrl(url);
            this.setCreateDate(createDate);
        }

        public TestModel(){
            super();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }


        @Override
        public String toString(){
            return "Favorite [id="+id+",title="+title+",url="+url+",createDate="+createDate+"]\n\n";
        }

    }
