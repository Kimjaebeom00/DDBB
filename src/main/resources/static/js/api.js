var api = {
    projectMembers: function(projectId) {//컨트롤러 메소드 이름이랑 같다 ->오브젝트 형식
        var params = {
            projectId: projectId//왼쪽은 키값 오른쪽은 벨류
        };

        $.ajax({
            type: "POST",
            url: "/projectMember/list",
            data: params,
            success: function(result){
                console.log(result.code);
                console.log(result);
            },
            error: function(error){
                alert("API 에러가 발생했습니다.");
                console.error(error);
            }
        });
    }
}