package com.nhatanh.centerlearn.common.utils;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@EzySingleton
public class SlugGenerate {
    public static String createSlug(String str) {
        String slug = str.replaceAll("đ", "d").replaceAll("Đ", "D");
        String normalized = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        slug = slug.toLowerCase(Locale.ENGLISH)
            .replaceAll("[^a-z0-9\\s-]", "")
            .replaceAll("\\s+", "-");
        slug = slug.replaceAll("^-+|-+$", "");
        return slug;
    }

    public static void main(String[] args) {
        System.out.println("Tên term có dấu và ký tự đặc biệt!");
        System.out.println(SlugGenerate.createSlug("Tên term có dấu và ký tự đặc biệt!"));
        System.out.println(SlugGenerate.createSlug("Vó Lụ Tó ché sõ lọc bợ lích"));
        System.out.println(SlugGenerate.createSlug("@$ký Đĩ *&% Gó Lợ CHớ sỡ nỡ"));
    }
}
