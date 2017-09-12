package com.example.kylinarm.multilisttest.Entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class OnlyTwoEntity extends BaseMultiEntity<OnlyTwoEntity.Onedata> {

    public String title;
    public List<Onedata> onedata;

    @Override
    public List<Onedata> getMultiListChildData() {
        return onedata;
    }

    @Override
    public String getData() {
        return null;
    }

    public static class Onedata extends BaseMultiEntity<NewspaperEntity>{
        public String name;
        public List<NewspaperEntity> twodata;

        @Override
        public List<NewspaperEntity> getMultiListChildData() {
            return twodata;
        }

        @Override
        public String getData() {
            return name;
        }


    }

}
