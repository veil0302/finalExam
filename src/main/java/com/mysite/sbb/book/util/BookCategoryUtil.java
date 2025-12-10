package com.mysite.sbb.book.util;

import java.util.Map;

public class BookCategoryUtil {

    public static final Map<String, String> CATEGORY_MAP = Map.of(
            "100", "소설",
            "110", "시",
            "120", "에세이",
            "130", "인문학",
            "170", "경제/경영",
            "280", "컴퓨터/IT",
            "310", "역사",
            "330", "사회과학"
    );

    public static String getCategoryName(String code) {
        return CATEGORY_MAP.getOrDefault(code, "기타");
    }
}
