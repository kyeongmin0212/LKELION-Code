package com.likelion.mission06.domain;

public class BackendLion extends Lion {

    public BackendLion(String name, String major, int generation) {
        super(name, major, generation);
    }

    @Override
    public Part part() {
        return Part.BACKEND;
    }

    @Override
    public void work() {
        System.out.println(name + "은(는) Spring Boot 로 API 와 데이터베이스 로직을 만든다.");
    }
}
