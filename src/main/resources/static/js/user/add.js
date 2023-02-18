$(document).ready(function () {

    $("#btn-add-user").click(function () {
        addUser();
    });

});

function addUser() {
    const quizLevel = $('#quiz-level').val(),
        email = $("#email").val(),
        quizSize = $("#quizSize").val(),
        quizDaySet = findQuizDaySet(),
        quizCategorySet = findQuizCategorySet();

    if (!quizLevel) {
        alert("Input quizLevel");
        return false;
    } else if (!email) {
        alert("Input email");
        return false;
    } else if (!quizSize) {
        alert("Input quizSize");
        return false;
    } else if (quizDaySet.length <= 0) {
        alert("Select quizDaySet");
        return false;
    } else if (quizCategorySet.length <= 0) {
        alert("Select quizCategorySet");
        return false;
    }

    $.ajax({
        url: '/members',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            'email': email,
            'quizLevel': quizLevel,
            'quizSize': quizSize,
            'quizDaySet': quizDaySet,
            'quizCategorySet': quizCategorySet
        }),
        success: function () {
            alert("사용자 추가 성공")
        },
        error: function () {
            alert("사용자 추가 실패");
        }
    });
}

function findQuizDaySet() {
    const quizDaySet = [];

    $("#quiz-day-set input").each(function () {
        const quizDay = $(this);
        if (quizDay.is(':checked')) {
            quizDaySet.push(quizDay.val())
        }
    });
    return quizDaySet;
}

function findQuizCategorySet() {
    const quizCategorySet = [];

    $("#quiz-category-set input").each(function () {
        const quizCategory = $(this);
        if (quizCategory.is(':checked')) {
            quizCategorySet.push(quizCategory.val())
        }
    });

    return quizCategorySet;
}