package com.likelion.mission06.domain;

public class FrontendLion extends Lion {

    public FrontendLion(String name, String major, int generation) {
        super(name, major, generation);
    }

    @Override
    public Part part() {
        return Part.FRONTEND;
    }

    @Override
    public void work() {
        System.out.println(name + "은(는) React 와 CSS 로 화면과 사용자 상호작용을 구현한다.");
    }
}
