package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UriService {
    public List<UriModel> mergeAndSetStatus(List<UriModel> uriSystem, List<UriModel> uriDatabase) {
        // Tạo map từ uriDatabase, thiết lập trạng thái NONE
        Map<UriKey, UriModel> dbMap = uriDatabase.stream()
            .collect(Collectors.toMap(
                uri -> new UriKey(uri.getUriPath(), uri.getUriMethod().name()),
                uri -> {
                    uri.setStatus(PermissionStatus.NONE);
                    return uri;
                }
            ));

        // Duyệt qua uriSystem, nếu đã có trong dbMap thì set ACTIVE, nếu chưa có thì set INACTIVE
        List<UriModel> result = uriSystem.stream()
            .map(uri -> {
                UriKey key = new UriKey(uri.getUriPath(), uri.getUriMethod().name());
                if (dbMap.containsKey(key)) {
                    dbMap.get(key).setStatus(PermissionStatus.ACTIVE);
                    return dbMap.remove(key);
                } else {
                    uri.setStatus(PermissionStatus.INACTIVE);
                    return uri;
                }
            })
            .collect(Collectors.toList());

        // Thêm các URI còn lại trong dbMap vào danh sách kết quả (với trạng thái NONE)
        result.addAll(dbMap.values());

        return result;
    }

    private static class UriKey {
        private final String uriPath;
        private final String uriMethod;

        public UriKey(String uriPath, String uriMethod) {
            this.uriPath = uriPath;
            this.uriMethod = uriMethod;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UriKey uriKey = (UriKey) o;
            return Objects.equals(uriPath, uriKey.uriPath) &&
                Objects.equals(uriMethod, uriKey.uriMethod);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uriPath, uriMethod);
        }
    }
}
