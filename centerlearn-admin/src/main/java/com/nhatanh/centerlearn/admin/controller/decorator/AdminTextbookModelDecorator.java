package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.TextbookModel;
import com.nhatanh.centerlearn.admin.response.AdminTextbookResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminTextbookModelDecorator {
    private final AdminModelToResponseConverter adminModelToResponseConverter;

    public PaginationModel<AdminTextbookResponse> decorateTextbookPaginationModel(PaginationModel<TextbookModel> textbookPaginationModel) {
        List<AdminTextbookResponse> textbookResponses = newArrayList(textbookPaginationModel.getItems(), this.adminModelToResponseConverter::toTextbookResponse);
        return PaginationModel.<AdminTextbookResponse>builder()
            .items(textbookResponses)
            .totalPage(textbookPaginationModel.getTotalPage())
            .currentPage(textbookPaginationModel.getCurrentPage())
            .build();
    }

    // Lấy ra danh sách các bài học kèm các đề mục của các bài học trong một giáo trình
    /*
    1. Từ id của giáo trình nhận được, lấy ra các bài học của giáo trình đó trong bảng textbook_lesson --> Danh sách id của các bài học (sắp xếp theo priority)
    2. Từ danh sách id của các bài học, lấy ra các section tương ứng với các bài học trong bảng section --> Danh sách các section của các bài học (theo priority)
    3. Ghép dữ liệu
     */
}
