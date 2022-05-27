$(document).ready(function () {
    $("#innerEditor").append($("#description").val())

    let editor
    ClassicEditor.create(document.querySelector('#editor'), {
        ckfinder: {
            uploadUrl: '/api/file'
        }}).then(newEditor => {
            editor = newEditor;
        }).catch(error => {
            console.error(error);
        });

    $("#btn-edit-answer").click(function () {
        editAnswer(editor);
    });

    $("#btn-delete-answer").click(function () {
        deleteAnswer();
    });
});

function editAnswer(editor) {
    const quizResourceId = $('#quizResourceId').val(),
        description = editor.getData();

    if (!description) {
        alert("Input description");
        return false;
    }

    $.ajax({
        url: '/answer',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            'quizResourceId': quizResourceId,
            'description': description
        }),
        success: function () {
            alert("설명 추가 성공")
            window.location = window.location
        },
        error: function () {
            alert("설명 추가 실패");
        }
    });
}

function deleteAnswer() {
    const answerResourceId = $("#answerResourceId").val();

    if (!answerResourceId) {
        alert("Answer Not Exists");
        return false;
    }

    $.ajax({
        url: '/answer/' + answerResourceId,
        type: 'DELETE',
        contentType: 'application/json',
        success: function () {
            alert("설명 삭제 성공");
            window.location = window.location
        },
        error: function () {
            alert("설명 삭제 실패");
        }
    });
}