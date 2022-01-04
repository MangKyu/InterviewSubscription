$(document).ready(function () {
    $("#innerEditor").append($("#description").val())

    let editor
    ClassicEditor.create(document.querySelector('#editor'))
        .then(newEditor => {
            editor = newEditor;
        })
        .catch(error => {
            console.error(error);
        });

    $("#btn-edit-answer").click(function () {
        editAnswer(editor);
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
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            'quizResourceId': quizResourceId,
            'description': description
        }),
        success: function () {
            $("#answerResourceId").val('a');
            alert("설명 추가 성공")
        },
        error: function (e) {
            alert("설명 추가 실패");
        }
    });
}