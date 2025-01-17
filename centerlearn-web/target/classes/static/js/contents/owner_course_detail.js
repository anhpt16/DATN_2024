
import courseService from "../service/CourseService.js";
import courseUI from "../ui/CourseUI.js";
$(document).ready(function() {





    $("#btn-back").on('click', function() {
        history.back();
    })


    $(".list-lesson-container").on('click', ".lesson-item .lesson-title-container", function() {
        console.log("This done")
        const thisItemDetail = $(this).closest("li").find('ul');
        const thisItemIcon = $(this).find('i');
        if (thisItemDetail.hasClass('d-none')) {
            thisItemDetail.removeClass('d-none');
            thisItemIcon.removeClass('fa-angle-down').addClass('fa-angle-right');
        } else {
            thisItemDetail.addClass('d-none');
            thisItemIcon.removeClass('fa-angle-right').addClass('fa-angle-down');
        }
    })


    $(".list-lesson-container").on('click', ".lesson-detail-item a", async function() {
        console.log("Onn")
        $(".lesson-detail-item a").removeClass('active');
        $(this).addClass('active')
        let detailId = $(this).closest('li').attr('data-id');
        let lessonId = $(this).closest('li').closest('ul').closest('li').attr('data-id');
        let courseId = $("#btn-back").attr('data-id');
        if (detailId == null || lessonId == null || courseId == null) {
            return;
        }
        console.log(lessonId + " " + detailId + " " + courseId);
        console.log(menuSelected());
        if (menuSelected() === 1) {
            try {
                const response = await courseService.getSectionDetail(courseId, lessonId, detailId);
                console.log(response);
                $("#content-subject").empty();
                $("#content-subject").html(response.content);
            } catch (error) {
                console.log("Error: " + error);
            }
        }
        else if (menuSelected() === 2) {
            try {
                const response  = await courseService.getExerciseDetail(courseId, lessonId, detailId);
                console.log(response);
                $("#content-subject").empty();
                $("#content-subject").html(response.content);
            } catch (error) {
                console.log("Error: " + error);
            }
        }
        else {
            console.log("Menu Choosen Invalid");
        }
    });

    $("#select-menu").on('change', async function() {
        let select = $("#select-menu").val()
        let courseId = $("#btn-back").attr('data-id');
        if (select == null || select == undefined || select === "" || courseId == null || courseId == undefined || courseId === "") {
            return;
        }
        if (select == 1) {
            try {
                const response = await courseService.getSubjectSection(courseId);
                console.log(response);
                renderSubjectsSections(response);
            } catch (error) {
                console.log("Error: " + error);
            }
        } else if (select == 2) {
            try {
                const response = await courseService.getSubjectExercise(courseId);
                console.log(response);
                renderSubjectsExercises(response);
            } catch (error) {
                console.log("Error: " + error);
            }
        } else {
            console.log("Menu Invalid");
        }
    })

    function menuSelected() {
        if ($("#select-menu").val() == 1) {
            return 1;
        }
        if ($("#select-menu").val() == 2) {
            return 2;
        }
    }


    function renderSubjectsSections(subjects) {
        // Lấy container chính
        const container = document.querySelector('.list-lesson-container');
        container.innerHTML = ''; // Xóa nội dung cũ nếu có
      
        subjects.forEach(subject => {
          // Tạo phần tử div cho mỗi subject
          const subjectDiv = document.createElement('div');
          subjectDiv.setAttribute('data-id', subject.subjectId);
      
          // Tạo tiêu đề môn học
          const subjectTitle = document.createElement('h3');
          subjectTitle.textContent = subject.subjectName;
          subjectTitle.setAttribute('data-id', subject.subjectId);
      
          subjectDiv.appendChild(subjectTitle);
      
          // Tạo danh sách bài học
          const lessonList = document.createElement('ul');
          subject.lessonsSections.forEach(lessonSection => {
            const lessonItem = document.createElement('li');
            lessonItem.classList.add('lesson-item');
            lessonItem.setAttribute('data-id', lessonSection.id);
      
            // Tạo tiêu đề bài học
            const lessonTitleContainer = document.createElement('a');
            lessonTitleContainer.href = 'javascript:void(0)';
            lessonTitleContainer.classList.add(
              'lesson-title-container',
              'd-flex',
              'justify-content-between',
              'align-items-center',
              'border-bottom'
            );
      
            const lessonTitle = document.createElement('span');
            lessonTitle.classList.add('lesson-title');
            lessonTitle.textContent = lessonSection.title;
      
            const icon = document.createElement('i');
            icon.classList.add('fa-solid', 'fa-angle-down');
      
            lessonTitleContainer.appendChild(lessonTitle);
            lessonTitleContainer.appendChild(icon);
            lessonItem.appendChild(lessonTitleContainer);
      
            // Tạo danh sách chi tiết bài học
            const lessonDetail = document.createElement('ul');
            lessonDetail.classList.add('lesson-detail', 'd-none');
      
            if (lessonSection.sections && lessonSection.sections.length > 0) {
              lessonSection.sections.forEach(section => {
                const detailItem = document.createElement('li');
                detailItem.classList.add('lesson-detail-item');
                detailItem.setAttribute('data-id', section.id);
      
                const detailLink = document.createElement('a');
                detailLink.href = 'javascript:void(0)';
                detailLink.classList.add(
                  'nav-link',
                  'd-flex',
                  'align-items-center',
                  'text-decoration-none'
                );
      
                const priority = document.createElement('strong');
                priority.textContent = section.priority;
      
                const detailTitle = document.createElement('span');
                detailTitle.classList.add('lesson-detail-title');
                detailTitle.textContent = section.title;
      
                detailLink.appendChild(priority);
                detailLink.appendChild(detailTitle);
                detailItem.appendChild(detailLink);
                lessonDetail.appendChild(detailItem);
              });
            } else {
              // Hiển thị thông báo nếu không có sections
              const emptyItem = document.createElement('li');
              emptyItem.classList.add('lesson-detail-item');
              emptyItem.textContent = 'No sections available';
              lessonDetail.appendChild(emptyItem);
            }
      
            lessonItem.appendChild(lessonDetail);
            lessonList.appendChild(lessonItem);
          });
      
          subjectDiv.appendChild(lessonList);
          container.appendChild(subjectDiv);
        });
      }
      

      function renderSubjectsExercises(subjects) {
        // Lấy container chính
        const container = document.querySelector('.list-lesson-container');
        container.innerHTML = ''; // Xóa nội dung cũ nếu có
      
        subjects.forEach(subject => {
          // Tạo phần tử div cho mỗi subject
          const subjectDiv = document.createElement('div');
          subjectDiv.setAttribute('data-id', subject.subjectId);
      
          // Tạo tiêu đề môn học
          const subjectTitle = document.createElement('h3');
          subjectTitle.textContent = subject.subjectName;
          subjectTitle.setAttribute('data-id', subject.subjectId);
      
          subjectDiv.appendChild(subjectTitle);
      
          // Tạo danh sách bài học
          const lessonList = document.createElement('ul');
          subject.lessonsExercises.forEach(lessonExercise => {
            const lessonItem = document.createElement('li');
            lessonItem.classList.add('lesson-item');
            lessonItem.setAttribute('data-id', lessonExercise.id);
      
            // Tạo tiêu đề bài học
            const lessonTitleContainer = document.createElement('a');
            lessonTitleContainer.href = 'javascript:void(0)';
            lessonTitleContainer.classList.add(
              'lesson-title-container',
              'd-flex',
              'justify-content-between',
              'align-items-center',
              'border-bottom'
            );
      
            const lessonTitle = document.createElement('span');
            lessonTitle.classList.add('lesson-title');
            lessonTitle.textContent = lessonExercise.title;
      
            const icon = document.createElement('i');
            icon.classList.add('fa-solid', 'fa-angle-down');
      
            lessonTitleContainer.appendChild(lessonTitle);
            lessonTitleContainer.appendChild(icon);
            lessonItem.appendChild(lessonTitleContainer);
      
            // Tạo danh sách chi tiết bài học
            const lessonDetail = document.createElement('ul');
            lessonDetail.classList.add('lesson-detail', 'd-none');
      
            if (lessonExercise.exercises && lessonExercise.exercises.length > 0) {
                lessonExercise.exercises.forEach(exercise => {
                const detailItem = document.createElement('li');
                detailItem.classList.add('lesson-detail-item');
                detailItem.setAttribute('data-id', exercise.id);
      
                const detailLink = document.createElement('a');
                detailLink.href = 'javascript:void(0)';
                detailLink.classList.add(
                  'nav-link',
                  'd-flex',
                  'align-items-center',
                  'text-decoration-none'
                );
      
                const priority = document.createElement('strong');
                priority.textContent = exercise.priority;
      
                const detailTitle = document.createElement('span');
                detailTitle.classList.add('lesson-detail-title');
                detailTitle.textContent = exercise.title;
      
                detailLink.appendChild(priority);
                detailLink.appendChild(detailTitle);
                detailItem.appendChild(detailLink);
                lessonDetail.appendChild(detailItem);
              });
            } else {
              // Hiển thị thông báo nếu không có sections
              const emptyItem = document.createElement('li');
              emptyItem.classList.add('lesson-detail-item');
              emptyItem.textContent = 'No sections available';
              lessonDetail.appendChild(emptyItem);
            }
      
            lessonItem.appendChild(lessonDetail);
            lessonList.appendChild(lessonItem);
          });
      
          subjectDiv.appendChild(lessonList);
          container.appendChild(subjectDiv);
        });
      }
})